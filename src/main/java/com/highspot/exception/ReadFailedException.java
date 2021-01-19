package com.highspot.exception;

/**
 * Generic exception for readers
 */
public class ReadFailedException extends RuntimeException {

    public ReadFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
