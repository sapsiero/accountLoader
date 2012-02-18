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

    ElementNotAvailableException(String[] availableElements) {
        super()
        this.availableElements = availableElements
    }

    ElementNotAvailableException(String message, String[] availableElements) {
        super(message)
        this.availableElements = availableElements
    }

    ElementNotAvailableException(String message, Throwable cause, String[] availableElements) {
        super(message, cause)
        this.availableElements = availableElements
    }

    ElementNotAvailableException(Throwable cause, String[] availableElements) {
        super(cause)
        this.availableElements = availableElements
    }

    protected ElementNotAvailableException(String message, Throwable cause, boolean enableSuppression,
                                         boolean writableStackTrace, String[] availableElements) {
        super(message, cause, enableSuppression, writableStackTrace)
        this.availableElements = availableElements
    }

    public String[] getAvailableValues(){
        return availableElements
    }
}
