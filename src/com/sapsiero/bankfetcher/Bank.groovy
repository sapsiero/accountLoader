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
    private StringWriter writer
    protected Logger log = Logger.getLogger(Bank.class)
    String folder

    protected Bank(BrowserVersion version, String targetFolder) {
        log.debug("creating webclient...")
        this.folder = targetFolder
        client = new WebClient(version)
        client.throwExceptionOnScriptError = false
        client.setIncorrectnessListener(new IncorrectnessListener(){
            void notify(String message, Object origin) {}
        })
        client.setCssErrorHandler(new SilentCssErrorHandler())
        writer = new StringWriter()
        xml = new MarkupBuilder(writer)
        log.debug("...webclient created.")
    }

    protected void resolve(String url) {
        log.debug("resolving url ${url}...")
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
        log.debug("...resolved")
    }

    protected void consoleInputByName(String consoleText, String name) {
        log.debug("requesting input...")
        def value
        if (System.console())
            value = System.console().readLine(consoleText)
        else {
            print consoleText
            value = System.in.newReader().readLine()
        }
        getElementByName(name).valueAttribute = value
        log.debug("...input set")
    }

    protected void passwordInputByName(String consoleText, String name) {
        log.debug("requesting password...")
        def password
        if (System.console())
            password = System.console().readPassword(consoleText)
        else {
            print consoleText
            password = System.in.newReader().readLine()
        }
        getElementByName(name).valueAttribute = password
        log.debug("...password set")
    }

    protected void eachOptionOfSelectByName(String name, Closure closure) {
        log.debug("iteration options...")
        def tempPage = currentPage
        getElementByName(name).getHtmlElementsByTagName('option').each { option ->
            log.debug("...option ${option.text}...")
            option.selected = true
            closure.call(option.text)
        }
        currentPage = tempPage
        log.debug("...iteration complete")
    }

    protected void setRadioButtonByName(String name, String value) {
        log.debug("setting radio button...")
        def set = false
        getElementsByName(name).each { radio ->
            if (radio.valueAttribute == value) {
                radio.checked = true
                set = true
            }
        }
        log.debug("...radiobutton ${(set?"":"NOT ")}set")
    }

    protected void setValueByName(String name, String value) {
        log.debug("setting value...")
        getElementByName(name).valueAttribute = value
        log.debug("...value set")
    }

    protected void clickOnId(String id) {
        log.debug("clicking id...")
        currentPage = getElementById(id).click()
        log.debug("...clicked")
    }

    protected void clickOnAnchor(String href) {
        log.debug("clicking anchor...")
        currentPage = getAnchorByHref(href).click()
        log.debug("...clicked")
    }

    protected String loadDocumentOnAnchor(String href) {
        log.debug("clicking anchor...")
        def page = getAnchorByHref(href).click()
        log.debug("...clicked")
        page.content
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

    private HtmlElement[] getElementsByName(String name) {
        try {
            return currentPage.getElementsByName(name)
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

    protected void save() {
        def i = 0
        def file = new File("${folder}currentPage${i++}.html")
        while (file.exists()) {
            file = new File("${folder}currentPage${i++}.html")
        }
        log.info("Writing file to ${file.name}")
        currentPage.save(file)
        log.info("Exiting...")
    }

    protected String getXmlResult() {
        writer.toString()
    }

    abstract String exec()

}
