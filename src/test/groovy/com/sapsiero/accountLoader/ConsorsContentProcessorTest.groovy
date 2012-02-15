package com.sapsiero.accountLoader

import com.sapsiero.accountLoader.mock.MockDkbWebsite
import org.junit.Test
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue
import com.sapsiero.accountLoader.mock.MockConsorsWebsite

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/28/12
 * Time: 1:56 PM
 * To change this template use File | Settings | File Templates.
 */
class ConsorsContentProcessorTest {

    @Test public void debitTest () {
        def mockWebsite = new MockConsorsWebsite(showDebit: true)

        def processor = new ConsorsContentProcessor(mockWebsite)
        
        processor.process { docType, docAttr, doc ->
            assertTrue(docType == Website.DocType.XML)
            assertEquals(replaceDates(mockWebsite.result1), doc)
            assertEquals("CSDBDE71", docAttr.bank)
        }
    }

    @Test public void callMoneyTest () {
        def mockWebsite = new MockConsorsWebsite(showCallMoney: true)

        def processor = new ConsorsContentProcessor(mockWebsite)

        processor.process { docType, docAttr, doc ->
            assertTrue(docType == Website.DocType.XML)
            assertEquals(replaceDates(mockWebsite.result2), doc)
            assertEquals("CSDBDE71", docAttr.bank)
        }
    }

    @Test public void callMoneyDebitTest () {
        def mockWebsite = new MockConsorsWebsite(showDebit: true, showCallMoney: true)

        def processor = new ConsorsContentProcessor(mockWebsite)

        processor.process { docType, docAttr, doc ->
            assertTrue(docType == Website.DocType.XML)
            assertEquals(replaceDates(mockWebsite.result3), doc)
            assertEquals("CSDBDE71", docAttr.bank)
        }
    }
    
    private String replaceDates(String doc) {
        doc.replaceAll("2012-01-29", new Date().format("yyyy-MM-dd"))
    }
}
