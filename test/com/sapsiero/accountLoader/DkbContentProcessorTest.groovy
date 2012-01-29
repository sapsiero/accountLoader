package com.sapsiero.accountLoader

import org.junit.Test
import static org.junit.Assert.*
import com.sapsiero.accountLoader.mock.MockDkbWebsite

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/28/12
 * Time: 1:56 PM
 * To change this template use File | Settings | File Templates.
 */
class DkbContentProcessorTest {

    @Test public void debitTest () {
        def mockWebsite = new MockDkbWebsite(showDebit: true)

        def processor = new DkbContentProcessor(mockWebsite)
        
        processor.process { docType, docAttr, doc ->
            assertTrue(docType == Website.DocType.XML)
            assertEquals(doc, replaceDates(mockWebsite.result1))
            assertEquals(docAttr.bank, "BYLADEM1001")
        }
    }

    @Test public void creditTest () {
        def mockWebsite = new MockDkbWebsite(showCredit: true)

        def processor = new DkbContentProcessor(mockWebsite)

        processor.process { docType, docAttr, doc ->
            assertTrue(docType == Website.DocType.XML)
            assertEquals(doc, replaceDates(mockWebsite.result2))
            assertEquals(docAttr.bank, "BYLADEM1001")
        }
    }

    @Test public void creditDebitTest () {
        def mockWebsite = new MockDkbWebsite(showCredit: true, showDebit: true)

        def processor = new DkbContentProcessor(mockWebsite)

        processor.process { docType, docAttr, doc ->
            assertTrue(docType == Website.DocType.XML)
            assertEquals(doc, replaceDates(mockWebsite.result3))
            assertEquals(docAttr.bank, "BYLADEM1001")
        }
    }

    @Test public void creditWithInternationalTest () {
        def mockWebsite = new MockDkbWebsite(showCreditWithInternational: true)

        def processor = new DkbContentProcessor(mockWebsite)

        processor.process { docType, docAttr, doc ->
            assertTrue(docType == Website.DocType.XML)
            assertEquals(doc, replaceDates(mockWebsite.result4))
            assertEquals(docAttr.bank, "BYLADEM1001")
        }
    }

    @Test(expected=ContentProcessException)
    public void creditWithInternationalErrorTest () {
        def mockWebsite = new MockDkbWebsite(showCreditWithInternationalError: true)

        def processor = new DkbContentProcessor(mockWebsite)

        processor.process { docType, docAttr, doc ->
        }
    }
    
    private String replaceDates(String doc) {
        doc.replaceAll("2012-01-28", new Date().format("yyyy-MM-dd"))
    }
}
