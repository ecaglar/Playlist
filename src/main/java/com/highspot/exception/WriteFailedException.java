package com.highspot.exception;

/**
 * Generic exception for writers
 */
public class WriteFailedException extends RuntimeException {

    public WriteFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
