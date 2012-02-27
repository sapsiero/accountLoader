package com.sapsiero.accountLoader

import com.gargoylesoftware.htmlunit.BrowserVersion

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/25/12
 * Time: 10:02 PM
 *
 */
class ZkbWebsite extends Website {

    public ZkbWebsite(Properties properties, String errorFolder) {
        super(BrowserVersion.FIREFOX_3, errorFolder, properties)
        init()
        client.javaScriptEnabled = false
        
        determinePageName = { page ->
            def title = page.querySelector('div[class~="pageTitle"]').asText().trim()
            def errors = page.querySelector('div[class~="globalErrorLine"]').collect { text -> text.asText().trim() }.join(" - ")
            "${title} (${errors})"
        }
    }

    @Override
    void processWebsite(Closure closure) {
        resolve("https://onba.zkb.ch")

        consoleInputByName( "Account Nbr:", "vertrag")
        passwordInputByName("Password   :", "passwort")
        clickOnName("SubmitURL|_command=exec|")
        
        while (isPage("Login ZKB Onlinebank (Anmeldung fehlerhaft. Bitte überprüfen Sie Ihre Eingaben.)")) {
            consoleInputByName( "Account Nbr:", "vertrag")
            passwordInputByName("Password   :", "passwort")
            clickOnName("SubmitURL|_command=exec|")
        }

        passwordInputByName("MTAN       :", "logon2.tan")
        clickOnInputNameContaining("localCommand=logon2")
        
        while (isPage('Login ZKB Onlinebank (Die eingegebene TAN ist falsch. Bitte überprüfen Sie Ihre Eingabe.)')) {
            passwordInputByName("MTAN       :", "logon2.tan")
            clickOnInputNameContaining("localCommand=logon2")
        }

        if (isPage("Vertrag bereits angemeldet ()")) {
            log.info("Accepting warning.")
            clickOnInputNameContaining("button=weiter")
        } else {
            log.info("Last time was an complete logout.")
        }

        clickOnAnchorContaining("Kontoauszug")

        def accountNumber = (getElementByXPath('/html/body/div[4]/div/form/div/div/div/div').getTextContent() =~ /^[0-9\-\.]+/)[0]
        clickOnAnchorContaining("Export (CSV)")

        def document = loadDocumentOnInputNameContaining("mitDetails=true")

        closure.call(Website.DocType.TEXT, [type: 'debit', account: accountNumber], document)

        ["Bankbelege"].each { docType ->
            clickOnAnchorContaining(docType)

            eachElementByXPath('/html/body/div[4]/div/form/table/tbody/tr') { tableRow ->

                if (tableRow.getAttribute('class').startsWith('OnbaTableRowBgColor') && tableRow.getByXPath('td[4]')[0].asText() != "gelesen" ) {

                    def tag = tableRow.getByXPath('td[2]/a')[0]
                    def filename = tag.asText().trim().toLowerCase().replaceAll(' ', '_')

                    document = clickOnAnchor(tag.hrefAttribute)  {
                        getElementsByXPath('/html/body/div[4]/div/form/table/tbody/tr') { innerTableRow ->
                            if (innerTableRow.getAttribute('class').startsWith('OnbaTableRowBgColor')) {
                                def tag2 = innerTableRow.getByXPath('td[1]/a') [0]
                                def filename2 = tag2.asText().trim().toLowerCase().replaceAll(' ', '_')

                                document = tag2.click()

                                closure.call(Website.DocType.PDF, [name: filename2], document.inputStream)
                            }
                        }
                    }
                    if (document) {
                        closure.call(Website.DocType.PDF, [name: filename], document.inputStream)
                    } else {
                        log.warn("Nothing returned")
                    }
                }

            }
        }
    }

    @Override
    void processLogout(){
        clickOnXPath('/html/body/div/div[2]/div/strong/a')
    }
}
