package com.sapsiero.accountLoader

import com.sapsiero.accountLoader.mock.MockCommerzbankWebsite
import org.custommonkey.xmlunit.Diff
import org.custommonkey.xmlunit.XMLUnit
import org.junit.Test
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue
import com.sapsiero.accountLoader.mock.MockZkbWebsite

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/28/12
 * Time: 1:56 PM
 *
 */
class ZkbContentProcessorTest {

    @Test public void debitTest () {
        def mockWebsite = new MockZkbWebsite()

        def processor = new ZkbContentProcessor(mockWebsite)
        
        processor.process() { docType, docAttr, doc ->
            assertTrue(docType == Website.DocType.XML)

            XMLUnit.setIgnoreWhitespace(true)

            def diff = new Diff(doc, replaceDates(mockWebsite.result))

            assert diff.identical()

            assertEquals("ZKBKCHZZ80A", docAttr.bank)
        }
    }

    private String replaceDates(String doc) {
        doc.replaceAll("2012-02-08", new Date().format("yyyy-MM-dd"))
    }
}
