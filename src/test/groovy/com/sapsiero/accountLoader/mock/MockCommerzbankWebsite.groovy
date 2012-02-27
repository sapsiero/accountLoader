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
class MockCommerzbankWebsite extends MockWebsite {

    public MockCommerzbankWebsite() {
        super(BrowserVersion.FIREFOX_3_6, '', new Properties())
    }

    @Override
    void processWebsite(Closure closure) {
        closure.call(Website.DocType.XML, [type: 'debit', account: 'ABCDEFGHI'], getContent("mockCommerzbank.html"))
    }

    @Override
    void processLogout() {

    }
    
    String getResult(){
       return getContent("mockCommerzbankResult.xml")
    }
}
