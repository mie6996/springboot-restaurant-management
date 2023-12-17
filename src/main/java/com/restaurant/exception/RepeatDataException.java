package com.restaurant.exception;

public class RepeatDataException extends RuntimeException {

    public RepeatDataException() {
        super();
    }

    public RepeatDataException(String message) {
        super(message);
    }

    public RepeatDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
