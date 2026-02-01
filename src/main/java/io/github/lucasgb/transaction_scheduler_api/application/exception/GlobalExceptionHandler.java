package io.github.lucasgb.transaction_scheduler_api.application.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.github.lucasgb.transaction_scheduler_api.domain.exceptions.SameAccountTransferException;
import io.github.lucasgb.transaction_scheduler_api.infrastructure.presentation.dto.response.ApiError;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(SameAccountTransferException.class)
    public ResponseEntity<ApiError> handleSameAccount(SameAccountTransferException ex) {
        log.error("Same account rule violation: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new ApiError("SAME_ACCOUNT_TRANSFER_EXCEPTION", ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<ApiError> handleNotFound(EntityNotFoundException ex) {
        log.warn("Not found exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError("NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex) {
        log.error("Invalid argument: {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(new ApiError("INVALID_INPUT", ex.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiError> handleIllegalState(IllegalStateException ex) {
        log.error("Violation: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError("NO_MATCHING_FEE_CALCULATION_RULE", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(Exception ex) {
        log.error("Unexpected error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError("INTERNAL_ERROR", "An unexpected error occurred"));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleMessageNotReadable(HttpMessageNotReadableException ex) {

        final Throwable rootCause = ex.getMostSpecificCause();
        if (rootCause instanceof InvalidFormatException ife && ife.getTargetType().isEnum()) {

            final String invalidValue = String.valueOf(ife.getValue());
            final String enumName = ife.getTargetType().getSimpleName();

            final String allowed = Arrays.stream(ife.getTargetType().getEnumConstants())
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));


            return ResponseEntity.badRequest().body(
                    new ApiError(
                            "INVALID_CURRENCY_VALUE",
                            "Invalid currency '" + invalidValue + "'. Allowed values: " + allowed
                    )
            );
        }
        else if (rootCause instanceof DateTimeParseException dtpe) {
            return ResponseEntity.badRequest().body(
                    new ApiError(
                            "INVALID_SCHEDULE_DATE_VALUE",
                            "Invalid value for date object: '" + dtpe.getMessage()
                    )
            );
        }

        return ResponseEntity.badRequest().body(
                new ApiError(
                        "MALFORMED_REQUEST",
                        "Request body is malformed or contains invalid values"
                )
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> "[" +error.getField() + "]: " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        return ResponseEntity.badRequest()
                .body(new ApiError("INVALID_ARGUMENT", message));
    }



}
