package com.sapsiero.accountLoader.exception

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 2/16/12
 * Time: 9:35 PM
 *
 */
class ElementNotAvailableException extends Exception {

    private String[] availableElements

    ElementNotAvailableException(def availableElements) {
        super()
        this.availableElements = availableElements
    }

    ElementNotAvailableException(String message, def availableElements) {
        super(message)
        this.availableElements = availableElements
    }

    ElementNotAvailableException(String message, Throwable cause, def availableElements) {
        super(message, cause)
        this.availableElements = availableElements
    }

    ElementNotAvailableException(Throwable cause, def availableElements) {
        super(cause)
        this.availableElements = availableElements
    }

    protected ElementNotAvailableException(String message, Throwable cause, boolean enableSuppression,
                                         boolean writableStackTrace, def availableElements) {
        super(message, cause, enableSuppression, writableStackTrace)
        this.availableElements = availableElements
    }

    public String[] getAvailableValues(){
        return availableElements
    }
}
