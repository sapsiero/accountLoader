package com.sapsiero.accountLoader

import com.gargoylesoftware.htmlunit.BrowserVersion
import com.sapsiero.accountLoader.exception.ElementNotAvailableException

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/25/12
 * Time: 10:02 PM
 *
 */
class CommerzbankWebsite extends Website {

    public CommerzbankWebsite(Properties properties, String errorFolder) {
        super(BrowserVersion.FIREFOX_3, errorFolder, properties)
        init()
        client.javaScriptEnabled = false

        determinePageName = { page ->
            def title = page.querySelector('span[class~="subhead"]')?.asText()?.trim()
            if (!title) {
                title = page.querySelector('span[class~="tablehead2"]')?.asText().trim()
            }
            title
        }
    }

    @Override
    void processWebsite(Closure closure) {
        resolve("https://www.commerzbanking.de/P-Portal1/XML/ifilportal/pgf.html?Tab=1")

        while(isPage('Anmelden')) {
            consoleInputByName( "Account Nbr:", 'PltLogin_8_txtTeilnehmernummer')
            passwordInputByName("Password   :", 'PltLogin_8_txtPIN')
            clickOnName('PltLogin_8_btnLogin')
        }

        clickOnAnchorContaining("Kontoumsätze")

        eachOptionOfSelectByName('PltViewAccountTransactions_8_STR_KontoNr') { option ->
            setSelectedByName("PltViewAccountTransactions_8_STR_CodeZeitrahmen", "90 Tage", true)

            def xml = getElementByName("PltViewAccountTransactions_8_btnAnzeigen").click().asXml()

            closure.call(Website.DocType.XML, [name: "commerzbank", account: (option =~ /[0-9]+/)[0]], xml)
        }

        clickOnAnchorContaining("Mein Postfach")

        eachElementByXPath('//*[starts-with(@id, "PltInbox_7_DokArtSpalte")]') { link ->
            closure.call(Website.DocType.PDF, [name: link.textContent.trim()], link.click().inputStream)
        }
    }

    @Override
    void processLogout() {
        clickOnXPath("/html/body/form/table/tbody/tr/td[4]/table/tbody/tr/td/input")
    }
}
