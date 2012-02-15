package com.sapsiero.accountLoader

import groovy.xml.MarkupBuilder
import java.text.NumberFormat

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/28/12
 * Time: 12:19 PM
 * To change this template use File | Settings | File Templates.
 */
class ZkbContentProcessor implements ContentProcessor {

    private Website site
    private StringWriter writer
    private MarkupBuilder xml

    public ZkbContentProcessor(Website site) {
        this.site = site

        writer = new StringWriter()
        xml = new MarkupBuilder(writer)
    }

    public void process(Closure closure) {
        xml.accounts(banknumber: 'ZKBKCHZZ80A') {
            site.eachDocument { docType, docAttr, doc ->
                switch (docType) {
                    case Website.DocType.TEXT:
                        processText(docAttr, doc)
                        break
                    case Website.DocType.PDF:
                        closure.call(docType, docAttr, doc)
                        break
                    default:
                        break
                }
            }
        }
        closure.call(Website.DocType.XML, [name: "zkb", bank: "ZKBKCHZZ80A"], writer.toString())
    }

    private void processText(def docAttr, def doc) {
        doc = doc.replace("\r", "")
        
        xml.account(number: docAttr.account, type: 'Cash', currency: 'CHF') {

            def lineBefore

            doc.split('\n').eachWithIndex { line, i ->

                if (i == 1) {
                    balance(date: new Date().format('yyyy-MM-dd'), value: toPlain(line.split(',')[8]))
                }

                if (i % 2 != 0) {
                    lineBefore = line
                } else if (i > 1) {
                    if (lineBefore =~ /^"[0-9]{2}.[0-9]{2}.[0-9]{4}".*/) {
                        def line1 = (lineBefore =~ /"([^"]*)"|(?<=,|^)([^,]*)(?=,|$)/)
                        def line2 = (line =~ /"([^"]*)"|(?<=,|^)([^,]*)(?=,|$)/)

                        def m = (toPlain(line2[1][1]) =~ / ([A-Z]{3})[ ]*([0-9]+.[0-9]{2})/)

                        def cur = "CHF"
                        def val = "0.0"
                        if (m.size() == 0) {
                            //println 'No currency found. Defaulting to "CHF".'
                            val = toPlain(line1[5][1]) + toPlain(line1[6][1])
                        }
                        if (m.size() >= 1) {
                            //println "Value ${m[0][2]} with currency \"${m[0][1]}\" found."
                            val = m[0][2]
                            cur = m[0][1]
                        }
                        def fee = "0.0"
                        if (m.size() == 2) {
                            if (m[1][1] == 'CHF') {
                                fee = m[1][2]
                            }
                        }

                        //create transaction
                        transaction(
                                bookingdate: toDate(toPlain(line1[0][1])),
                                tradedate: toDate(toPlain(line1[7][1])),
                                message: toPlain(line2[1][1]),
                                value: toDouble((line1[5][1].length() > 2 ? "-" : "") + val),
                                currency: cur,
                                fee: '-' + toDouble(fee)
                                ,
                                accountvalue: toDouble(((line1[5][1].length() > 2 ? "-" + toPlain(line1[5][1]) :"") + toPlain(line1[6][1])))
                        )
                    }
                }
            }
        }
    }

    private String toDate(String value) {
        String.format('%tF', Date.parse('dd.MM.yyyy', value))
    }

    private String toDouble(String value) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US)
        String.format('%.2f', numberFormat.parse(value).doubleValue())
    }

    private String toPlain(String value) {
        if (value && value[-1] == "\"" && value[0] == "\"")
            if (value.length() == 2)
                return ""
            else
                return value[1..-2]
        else
            return value
    }
}
