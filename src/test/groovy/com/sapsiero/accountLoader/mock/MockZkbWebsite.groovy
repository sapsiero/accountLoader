package com.sapsiero.accountLoader.mock

import com.gargoylesoftware.htmlunit.BrowserVersion
import com.sapsiero.accountLoader.Website

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/28/12
 * Time: 2:02 PM
 *
 */
class MockZkbWebsite extends MockWebsite {

    public MockZkbWebsite() {
        super(BrowserVersion.FIREFOX_3_6, '', new Properties())
    }

    @Override
    void processWebsite(Closure closure) {
        closure.call(Website.DocType.TEXT, [type: 'debit', account: 'ABCDEFGHI'], getContent("mockZkb.csv"))
    }

    @Override
    void processLogout() {

    }
    
    String getResult(){
        return getContent("mockZkbResult.xml")
    }
}
