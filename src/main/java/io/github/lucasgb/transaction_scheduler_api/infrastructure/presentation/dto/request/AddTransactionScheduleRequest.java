package io.github.lucasgb.transaction_scheduler_api.infrastructure.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(
        name = "AddTransactionScheduleRequest",
        description = "Request payload to schedule a new transaction"
)
public record AddTransactionScheduleRequest (
        @Schema(
                description = "Amount to be transferred",
                example = "2000.00",
                minimum = "0.01",
                maximum = "999999999999999.99",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Transfer amount is required")
        @DecimalMin(value = "0.01", message = "Transfer amount must be greater than 0")
        @Digits(integer = 15, fraction = 2, message = "Invalid amount format")
        BigDecimal transferAmount,

        @Schema(
                description = "ISO currency code",
                example = "EUR",
                allowableValues = {"EUR", "USD", "BRL"},
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Currency is required")
        @Pattern(regexp = "EUR|USD|BRL", message = "Currency must be EUR, USD, or BRL")
        @NotNull String currency,

        @Schema(
                description = "Source account identifier",
                example = "ACC12345",
                minLength = 5,
                maxLength = 20,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Source account is required")
        @Pattern(regexp = "^[A-Z0-9]{5,20}$", message = "Invalid account format")
        String sourceAccount,

        @Schema(
                description = "Target account identifier",
                example = "ACC98765",
                minLength = 5,
                maxLength = 20,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Target account is required")
        @Pattern(regexp = "^[A-Z0-9]{5,20}$", message = "Invalid account format")
        String targetAccount,

        @Schema(
                description = "Date when the transaction should be executed",
                example = "2026-02-15",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Schedule date is required")
        @FutureOrPresent(message = "Schedule date cannot be in the past")
        LocalDate scheduleDate
) {}
