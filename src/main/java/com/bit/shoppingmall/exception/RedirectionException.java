package com.bit.shoppingmall.exception;

public class RedirectionException extends RuntimeException{
    public RedirectionException() {
        super();
    }

    public RedirectionException(String message) {
        super(message);
    }

    public RedirectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedirectionException(Throwable cause) {
        super(cause);
    }

    protected RedirectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
