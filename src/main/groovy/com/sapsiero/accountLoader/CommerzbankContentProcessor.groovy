package com.sapsiero.accountLoader

import groovy.xml.MarkupBuilder
import java.text.NumberFormat

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/28/12
 * Time: 12:19 PM
 *
 */
class CommerzbankContentProcessor implements ContentProcessor {

    private Website site
    private StringWriter writer
    private MarkupBuilder xml

    public CommerzbankContentProcessor(Website site) {
        this.site = site

        writer = new StringWriter()
        xml = new MarkupBuilder(writer)
    }

    public void process(Closure closure) {
        xml.accounts(banknumber: 'COBADEFF') {
            site.eachDocument { docType, docAttr, doc ->
                switch (docType) {
                    case Website.DocType.XML:
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
        closure.call(Website.DocType.XML, [name: "commerzbank", bank: "COBADEFF"], writer.toString())
    }

    private void processText(def docAttr, def doc) {
        xml.account(number: docAttr.account, type: 'Cash') {

            def slurper = new XmlSlurper().parseText(doc)

            def balanceText = slurper.body.table[2].tbody.tr.td[1].table[1].form.tbody.tr[5].td.table.tbody.tr[5].td[2].span.text().trim()
            def balanceValue = (balanceText =~ /[0-9.,]+/)[0]

            balance(date: new Date().format('yyyy-MM-dd'), value: toDouble(balanceValue+balanceText[-1]))

            slurper.body.table[2].tbody.tr.td[1].table[1].form.tbody.tr[8].td.table.tbody.tr.eachWithIndex { row, i ->

                if(row.td[1].span && i > 0) {
                    def bookingDate = row.td[2].span.text().trim()
                    def text = row.td[6].span.text().split("\n").collect { line -> line.trim() }.findAll { line -> line.length() > 0 }.join("\n")
                    def valueDate = row.td[9].span.text().trim()
                    def value = row.td[12].span.text().trim()

                    try {
                        transaction(bookingdate: toDate(bookingDate), tradedate: toDate(valueDate), message: text[0..-3], value: toDouble(value))
                    } catch (Exception e) {}
                }
            }
        }
    }

    private String toDate(String value) {
        String.format('%tF', Date.parse('dd.MM.yyyy', value))
    }

    private String toDouble(String value) {
        value = value.replace('.','')
        value = value.replace(',','.')
        if (value.length() > 1)
            String.format('%.2f', value[0..-2].toDouble() * (value[-1] == '-'? -1 : 1))
        else
            String.format('%.2f', value[0].toDouble())

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
