package com.sapsiero.accountLoader

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 1/28/12
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
class ContentProcessException extends Exception {


    ContentProcessException() {
        super()
    }

    ContentProcessException(String message) {
        super(message)
    }

    ContentProcessException(String message, Throwable cause) {
        super(message, cause)
    }

    ContentProcessException(Throwable cause) {
        super(cause)
    }

    protected ContentProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace)
    }
}
