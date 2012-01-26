package com.sapsiero.bankfetcher

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.IncorrectnessListener
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler
import groovy.xml.MarkupBuilder
import com.gargoylesoftware.htmlunit.Page
import com.gargoylesoftware.htmlunit.html.HtmlPage
import org.apache.log4j.Logger
import com.gargoylesoftware.htmlunit.html.HtmlElement
import com.gargoylesoftware.htmlunit.html.HtmlAnchor

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/25/12
 * Time: 9:53 PM
 * To change this template use File | Settings | File Templates.
 */
abstract class Bank {

    protected WebClient client
    protected MarkupBuilder xml
    private HtmlPage currentPage
    private Logger log = Logger.getLogger(Bank.class)
    String folder

    protected Bank(BrowserVersion version, String targetFolder) {
        this.folder = targetFolder
        client = new WebClient(version)
        client.throwExceptionOnScriptError = false
        client.setIncorrectnessListener(new IncorrectnessListener(){
            void notify(String message, Object origin) {}
        })
        client.setCssErrorHandler(new SilentCssErrorHandler())
        xml = new MarkupBuilder(new StringWriter())
    }

    protected void resolve(String url) {
        try {
            currentPage = client.getPage(url)
            if (currentPage)
                log.info("Page loaded...")
            else {
                log.fatal("Url ${url} did not return a valid page.")
                System.exit(1)
            }
        } catch (UnknownHostException uhe) {
            log.fatal("Url ${url} could not be resolved.")
            System.exit(1)
        }
    }

    protected void consoleInputByName(String consoleText, String name) {
        def value
        if (System.console())
            value = System.console().readLine(consoleText)
        else {
            print consoleText
            value = System.in.newReader().readLine()
        }
        getElementByName(name).valueAttribute = value
    }

    protected void passwordInputByName(String consoleText, String name) {
        def password
        if (System.console())
            password = System.console().readPassword(consoleText)
        else {
            print consoleText
            password = System.in.newReader().readLine()
        }
        getElementByName(name).valueAttribute = password
    }

    protected void clickOnId(String id) {
        currentPage = getElementById(id).click()
    }

    protected void clickOnAnchor(String href) {
        currentPage = getAnchorByHref(href).click()
    }

    private HtmlElement getElementByName(String name) {
        try {
            return currentPage.getElementByName(name)
        } catch (Throwable t) {
            def i = 0
            def file = new File("${folder}currentPage${i++}.html")
            while (file.exists()) {
                file = new File("${folder}currentPage${i++}.html")
            }
            log.fatal(t)
            log.fatal("Writing file to ${file.name}")
            currentPage.save(file)
            log.fatal("Exiting...")
            System.exit(1)
        }
    }

    private HtmlElement getElementById(String id) {
        try {
            return currentPage.getElementById(id)
        } catch (Throwable t) {
            def i = 0
            def file = new File("${folder}currentPage${i++}.html")
            while (file.exists()) {
                file = new File("${folder}currentPage${i++}.html")
            }
            log.fatal(t)
            log.fatal("Writing file to ${file.name}")
            currentPage.save(file)
            log.fatal("Exiting...")
            System.exit(1)
        }
    }

    private HtmlAnchor getAnchorByHref(String href) {
        try {
            return currentPage.getAnchorByHref(href)
        } catch (Throwable t) {
            def i = 0
            def file = new File("${folder}currentPage${i++}.html")
            while (file.exists()) {
                file = new File("${folder}currentPage${i++}.html")
            }
            log.fatal(t)
            log.fatal("Writing file to ${file.name}")
            currentPage.save(file)
            log.fatal("Exiting...")
            System.exit(1)
        }
    }

    abstract String exec()

    abstract String buildXml()

}
