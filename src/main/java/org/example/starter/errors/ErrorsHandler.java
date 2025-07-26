package org.example.starter.errors;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.example.starter.command.service.exceptions.QueueOverflowException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class ErrorsHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleValidationError(ConstraintViolationException ex) {
        log.warn("Validation failed: {}", ex.getMessage());
        return buildResponse("VALIDATION_ERROR", ex.getMessage(), HttpStatus.NOT_ACCEPTABLE, ex);
    }

    @ExceptionHandler(QueueOverflowException.class)
    public ResponseEntity<ApiError> handleQueueOverflow(QueueOverflowException ex) {
        log.warn("Queue overflow: {}", ex.getMessage());
        return buildResponse("QUEUE_OVERFLOW", ex.getMessage(), HttpStatus.TOO_MANY_REQUESTS, ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralError(Exception ex) {
        log.error("Unexpected error", ex);
        return buildResponse("UNEXPECTED_ERROR", "Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    private ResponseEntity<ApiError> buildResponse(String code, String message, HttpStatus status, Exception ex) {
        ApiError error = new ApiError(code, message, Instant.now());
        return ResponseEntity.status(status).body(error);
    }
}
