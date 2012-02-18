package com.sapsiero.accountLoader

import groovy.xml.MarkupBuilder

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/28/12
 * Time: 12:19 PM
 *
 */
class ConsorsContentProcessor implements ContentProcessor {

    private Website site
    private StringWriter writer
    private MarkupBuilder xml

    public ConsorsContentProcessor(Website site) {
        this.site = site

        writer = new StringWriter()
        xml = new MarkupBuilder(writer)
    }

    public void process(Closure closure) {
        xml.accounts(banknumber: 'CSDBDE71') {
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
        closure.call(Website.DocType.XML, [name: "consors", bank: "CSDBDE71"], writer.toString())
    }

    private void processText(def docAttr, def doc) {
        xml.account(number: docAttr.account, type: docAttr.type) {
            def firstLine = true
            doc.eachLine() { line ->
                if (firstLine) {
                    def m = line =~ /([0-9\.,-]+)/
                    balance(date: new Date().format('yyyy-MM-dd'), value: toDouble(m[1][1]))
                    firstLine = false
                } else {
                    def tabs = line.split(';')

                    def txt = '';

                    tabs[2..4].each() {
                        if (it != '')
                            txt += it +  '; '
                    }

                    transaction(bookingdate: toDate(tabs[0]), tradedate: toDate(tabs[1]), message: "${txt[0..-3]}", value: toDouble(tabs[5]))
                }
            }
        }
    }

    private String toDate(String value) {
        String.format('%tF', Date.parse('dd.MM.yyyy', value))
    }

    private String toDouble(String value) {
        if (value =~ /^\+/) {
            value = value[1..-1]
        }
        value = value.replace('.','')
        value = value.replace(',','.')
        String.format('%.2f', value.toDouble())

    }

    private String toPlain(String value) {
        if (value[-1] == "\"" && value[0] == "\"")
            if (value.length() == 2)
                return ""
            else
                return value[1..-2]
        else
            return value
    }
}
