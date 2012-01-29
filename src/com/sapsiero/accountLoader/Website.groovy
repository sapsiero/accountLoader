package com.sapsiero.accountLoader

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.IncorrectnessListener
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler
import groovy.xml.MarkupBuilder

import com.gargoylesoftware.htmlunit.html.HtmlPage
import org.apache.log4j.Logger
import com.gargoylesoftware.htmlunit.html.HtmlElement
import com.gargoylesoftware.htmlunit.html.HtmlAnchor
import com.gargoylesoftware.htmlunit.ElementNotFoundException

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/25/12
 * Time: 9:53 PM
 * To change this template use File | Settings | File Templates.
 */
abstract class Website {

    protected WebClient client
    private BrowserVersion version
    protected MarkupBuilder xml
    private HtmlPage currentPage
    protected Logger log = Logger.getLogger(Website.class)
    String folder

    protected Website(BrowserVersion version, String targetFolder) {
        log.debug("creating webclient...")
        this.folder = targetFolder
        this.version = version
        log.debug("...webclient created.")
    }

    protected void init() {
        log.debug("initialising webclient...")
        client = new WebClient(version)
        client.throwExceptionOnScriptError = false
        client.setIncorrectnessListener(new IncorrectnessListener(){
            void notify(String message, Object origin) {}
        })
        client.setCssErrorHandler(new SilentCssErrorHandler())
        log.debug("...initialised.")
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

    protected void eachElementByName(String name, Closure closure) {
        log.debug("iteration elements...")
        def tempPage = currentPage
        getElementsByName(name).eachWithIndex { element, i ->
            log.debug("...element ${i}...")
            closure.call(element)
        }
        currentPage = tempPage
        log.debug("...iteration complete")
    }

    protected void eachElementByTag(String tag, Closure closure) {
        log.debug("iteration elements...")
        def tempPage = currentPage
        getElementsByTag(tag).eachWithIndex { element, i ->
            log.debug("...element ${i}...")
            closure.call(element)
        }
        currentPage = tempPage
        log.debug("...iteration complete")
    }

    protected void eachElementByXPath(String path, Closure closure) {
        log.debug("iteration elements...")
        def tempPage = currentPage
        getElementsByXPath(path).eachWithIndex { element, i ->
            log.debug("...element ${i}...")
            closure.call(element)
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

    protected void setSelectedByName(String name, String attribute, boolean value) {
        log.debug("setting value...")
        getElementByName(name).setSelectedAttribute(attribute, value)
        log.debug("...value set")
    }

    protected void clickOnId(String id) {
        log.debug("clicking id...")
        currentPage = getElementById(id).click()
        log.debug("...clicked")
    }

    protected void clickOnName(String name) {
        log.debug("clicking anchor...")
        currentPage = getElementByName(name).click()
        log.debug("...clicked")
    }

    protected void clickOnAnchor(String href) {
        log.debug("clicking anchor...")
        currentPage = getAnchorByHref(href).click()
        log.debug("...clicked")
    }
    
    protected void clickOnXPath(String path) {
        log.debug("clicking xpath...")
        currentPage = getElementByXPath(path).click()
        log.debug("...clicked")
    }

    protected void clickOnAnchorContaining(String content) {
        def clicked = false
        def tmpPage
        currentPage.getElementsByTagName('a').each() { child ->
            if (child.asXml().contains(content))  {
                tmpPage = child.click()
                clicked = true
            }
        }
        if (clicked) {
            currentPage = tmpPage
        } else {
            def i = 0
            def file = new File("${folder}currentPage${i++}.html")
            while (file.exists()) {
                file = new File("${folder}currentPage${i++}.html")
            }
            log.fatal(new ElementNotFoundException("a", "content", "*${content}*"))
            log.fatal("Writing file to ${file.name}")
            currentPage.save(file)
            log.fatal("Exiting...")
            System.exit(1)
        }
    }

    protected String loadDocumentOnAnchor(String href) {
        log.debug("clicking anchor...")
        def page = getAnchorByHref(href).click()
        log.debug("...clicked")
        page.content
    }

    protected HtmlElement getElementByName(String name) {
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

    protected HtmlElement getElementByTag(String tag) {
        try {
            return currentPage.getElementsByTagName(tag)[0]
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

    protected HtmlElement[] getElementsByTag(String tag) {
        try {
            return currentPage.getElementsByTagName(tag)
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

    protected HtmlElement[] getElementsByName(String name) {
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

    protected HtmlElement getElementById(String id) {
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
    
    protected HtmlElement getElementByXPath(String path){
        try {
            return currentPage.getFirstByXPath(path)
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

    protected HtmlElement[] getElementsByXPath(String path){
        try {
            return currentPage.getByXPath(path)
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

    protected HtmlAnchor getAnchorByHref(String href) {
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

    protected void setJsEnabled(boolean enabled) {
        client.setJavaScriptEnabled(enabled)
    }

    public enum DocType {
        HTML, XML, TEXT, PDF
    }

    abstract void eachDocument(Closure closure)

}
