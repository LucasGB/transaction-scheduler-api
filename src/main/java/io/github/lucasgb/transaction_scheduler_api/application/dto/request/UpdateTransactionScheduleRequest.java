package io.github.lucasgb.transaction_scheduler_api.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Request to update a scheduled transaction")
public record UpdateTransactionScheduleRequest(
        @Schema(
                description = "New transfer amount to be updated (optional)",
                example = "1800.00",
                minimum = "0.01",
                maximum = "999999999999999.99"
        )
        @DecimalMin(value = "0.01", message = "Transfer amount must be greater than 0")
        @Digits(integer = 15, fraction = 2, message = "Invalid amount format")
        BigDecimal newTransferAmount,

        @Schema(
                description = "New schedule date to be updated (optional).",
                example = "2026-02-15"
        )
        @FutureOrPresent(message = "Schedule date cannot be in the past")
        LocalDate scheduleDate
) {}