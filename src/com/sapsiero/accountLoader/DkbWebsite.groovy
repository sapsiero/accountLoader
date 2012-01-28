package com.sapsiero.accountLoader

import com.gargoylesoftware.htmlunit.BrowserVersion

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/25/12
 * Time: 10:02 PM
 * To change this template use File | Settings | File Templates.
 */
class DkbWebsite extends Website {

    public DkbWebsite(String errorFolder) {
        super(BrowserVersion.FIREFOX_3, errorFolder)
        init()
        client.javaScriptEnabled = false
    }

    @Override
    void eachDocument(Closure closure) {
        resolve("https://banking.dkb.de/dkb/")

        consoleInputByName( "Account Nbr:", "j_username")
        passwordInputByName("Password   :", "j_password")
        clickOnId("buttonlogin")

        clickOnAnchor("/dkb/-?\$part=DkbTransactionBanking.index.menu&node=2&tree=menu&treeAction=selectNode")

        clickOnAnchor('/dkb/-?\$part=DkbTransactionBanking.index.menu&node=2.0&tree=menu&treeAction=selectNode')

        eachOptionOfSelectByName("slBankAccount") { optionText ->
            setRadioButtonByName("searchPeriodRadio", "1")
            setValueByName("transactionDate", (new Date() - 150).format('dd.MM.yyyy'))
            setValueByName("toTransactionDate", new Date().format('dd.MM.yyyy'))
            clickOnId("searchbutton")

            def content = loadDocumentOnAnchor("/dkb/-?\$part=DkbTransactionBanking.content.banking.Transactions.Search&\$event=csvExport")
            closure.call(Website.DocType.TEXT, [type: 'debit', account: (optionText =~ /[0-9]+/)[0]], content)
        }

        clickOnAnchor('/dkb/-?$part=DkbTransactionBanking.index.menu&node=2.1&tree=menu&treeAction=selectNode')

        eachOptionOfSelectByName("slCreditCard") { optionText ->
            setRadioButtonByName("searchPeriod", "0")
            setValueByName("postingDate", (new Date() - 150).format('dd.MM.yyyy'))
            setValueByName("toPostingDate", new Date().format('dd.MM.yyyy'))
            clickOnId("searchbutton")

            def content = loadDocumentOnAnchor("/dkb/-?\$part=DkbTransactionBanking.content.creditcard.CreditcardTransactionSearch&\$event=csvExport")
            closure.call(Website.DocType.TEXT, [type: 'credit', account: (optionText =~ /[0-9\*]+/)[0]], content)
        }

        jsEnabled = true
        clickOnAnchor('/dkb/-?$part=Postbox')

        def map = ['Konto' : '1', 'Kredit' : '2']
        map.keySet().each { typ ->
            eachElementByTag("strong") { strongElements ->
                log.info(strongElements)
                setSelectedByName("slEsafeFolders", map[typ], true)

                strongElements.getElementsByTagName('a').each { pdf ->
                    if (pdf.textContent.contains(typ)) {
                        pdf.getEnclosingElement('tr').getHtmlElementsByTagName('input')[0].checked = true
                        def document = pdf.click()
                        closure.call(Website.DocType.PDF, [name: pdf.textContent.trim()], document.inputStream)
                    }

                    jsEnabled = false
                    getElementByTag("body").getOneHtmlElementByAttribute('input', 'title', '''In die 'Dokumentenmappe' kopieren''').click()
                    jsEnabled = true
                }
            }
        }

        clickOnAnchor('/dkb/-?$part=Postbox.index.menu&node=1&tree=menu&treeAction=selectNode')

        eachElementByTag("strong") { strongElements ->
            setSelectedByName("slEsafeFolders", "0", true)

            strongElements.getElementsByTagName('a').each { pdf ->
                if (pdf.textContent.contains(typ)) {
                    pdf.getEnclosingElement('tr').getHtmlElementsByTagName('input')[0].checked = true
                    def document = pdf.click()
                    closure.call(Website.DocType.PDF, [name: pdf.textContent.trim()], document.inputStream)
                }

                jsEnabled = false
                getElementByTag("body").getOneHtmlElementByAttribute('input', 'title', '''In die 'Dokumentenmappe' kopieren''').click()
                jsEnabled = true
            }
        }

        clickOnAnchor('/dkb/-?$part=Postbox.login-status&$event=logout')
    }
}
