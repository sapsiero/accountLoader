package com.sapsiero.accountLoader

import com.gargoylesoftware.htmlunit.BrowserVersion

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/25/12
 * Time: 10:02 PM
 * To change this template use File | Settings | File Templates.
 */
class ZkbWebsite extends Website {

    public ZkbWebsite(String errorFolder) {
        super(BrowserVersion.FIREFOX_3, errorFolder)
        init()
        client.javaScriptEnabled = false
        
        determinePageName = { page ->
            def title = page.querySelector('div[class~="pageTitle"]')
            title.asText().trim()
        }
    }

    @Override
    void eachDocument(Closure closure) {
        resolve("https://onba.zkb.ch")

        consoleInputByName( "Account Nbr:", "vertrag")
        passwordInputByName("Password   :", "passwort")
        clickOnName("SubmitURL|_command=exec|")
        
        if (isPage("Login ZKB Onlinebank")) {
            //TODO wrong password
        }

        passwordInputByName("MTAN       :", "logon2.tan")
        clickOnInputNameContaining("localCommand=logon2")

        if (isPage("Vertrag bereits angemeldet")) {
            log.info("Accepting warning.")
            clickOnInputNameContaining("button=weiter")
        } else {
            log.info("Last time was an complete logout.")
        }

        clickOnAnchorContaining("Kontoauszug")

        def accountNumber = (getElementByXPath('/html/body/div[3]/div/form/div/div/div').getTextContent() =~ /^[0-9\-\.]+/)[0]

        clickOnAnchorContaining("Export (CSV)")

        //def document = loadDocumentOnAnchorContaining()

        save()
    }
}
