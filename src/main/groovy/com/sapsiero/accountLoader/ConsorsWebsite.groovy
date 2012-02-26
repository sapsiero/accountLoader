package com.sapsiero.accountLoader

import com.gargoylesoftware.htmlunit.BrowserVersion

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/25/12
 * Time: 10:02 PM
 *
 */
class ConsorsWebsite extends Website {

    public ConsorsWebsite(String errorFolder) {
        super(BrowserVersion.INTERNET_EXPLORER_8, errorFolder)
        init()
        client.javaScriptEnabled = false
    }

    @Override
    void eachDocument(Closure closure) {
        resolve("https://www.cortalconsors.de/euroWebDe/-")

        clickOnId("login-button-nojs")

        consoleInputByName( "Account Nbr:", 'userId')
        passwordInputByName("Password   :", 'nip')
        clickOnName('$$event_login')

        clickOnAnchorContaining("Verrechnungskonto")

        eachElementByXPath("/html/body/div[2]/div[6]/div[2]/div[2]/form/div/ul/li/a") { anchor ->
            def accountText = anchor.asText().trim()

            println accountText

            clickOnAnchor(anchor.hrefAttribute)

            setValueByName('filterStartDate', (new Date() - 90).format('dd.MM.yyyy'))
            clickOnName('$$event_show')

            def content = loadDocumentOnAnchor('/euroWebDe/-?$part=MonalisaDE.Desks.Accounts.Desks.CashTurnoverInq.content.main.tabFrame.cashTurnoverInq&$event=save&selectedFileFormat=csv')

            def accountType
            if (accountText.contains('Tagesgeldkonto')) {
                accountType = 'callmoney'
            } else {
                accountType = 'debit'
            }

            def accountNbr = (accountText =~ /[0-9]+/)[0]

            closure.call(Website.DocType.TEXT, [name: "consors", account: accountNbr, type: accountType], content)
        }

        clickOnAnchor('/euroWebDe/-?$part=MonalisaDE.Desks.OnlineArchive.content.main.accountTabs&$event=navigation')

        setValueByName("dateFrom", (new Date() - 90).format('dd.MM.yyyy'))

        clickOnXPath("//input[@src='/euroWebDe/images/euroPort/design/de/buttons/bt_search.gif']")

        eachElementByXPath("//img[@alt='ungelesen']") { documentLink ->
            def title = documentLink.getFirstByXPath('./../../../../td[3]/a').asText().replaceAll(' ','_').replaceAll('/','')
            def pdfDocument = documentLink.getEnclosingElement('a').click()
            closure.call(Website.DocType.PDF, [name: title], pdfDocument.inputStream)
        }

        clickOnName('$$event_logout')
    }
}
