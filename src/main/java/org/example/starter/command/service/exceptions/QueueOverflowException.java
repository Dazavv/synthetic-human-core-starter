package org.example.starter.command.service.exceptions;

public class QueueOverflowException extends RuntimeException {
    public QueueOverflowException(String message, Throwable cause) {
        super(message, cause);
    }
}
