package com.modelsis.backendAhamada.Exception;

public class ProductTypeNotFoundException extends Exception{
    public ProductTypeNotFoundException() {
        super();
    }

    public ProductTypeNotFoundException(String message) {
        super(message);
    }

    public ProductTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductTypeNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ProductTypeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
