package com.ecom.custom_exceptions;

public class ResourceNotFoundException extends RuntimeException 
{
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, String field, Object value) {
        super(resourceName + " not found with " + field + ": " + value);
    }

}
