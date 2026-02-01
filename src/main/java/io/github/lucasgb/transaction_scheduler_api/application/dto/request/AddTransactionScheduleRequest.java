package io.github.lucasgb.transaction_scheduler_api.application.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AddTransactionScheduleRequest (
        @NotNull(message = "Transfer amount is required")
        @DecimalMin(value = "0.01", message = "Transfer amount must be greater than 0")
        @Digits(integer = 15, fraction = 2, message = "Invalid amount format")
        BigDecimal transferAmount,

        @NotBlank(message = "Currency is required")
        @Pattern(regexp = "EUR|USD|BRL", message = "Currency must be EUR, USD, or BRL")
        @NotNull String currency,

        @NotBlank(message = "Source account is required")
        @Pattern(regexp = "^[A-Z0-9]{5,20}$", message = "Invalid account format")
        String sourceAccount,

        @NotBlank(message = "Target account is required")
        @Pattern(regexp = "^[A-Z0-9]{5,20}$", message = "Invalid account format")
        String targetAccount,

        @NotNull(message = "Schedule date is required")
        @FutureOrPresent(message = "Schedule date cannot be in the past")
        LocalDate scheduleDate
){
    public AddTransactionScheduleRequest {
        if (sourceAccount != null && sourceAccount.equals(targetAccount)) {
            throw new IllegalArgumentException("Source and target accounts must be different");
        }
    }
}
