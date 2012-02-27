package com.sapsiero.accountLoader

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.IncorrectnessListener
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler

import com.gargoylesoftware.htmlunit.html.HtmlPage
import org.apache.log4j.Logger
import com.gargoylesoftware.htmlunit.html.HtmlElement
import com.gargoylesoftware.htmlunit.html.HtmlAnchor
import com.gargoylesoftware.htmlunit.ElementNotFoundException
import com.sapsiero.accountLoader.exception.StepExecutionException
import com.sapsiero.accountLoader.exception.ValueNotAvailableException
import com.sapsiero.accountLoader.exception.ElementNotAvailableException
import groovyjarjarasm.asm.tree.TryCatchBlockNode

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/25/12
 * Time: 9:53 PM
 *
 */
abstract class Website {

    /**
     * current web client
     */
    protected WebClient client
    /**
     * browser version of request
     */
    private BrowserVersion version
    /**
     * current page
     */
    private HtmlPage currentPage
    /**
     * default logger
     */
    protected Logger log = Logger.getLogger(Website.class)
    /**
     * folder to store files in
     */
    private String folder
    /**
     * closure that is able to determine a unique page name of an HTML page
     */
    protected Closure determinePageName

    /**
     * Configuration parameter
     */
    private Properties properties

    /**
     * Standard constructor.
     * @param version BrowserVersion of the request
     * @param targetFolder Folder to store the data in
     */
    protected Website(BrowserVersion version, String targetFolder, Properties props) {
        log.debug("creating webclient...")
        this.folder = targetFolder
        this.version = version
        this.properties = props
        log.debug("...webclient created.")
    }

    /**
     * Initialise the web client. This is initialising the underlying WebClient.
     */
    protected void init() {
        log.debug("initialising webclient...")
        client = new WebClient(version)
        client.throwExceptionOnScriptError = false
        client.setIncorrectnessListener(new IncorrectnessListener(){
            public void notify(String message, Object origin) {}
        })
        client.setCssErrorHandler(new SilentCssErrorHandler())
        log.debug("...initialised.")
    }

    /**
     * Checks wether a page has a particular id by using the detemerinePageName closure.
     * @param name Checks for this page name.
     * @return if a page name matches the given page name.
     */
    protected boolean isPage(def name) {
        def pageName = determinePageName.call(currentPage)
        log.info("Currently on page '${pageName}'")
        name == pageName
    }

    /**
     * Connects the website execution to a particular url.
     * @param url Url to connect to.
     * @throws StepExecutionException exception, if url could not be resolved.
     */
    protected void resolve(String url) throws StepExecutionException {
        log.debug("resolving url '${url}'...")
        try {
            currentPage = client.getPage(url)
            if (currentPage) {
                log.info("...page loaded")
                logPageName()
            } else {
                log.fatal("Url '${url}' did not return a valid page.")
                throw new StepExecutionException("Url ${url} did not return a valid page.")
            }
        } catch (UnknownHostException uhe) {
            log.fatal("Url '${url}' could not be resolved.")
            throw new StepExecutionException("Url ${url} could not be resolved.", uhe)
        }
        log.debug("...resolved")
    }

    /**
     * Reqests an user input and adds it to an input element determined by name.
     * @param consoleText Test is printed.
     * @param name Name of input element.
     */
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

    /**
     * Reqests an user input and adds it to an input element determined by name. In case the system console
     * available, this input field does not echo the input.
     * @param consoleText Test is printed.
     * @param name Name of input element.
     */
    protected void passwordInputByName(String consoleText, String name) {
        log.debug("requesting password...")
        def password
        if (System.console())
            password = System.console().readPassword(consoleText).toString()
        else {
            print consoleText
            password = System.in.newReader().readLine()
        }
        getElementByName(name).valueAttribute = password
        log.debug("...password set")
    }

    /**
     * Iterates through all option elements of a named select element.
     * @param name Name of select attribute.
     * @param closure Closure to be executed. Parameter of closure is the text content of the current option.
     */
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

    /**
     * Iterates through all elements with the same name.
     * @param name Name attribute of elements.
     * @param closure Closure to be executed. Parameter of the closure is the current html element.
     */
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

    /**
     * Iterates through all elements with the same tag.
     * @param tag Tag of elements to be iterated through.
     * @param closure Closure to be executed. Parameter of the closure is the current html element.
     */
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

    /**
     * Iterates through all elements referenced by an XPath expression.
     * @param path XPath that is used to determine the elements.
     * @param closure Closure to be executed. Parameter of the closure is the current html element.
     */
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

    /**
     * Sets a named radio button to a specified value.
     * @param name Name attribute of the radio element.
     * @param value Value to which the element is set.
     */
    protected void setRadioButtonByName(String name, String value) throws ValueNotAvailableException {
        log.debug("setting radio button...")
        def values = []
        def set = false

        getElementsByName(name).each { radio ->
            values << radio.valueAttribute
            if (radio.valueAttribute == value) {
                radio.checked = true
                set = true
            }
        }
        if(!set) {
            throw new ValueNotAvailableException("Value '${value}' not available in radio button '${name}'.", values)
        }

        log.debug("...radiobutton ${(set?"":"NOT ")}set")
    }

    /**
     * Sets the value of an input element to a particular value.
     * @param name Name of the element.
     * @param value Value to set the elements value attribute to.
     */
    protected void setValueByName(String name, String value) {
        log.debug("setting value...")
        getElementByName(name).valueAttribute = value
        log.debug("...value set")
    }

    /**
     * Sets the selected option of a selection element.
     * @param name Name of the selection element.
     * @param attribute Option element of the selection element.
     * @param value Boolean to set or unset the option element.
     */
    protected void setSelectedByName(String name, String attribute, boolean value) {
        log.debug("setting value...")
        getElementByName(name).setSelectedAttribute(attribute, value)
        log.debug("...value set")
    }

    /**
     * Clicks on the element identified by its id attribute and moves the current page pointer to this page.
     * @param id Id of the element.
     */
    protected void clickOnId(String id) {
        log.debug("clicking id...")
        currentPage = getElementById(id).click()
        logPageName()
        log.debug("...clicked")
    }

    /**
     * Clicks on the anchor element identified by its href attribute and executes the given closure without moving
     * the current page pointer.
     * @param href Href attribute of the anchor element.
     * @param closure Closure to be executed. This closure is only executed if the page following the click is a Html
     * Page. No parameter are passed.
     * @return If the anchor leads to an UnexpectedPage (e.g. text document), this is returned.
     */
    protected def clickOnAnchor(String href, Closure closure) {
        log.debug("clicking anchor...")
        def tmpPage = currentPage
        def checkPage = getAnchorByHref(href).click()
        if (checkPage instanceof HtmlPage) {
            currentPage = checkPage
            closure.call()
            currentPage = tmpPage
            logPageName()
            null
        } else {
            log.debug("...clicked without finding new HTMLPage")
            log.debug("returning ${checkPage.class}")
            checkPage
        }
    }

    /**
     * Clicks on the element identified by its name attribute and moves the current page pointer to this page.
     * @param name Name attribute of the element.
     */
    protected void clickOnName(String name) {
        log.debug("clicking anchor...")
        currentPage = getElementByName(name).click()
        logPageName()
        log.debug("...clicked")
    }

    /**
     * Clicks on the anchor element identified by its href attribute and moves
     * the current page pointer.
     * @param href Href attribute of the anchor element.
     */
    protected void clickOnAnchor(String href) {
        log.debug("clicking anchor...")
        currentPage = getAnchorByHref(href).click()
        logPageName()
        log.debug("...clicked")
    }

    /**
     * Clicks on the anchor element identified by the given XPath expression and moves
     * the current page pointer.
     * @param path XPath expression defining the anchor.
     */
    protected void clickOnXPath(String path) {
        log.debug("clicking xpath...")
        currentPage = getElementByXPath(path).click()
        logPageName()
        log.debug("...clicked")
    }

    /**
     * Clicks on the anchor element identified by the part of the html content of this element.
     * @param content Part of the content of the anchor element.
     * @throws ElementNotAvailableException In case the element is not available, an exception is thrown.
     */
    protected void clickOnAnchorContaining(String content) throws ElementNotAvailableException {
        log.debug("clicking anchor...")
        def clicked = false
        def tmpPage = null
        def elements = []
        currentPage.getElementsByTagName('a').each() { child ->
            elements << child.asXml()
            if (!clicked && child.asXml().contains(content))  {
                tmpPage = child.click()
                clicked = true
            }
        }
        if (!clicked) {
            throw new ElementNotAvailableException("Anchor containing '${content}' not found.", elements)
        } else {
            currentPage = tmpPage
            logPageName()
        }
        log.debug("...clicked")
    }

    /**
     * Clicks on the input element identified by the part of the html content of this element.
     * @param content Part of the content of the anchor element.
     * @throws ElementNotAvailableException In case the input element referenced by this name part could not be
     * found.
     */
    protected void clickOnInputNameContaining(String name) throws ElementNotAvailableException {
        log.debug("clicking input...")
        def clicked = false
        def tmpPage = null
        def elements = []
        currentPage.getElementsByTagName('input').each() { child ->
            elements << child.asXml()
            if (!clicked && child.nameAttribute.contains(name))  {
                tmpPage = child.click()
                clicked = true
            }
        }
        if (!clicked) {
            throw new ElementNotAvailableException("Input element containing '${content}' not found.", elements)
        } else {
            currentPage = tmpPage
            logPageName()
        }
        log.debug("...clicked")
    }

    /**
     * Returns the text document behind a anchor element.
     * @param href Href attribute of the anchor element.
     * @return A String containing the text content.
     */
    protected String loadDocumentOnAnchor(String href) {
        log.debug("clicking anchor...")
        def page = getAnchorByHref(href).click()
        log.debug("...clicked")
        page.content
    }

    /**
     * Returns the text document behind a anchor element which is defined by a particular content.
     * @param content Part of the content of the anchor element.
     * @return A String containing the text content.
     * @throws ElementNotAvailableException If the element is not available.
     */
    protected String loadDocumentOnAnchorContaining(String content) throws ElementNotAvailableException,
            StepExecutionException {
        def clicked = false
        def tmpPage = null
        def elements = []
        currentPage.getElementsByTagName('a').each() { child ->
            elements << child.asXml()
            if (!clicked && child.asXml().contains(content))  {
                tmpPage = child.click()
                clicked = true
            }
        }
        if (!clicked) {
            throw new ElementNotAvailableException("Anchor containing '${content}' not found.", elements)
        }
        if (tmpPage?.content) {
            tmpPage.content
        } else {
            throw new StepExecutionException("Link did not return the expected content. Link returned ${tmpPage.clazz}")
        }
    }

    /**
     * Returns the text document behind an input element which is defined by a particular name.
     * @param content Part of the name of the input element.
     * @return A String containing the text content.
     * @throws ElementNotAvailableException If the element is not available.
     */
    protected String loadDocumentOnInputNameContaining(String name) throws ElementNotAvailableException,
            StepExecutionException {
        log.debug("clicking input...")
        def clicked = false
        def tmpPage = null
        def elements = []
        currentPage.getElementsByTagName('input').each() { child ->
            elements << child.nameAttribute
            if (!clicked && child.nameAttribute.contains(name))  {
                tmpPage = child.click()
                clicked = true
            }
        }
        if (!clicked) {
            throw new ElementNotAvailableException("Input element containing '${content}' not found.", elements)
        }
        if (tmpPage?.content) {
            tmpPage.content
        } else {
            throw new StepExecutionException("Link did not return the expected content. Link returned ${tmpPage.clazz}")
        }
    }

    /**
     * Returns the first element referenced by this tag.
     * @param tag Element is referenced by this tag type.
     * @return HtmlElement of the named type.
     * @throws ElementNotAvailableException If element type is not available.
     */
    protected HtmlElement getElementByTag(String tag) throws ElementNotAvailableException {
        try {
            return currentPage.getElementsByTagName(tag)[0]
        } catch (ElementNotFoundException enf) {
            def elementEntries = []
            //TODO distinct tag types
            throw new ElementNotAvailableException("Element with tag type '${tag}' not found.", enf, elementEntries)
        }
    }

    /**
     * Returns all elements referenced by this tag.
     * @param tag Element is referenced by this tag type.
     * @return HtmlElement array of the named type.
     * @throws ElementNotAvailableException If element type is not available.
     */
    protected HtmlElement[] getElementsByTag(String tag) throws ElementNotAvailableException {
        try {
            return currentPage.getElementsByTagName(tag)
        } catch (Throwable t) {
            def elementEntries = []
            //TODO distinct tag types
            throw new ElementNotAvailableException("Element with tag type '${tag}' not found.", enf, elementEntries)
        }
    }

    /**
     * Returns the first element referenced by this name.
     * @param name Name of the HtmlElement.
     * @return The HtmlElement referenced by the given name.
     * @throws ElementNotAvailableException If the element could not be found.
     */
    protected HtmlElement getElementByName(String name) throws ElementNotAvailableException {
        try {
            return currentPage.getElementByName(name)
        } catch (ElementNotFoundException enf) {
            //TODO implement test
            def elements = currentPage.getElementsByTagName("*")
            def elementEntries = []
            elements.each { element ->
                if (element.nameAttribute && element.nameAttribute != "") {
                    elementEntries << "<${element.tagName} name='${element.nameAttribute}' .../>"
                }
            }
            throw new ElementNotAvailableException("Element with name '${name}' not found.", enf, elementEntries)
        }
    }

    /**
     * Returns all elements referenced by this name.
     * @param name Name of the HtmlElement.
     * @return The HtmlElement array referenced by the given name.
     * @throws ElementNotAvailableException If the element could not be found.
     */
    protected HtmlElement[] getElementsByName(String name) throws ElementNotAvailableException {
        try {
            return currentPage.getElementsByName(name)
        } catch (Throwable t) {
            def elements = currentPage.getElementsByTagName("*")
            def elementEntries = []
            elements.each { element ->
                if (element.nameAttribute && element.nameAttribute != "") {
                    elementEntries << "<${element.tagName} name='${element.nameAttribute}' .../>"
                }
            }
            throw new ElementNotAvailableException("Element with name '${name}' not found.", enf, elementEntries)
        }
    }

    /**
     * Returns the element referenced by this id.
     * @param id Id of the element.
     * @return The element referenced by the given id.
     * @throws ElementNotAvailableException If the element is not available.
     */
    protected HtmlElement getElementById(String id) throws ElementNotAvailableException {
        try {
            return currentPage.getElementById(id)
        } catch (Throwable t) {
            def elements = currentPage.getElementsByTagName("*")
            def elementEntries = []
            elements.each { element ->
                if (element.idAttribute && element.idAttribute != "") {
                    elementEntries << "<${element.tagName} id='${element.idAttribute}' .../>"
                }
            }
            throw new ElementNotAvailableException("Element with id '${id}' not found.", enf, elementEntries)
        }
    }

    /**
     * Returns the element referenced by this XPath expression.
     * @param path XPath expression of the element.
     * @return The element referenced by the given XPath.
     * @throws ElementNotAvailableException If the element is not available.
     */
    protected HtmlElement getElementByXPath(String path) throws ElementNotAvailableException {
        try {
            return currentPage.getFirstByXPath(path)
        } catch (Throwable t) {
            def elementEntries = []
            //TODO determine possible elements
            throw new ElementNotAvailableException("Element with id '${id}' not found.", enf, elementEntries)
        }
    }

    /**
     * Returns all elements referenced by this XPath expression.
     * @param path XPath expression of the elements.
     * @return The elements referenced by the given XPath.
     * @throws ElementNotAvailableException If the element is not available.
     */
    protected HtmlElement[] getElementsByXPath(String path) throws ElementNotAvailableException {
        try {
            return currentPage.getByXPath(path) as HtmlElement[]
        } catch (Throwable t) {
            def elementEntries = []
            //TODO determine possible elements
            throw new ElementNotAvailableException("Element with id '${id}' not found.", enf, elementEntries)
        }
    }

    /**
     * Applies a closure to each element determined by an XPath expression.
     * @param path XPath expression of the elements.
     * @param closure Closure to be executed.
     * @throws ElementNotAvailableException If the element is not available.
     */
    protected void getElementsByXPath(String path, Closure closure) throws ElementNotAvailableException {
        getElementByXPath(path).each { element ->
            closure.call(element)
        }
    }

    /**
     * Returns anchor element determined by href attribute.
     * @param href Href attribute that determines the anchor.
     * @return Anchor element determined by href attribute.
     * @throws ElementNotAvailableException If the element is not available.
     */
    protected HtmlAnchor getAnchorByHref(String href) throws ElementNotAvailableException {
        try {
            return currentPage.getAnchorByHref(href)
        } catch (ElementNotFoundException enf) {
            def elementEntries = []
            //TODO determine possible elements
            throw new ElementNotAvailableException("Anchor element with href '${href}' not found.", enf, elementEntries)
        }
    }

    /**
     * Writes current website to local folder.
     */
    protected void save() {
        try {
            def i = 0
            def file = new File("${folder}currentPage${i++}.html")
            while (file.exists()) {
                file = new File("${folder}currentPage${i++}.html")
            }
            log.info("Writing file to ${file.name}")
            currentPage.save(file)
        } catch (Exception ex) {
            log.error("Could not save file.", ex)
        }
    }

    /**
     * Sets availability of JavaScript function.
     * @param enabled
     */
    protected void setJsEnabled(boolean enabled) {
        client.setJavaScriptEnabled(enabled)
    }

    /**
     * Type of document that indicates the type of the returned document.
     */
    public enum DocType {
        HTML, XML, TEXT, PDF
    }

    /**
     * Logs the current page name, if a page determine closure is set.
     */
    private void logPageName() {
        if (properties.getProperty("debug.save_on_each_step", "false").equals("true")) {
            save()
        }
        if (determinePageName) {
            def pageName = determinePageName.call(currentPage)
            log.info("Now on page: ${pageName}")
        }
    }

    /**
     * Method that orchestrates the process logic and logout logic.
     * @param closure Closure to be executed on each file.
     */
    final void eachDocument(Closure closure) {
        try {
            processWebsite(closure)
        } catch (ElementNotAvailableException enaex) {
            log.error("Could not process website.", enaex)
            log.error(enaex.message)
            log.error("Try following alternatives:")
            enaex.availableValues.each{ it ->
                log.error("\t${it}")
            }
            log.error("---")
            save()
            throw enaex
        } catch (Exception ex) {
            log.error("Could not process website.", ex)
            log.error(ex.message)
            save()
            throw ex
        } finally {
            try {
                processLogout()
            } catch (Exception ex) {
                log.warn("Could not log out.", ex)
                save()
            }
        }
    }

    /**
     * Method to be implemented for each website. This method contains the extraction logic.
     * @param closure Closure to be executed on each file.
     */
    abstract void processWebsite(Closure closure)

    /**
     * Method to be implemented for each website. This method contains the logout logic.
     */
    abstract void processLogout()

}