package com.recruva.exception;

public class InvalidJobStatusTransitionException extends RuntimeException {
    public InvalidJobStatusTransitionException(String message) {
        super(message);
    }
}
