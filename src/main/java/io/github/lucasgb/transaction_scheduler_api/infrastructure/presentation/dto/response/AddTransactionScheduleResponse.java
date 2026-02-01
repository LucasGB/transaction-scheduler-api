package io.github.lucasgb.transaction_scheduler_api.infrastructure.presentation.dto.response;

import io.github.lucasgb.transaction_scheduler_api.domain.entity.TransactionSchedule;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Scheduled transaction details")
public record AddTransactionScheduleResponse(

        @Schema(example = "42")
        Long id,

        @Schema(
                description = "Total amount to be transferred.",
                example = "2000.00",
                minimum = "0.01",
                maximum = "999999999999999.99"
        )
        BigDecimal totalAmount,

        @Schema(
                description = "Tax fee deduced from business rules.",
                example = "180.00",
                minimum = "0.01",
                maximum = "999999999999999.99"
        )
        BigDecimal feeAmount,

        @Schema(
                description = "Net amount to be transferred.",
                example = "1820.00",
                minimum = "0.01",
                maximum = "999999999999999.99"
        )
        BigDecimal netAmount,

        @Schema(
                description = "ISO currency code.",
                example = "EUR",
                allowableValues = {"EUR", "USD", "BRL"}
        )
        String currency,

        @Schema(
                description = "Source account identifier.",
                example = "ACC12345",
                minLength = 5,
                maxLength = 20
        )
        String sourceAccount,

        @Schema(
                description = "Target account identifier.",
                example = "ACC98765",
                minLength = 5,
                maxLength = 20
        )
        String targetAccount,

        @Schema(
                description = "Date when the transaction should be executed.",
                example = "2026-02-15"
        )
        LocalDate scheduleDate
) {
    public static AddTransactionScheduleResponse from(TransactionSchedule transaction) {
        return new AddTransactionScheduleResponse(
                transaction.getId(),
                transaction.getTotalAmount().getAmount(),
                transaction.getFeeAmount().getAmount(),
                transaction.getNetAmount().getAmount(),
                transaction.getTotalAmount().getCurrency().name(),
                transaction.getSourceAccount(),
                transaction.getTargetAccount(),
                transaction.getScheduleDate()
        );
    }
}
