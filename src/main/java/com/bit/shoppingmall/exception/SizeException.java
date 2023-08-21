package com.bit.shoppingmall.exception;

public class SizeException extends RuntimeException{
    public SizeException() {
        super();
    }

    public SizeException(String message) {
        super(message);
    }

    public SizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SizeException(Throwable cause) {
        super(cause);
    }

    protected SizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
