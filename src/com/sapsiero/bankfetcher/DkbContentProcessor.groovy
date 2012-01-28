package com.sapsiero.bankfetcher

import groovy.xml.MarkupBuilder
import java.text.NumberFormat

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/28/12
 * Time: 12:19 PM
 * To change this template use File | Settings | File Templates.
 */
class DkbContentProcessor {

    private Website site
    private StringWriter writer
    private MarkupBuilder xml

    public DkbContentProcessor(Website site) {
        this.site = site

        writer = new StringWriter()
        xml = new MarkupBuilder(writer)
    }

    public void process(Closure closure) {
        xml.accounts(banknumber: 'BYLADEM1001') {
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
        closure.call(Website.DocType.XML, [bank: "BYLADEM1001"], writer.toString())
    }

    private void processText(def docAttr, def doc) {
        switch(docAttr.type) {
            case "debit":
                processDebit(docAttr, doc)
                break
            case "credit":
                processCredit(docAttr, doc)
                break
            default:
                break
        }
    }

    private void processDebit(def docAttr, def doc) {
        xml.account(number: docAttr.account, type: 'Cash', currency: 'EUR') {

            doc.split('\n').each() { line ->

                if (line =~ /^\"Kontostand vom:\"/) {
                    def m = line =~ /[-]?[,.0-9]+/

                    balance(date: new Date().format('yyyy-MM-dd'), value: toDouble(m[0]))

                } else if (line =~ /^\"[0-9]{2}\.[0-9]{2}\.[0-9]{4}\"/) {
                    def fields = line.split(';')

                    def val = toDouble(toPlain(fields[7]))
                    def cur = 'EUR'
                    def fee = '0.0'

                    if (fields[4] =~ /[0-9]+KURS[0-9]+/) {
                        val = String.format('%.2f', (toPlain(fields[4]) =~ /[0-9,]+/)[0].replaceAll(',','.').toDouble() * (val.toDouble() / Math.abs(val.toDouble())))
                        cur = (fields[4].plain() =~ /^[A-Z]{3}/)[0]
                        fee = String.format('%.2f', (toPlain(fields[3]) =~ /[0-9,]+$/)[0].replaceAll(',','.').toDouble() * (val.toDouble() / Math.abs(val.toDouble())))
                    }

                    //create transaction
                    transaction(
                            bookingdate: toDate(toPlain(fields[0])),
                            tradedate: toDate(toPlain(fields[1])),
                            message: toPlain(fields[1]) + "; " + toPlain(fields[3]) + "; " + toPlain(fields[4]),
                            value: val,
                            currency: cur,
                            fee: fee,
                            accountvalue: toDouble(toPlain(fields[7]))
                    )
                }
            }
        }
    }

    private void processCredit(def docAttr, def doc) {
        xml.account(number: docAttr.account, type: 'Credit', currency: 'EUR') {


            def feeTracker = []
            doc.split('\n').each(){ line ->
                if (line =~ /^\"(Ja|Nein)\";\"[0-9]{2}\.[0-9]{2}\.[0-9]{4}\"/) {
                    def fields = line.split(';')
                    if ((fields[3] =~ /Auslandseinsatz/)) {
                        feeTracker << toDouble(toPlain(fields[4]))
                    }
                }
            }
            doc.split('\n').each(){ line ->
                if (line =~ /^\"Saldo:\"/) {
                    def m = line =~ /[0-9]+.?[0-9]*/
                    balance(date: new Date().format('yyyy-MM-dd'), value: m[0].replaceAll(',','.').toDouble())
                } else if (line =~ /^\"(Ja|Nein)\";\"[0-9]{2}\.[0-9]{2}\.[0-9]{4}\"/) {
                    def fields = line.split(';')

                    if (!(fields[3] =~ /Auslandseinsatz/)) {

                        def val = toDouble(toPlain(fields[4]))
                        def cur = 'EUR'
                        def fee = '0.0'

                        if (fields[5] != "\"\"") {
                            if (feeTracker.contains(String.format('%.2f', val.toDouble() * 0.0175))) {
                                fee = String.format('%.2f', val.toDouble() * 0.0175)
                                feeTracker.remove(fee)
                            }
                            val = toDouble((toPlain(fields[5]) =~ /[0-9.,\-]+/)[0])
                            cur = (toPlain(fields[5]) =~ /[A-Z]{3}/)[0]
                        }

                        transaction(
                                bookingdate: toDate(toPlain(fields[1])),
                                tradedate: toDate(toPlain(fields[2])),
                                message: toPlain(fields[3]),
                                value: val,
                                currency: cur,
                                fee: fee,
                                accountvalue: String.format('%.2f', toPlain(fields[4]).replaceAll('\\.','').replaceAll(',','.').toDouble() + fee.toDouble())
                        )
                    }
                }
            }

            if (feeTracker.size() != 0)
                throw new Exception("Could not attach all fees: ${feeTracker}")
        }
    }

    private String toDate(String value) {
        String.format('%tF', Date.parse('dd.MM.yyyy', value))
    }

    private String toDouble(String value) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY)
        String.format('%.2f', numberFormat.parse(value).doubleValue())
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
