package com.sapsiero.accountLoader.exception

/**
 * Created by IntelliJ IDEA.
 * User: tim
 * Date: 2/16/12
 * Time: 9:08 PM
 *
 */
class StepExecutionException extends Exception {
    StepExecutionException() {
        super()
    }

    StepExecutionException(String message) {
        super(message)
    }

    StepExecutionException(String message, Throwable cause) {
        super(message, cause)
    }

    StepExecutionException(Throwable cause) {
        super(cause)
    }

    protected StepExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace)
    }
}
