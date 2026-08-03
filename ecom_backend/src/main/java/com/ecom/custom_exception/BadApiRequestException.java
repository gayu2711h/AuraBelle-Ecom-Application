package com.ecom.custom_exception;

public class BadApiRequestException extends RuntimeException {
    public BadApiRequestException(String message) {
        super(message);
    }
}
