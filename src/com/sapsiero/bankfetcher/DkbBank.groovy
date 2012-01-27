package com.sapsiero.bankfetcher

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.IncorrectnessListener
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/25/12
 * Time: 10:02 PM
 * To change this template use File | Settings | File Templates.
 */
class DkbBank extends Bank {

    public DkbBank() {
        super(BrowserVersion.FIREFOX_3, "/home/tim/Documents/Bank/Dkb/${new Date().format('yyyyMMdd')}/")
        client.javaScriptEnabled = false
    }

    @Override
    String exec() {
        String.metaClass.plain = {
            if (delegate[-1] == "\"" && delegate[0] == "\"")
                if (delegate.length() == 2)
                    return ""
                else
                    return delegate[1..-2]
            else
                return delegate
        }
        String.metaClass.date = {
            String.format('%tF', Date.parse('dd.MM.yyyy', delegate))
        }
        String.metaClass.double = {
            String.format('%.2f', delegate.replaceAll('\\.','').replaceAll(',','.').toDouble())
        }

        resolve("https://banking.dkb.de/dkb/")

        consoleInputByName( "Account Nbr:", "j_username")
        passwordInputByName("Password   :", "j_password")
        clickOnId("buttonlogin")

        clickOnAnchor("/dkb/-?\$part=DkbTransactionBanking.index.menu&node=2&tree=menu&treeAction=selectNode")

        xml.accounts(banknumber: 'BYLADEM1001') {

            clickOnAnchor('/dkb/-?\$part=DkbTransactionBanking.index.menu&node=2.0&tree=menu&treeAction=selectNode')

            eachOptionOfSelectByName("slBankAccount") { optionText ->
                setRadioButtonByName("searchPeriodRadio", "1")
                setValueByName("transactionDate", (new Date() - 150).format('dd.MM.yyyy'))
                setValueByName("toTransactionDate", new Date().format('dd.MM.yyyy'))
                clickOnId("searchbutton")

                account(number: (optionText =~ /[0-9]+/)[0], type: 'Cash', currency: 'EUR') {
                    def content = loadDocumentOnAnchor("/dkb/-?\$part=DkbTransactionBanking.content.banking.Transactions.Search&\$event=csvExport")

                    content.split('\n').each() { line ->
                        if (line =~ /^\"Kontostand vom:\"/) {
                            def m = line =~ /[-]?[,.0-9]+/
                            balance(date: new Date().format('yyyy-MM-dd'), value: m[0].double())
                        } else if (line =~ /^\"[0-9]{2}\.[0-9]{2}\.[0-9]{4}\"/) {
                            def fields = line.split(';')

                            def val = fields[7].plain().double()
                            def cur = 'EUR'
                            def fee = '0.0'

                            if (fields[4] =~ /[0-9]+KURS[0-9]+/) {
                                val = String.format('%.2f', (fields[4].plain() =~ /[0-9,]+/)[0].replaceAll(',','.').toDouble() * (val.toDouble() / Math.abs(val.toDouble())))
                                cur = (fields[4].plain() =~ /^[A-Z]{3}/)[0]
                                fee = String.format('%.2f', (fields[3].plain() =~ /[0-9,]+$/)[0].replaceAll(',','.').toDouble() * (val.toDouble() / Math.abs(val.toDouble())))
                            }

                            //create transaction
                            transaction(
                                    bookingdate: fields[0].plain().date(),
                                    tradedate: fields[1].plain().date(),
                                    message: fields[2].plain() + "; " + fields[3].plain() + "; " + fields[4].plain(),
                                    value: val,
                                    currency: cur,
                                    fee: fee,
                                    accountvalue: fields[7].plain().double()
                            )
                        }
                    }
                }
            }

            clickOnAnchor('/dkb/-?$part=DkbTransactionBanking.index.menu&node=2.1&tree=menu&treeAction=selectNode')

            eachOptionOfSelectByName("slCreditCard") { optionText ->
                setRadioButtonByName("searchPeriod", "0")
                setValueByName("postingDate", (new Date() - 150).format('dd.MM.yyyy'))
                setValueByName("toPostingDate", new Date().format('dd.MM.yyyy'))
                clickOnId("searchbutton")

                account(number: (optionText =~ /[0-9\*]+/)[0], type: 'Credit', currency: 'EUR') {
                    def content = loadDocumentOnAnchor("/dkb/-?\$part=DkbTransactionBanking.content.creditcard.CreditcardTransactionSearch&\$event=csvExport")
                    
                    def feeTracker = []
                    content.split('\n').each(){ line ->
                        if (line =~ /^\"(Ja|Nein)\";\"[0-9]{2}\.[0-9]{2}\.[0-9]{4}\"/) {
                            def fields = line.split(';')
                            if ((fields[3] =~ /Auslandseinsatz/)) {
                                feeTracker << fields[4].plain().double()
                            }
                        }
                    }
                    content.split('\n').each(){ line ->
                        if (line =~ /^\"Saldo:\"/) {
                            def m = line =~ /[0-9]+.?[0-9]*/
                            balance(date: new Date().format('yyyy-MM-dd'), value: m[0].replaceAll(',','.').toDouble())
                        } else if (line =~ /^\"(Ja|Nein)\";\"[0-9]{2}\.[0-9]{2}\.[0-9]{4}\"/) {
                            def fields = line.split(';')

                            if (!(fields[3] =~ /Auslandseinsatz/)) {

                                def val = fields[4].plain().double()
                                def cur = 'EUR'
                                def fee = '0.0'

                                if (fields[5] != "\"\"") {
                                    if (feeTracker.contains(String.format('%.2f', val.toDouble() * 0.0175))) {
                                        fee = String.format('%.2f', val.toDouble() * 0.0175)
                                        feeTracker.remove(fee)
                                    }
                                    val = (fields[5].plain() =~ /[0-9.,\-]+/)[0].double()
                                    cur = (fields[5].plain() =~ /[A-Z]{3}/)[0]
                                }

                                transaction(
                                        bookingdate: fields[1].plain().date(),
                                        tradedate: fields[2].plain().date(),
                                        message: fields[3].plain(),
                                        value: val,
                                        currency: cur,
                                        fee: fee,
                                        accountvalue: String.format('%.2f', fields[4].plain().replaceAll('\\.','').replaceAll(',','.').toDouble() + fee.toDouble())
                                )
                            }
                        }
                    }

                    if (feeTracker.size() != 0)
                        throw new Exception("Could not attach all fees: ${feeTracker}")
                }
                
            }
        }

        //TODO Documents
        xmlResult
    }
}
