package com.sapsiero.accountLoader.exception

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 2/16/12
 * Time: 9:35 PM
 *
 */
class ValueNotAvailableException extends Exception {

    private String[] availableValues

    ValueNotAvailableException(String[] availableValues) {
        super()
        this.availableValues = availableValues
    }

    ValueNotAvailableException(String message, String[] availableValues) {
        super(message)
        this.availableValues = availableValues
    }

    ValueNotAvailableException(String message, Throwable cause, String[] availableValues) {
        super(message, cause)
        this.availableValues = availableValues
    }

    ValueNotAvailableException(Throwable cause, String[] availableValues) {
        super(cause)
        this.availableValues = availableValues
    }

    protected ValueNotAvailableException(String message, Throwable cause, boolean enableSuppression,
                                         boolean writableStackTrace, String[] availableValues) {
        super(message, cause, enableSuppression, writableStackTrace)
        this.availableValues = availableValues
    }

    public String[] getAvailableValues(){
        return availableValues
    }
}
