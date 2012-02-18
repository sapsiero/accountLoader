package com.sapsiero.accountLoader.mock

import com.sapsiero.accountLoader.Website

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 2/18/12
 * Time: 5:35 PM
 * To change this template use File | Settings | File Templates.
 */
abstract class MockWebsite extends Website {


    protected MockWebsite(com.gargoylesoftware.htmlunit.BrowserVersion version, String targetFolder) {
        super(version, targetFolder)
    }

    protected String getContent(String filename) {
        def clazz = getClass()
        def clazzLoader = clazz.getClassLoader()
        InputStream stream = clazzLoader.getResourceAsStream(filename)

        if (stream == null)
            throw new FileNotFoundException("File ${filename} not part of the classpath.")

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
