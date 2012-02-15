package com.sapsiero.accountLoader.mock

import com.gargoylesoftware.htmlunit.BrowserVersion
import com.sapsiero.accountLoader.Website

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/28/12
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
class MockCommerzbankWebsite extends Website {

    public MockCommerzbankWebsite() {
        super(BrowserVersion.FIREFOX_3_6, '')
    }

    @Override
    void eachDocument(Closure closure) {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("com/sapsiero/accountLoader/mock/mockCommerzbank.xml")
        InputStreamReader reader = new InputStreamReader(stream, "UTF-8")
        
        StringBuilder doc = new StringBuilder()
        
        def line
        while ((line = reader.readLine()) != null) {
            doc.append(line)
            doc.append("\n")
        }
        
        closure.call(Website.DocType.XML, [type: 'debit', account: 'ABCDEFGHI'], doc.toString())
    }
    
    String getResult(){
        InputStream stream = getClass().getClassLoader().getResourceAsStream("com/sapsiero/accountLoader/mock/mockCommerzbankResult.xml")
        InputStreamReader reader = new InputStreamReader(stream, "UTF-8")

        StringBuilder doc = new StringBuilder()

        def line
        while ((line = reader.readLine()) != null) {
            doc.append(line)
            doc.append("\n")
        }

        return doc.toString()
    }
}
