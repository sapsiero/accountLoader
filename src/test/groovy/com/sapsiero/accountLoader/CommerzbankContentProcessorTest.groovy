package com.sapsiero.accountLoader

import com.sapsiero.accountLoader.mock.MockDkbWebsite
import org.junit.Test
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue
import com.sapsiero.accountLoader.mock.MockCommerzbankWebsite
import org.custommonkey.xmlunit.XMLUnit
import org.custommonkey.xmlunit.XMLTestCase
import org.custommonkey.xmlunit.Diff
import static org.junit.Assert.assertNotNull

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/28/12
 * Time: 1:56 PM
 *
 */
class CommerzbankContentProcessorTest {

    @Test public void debitTest () {
        def mockWebsite = new MockCommerzbankWebsite()

        def processor = new CommerzbankContentProcessor(mockWebsite)
        
        processor.process() { docType, docAttr, doc ->
            assertTrue(docType == Website.DocType.XML)

            XMLUnit.setIgnoreWhitespace(true)
            
            assertNotNull(doc)

            //noinspection GroovyAssignabilityCheck
            def diff = new Diff(doc, replaceDates(mockWebsite.result))

            assert diff.identical()

            assertEquals("COBADEFF", docAttr.bank)
        }
    }

    private String replaceDates(String doc) {
        doc.replaceAll(" date='2012-02-15'", " date='${new Date().format("yyyy-MM-dd")}'")
    }
}
