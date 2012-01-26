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
        resolve("https://banking.dkb.de/dkb/")
        consoleInputByName( "Account Nbr:", "j_username")
        passwordInputByName("Password   :", "j_password")
        clickOnId("buttonlogin")
        clickOnAnchor("/dkb/-?\$part=DkbTransactionBanking.index.menu&node=2&tree=menu&treeAction=selectNode")

        xml.accounts(banknumber: 'BYLADEM1001') {
            clickOnAnchor('/dkb/-?\$part=DkbTransactionBanking.index.menu&node=2.0&tree=menu&treeAction=selectNode')

            //TODO each bank account getElementByName('slBankAccount')
        }
        xml
    }

    @Override
    String buildXml() {
        return "Hallo2"  //To change body of implemented methods use File | Settings | File Templates.
    }
}
