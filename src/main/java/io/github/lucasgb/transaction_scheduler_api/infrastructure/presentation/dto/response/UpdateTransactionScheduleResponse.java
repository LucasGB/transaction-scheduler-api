package io.github.lucasgb.transaction_scheduler_api.infrastructure.presentation.dto.response;

import io.github.lucasgb.transaction_scheduler_api.domain.entity.TransactionSchedule;
import io.github.lucasgb.transaction_scheduler_api.domain.valueObjects.Money;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Scheduled transaction details")
public record UpdateTransactionScheduleResponse(
        Long id,
        Money netAmount,
        Money feeAmount,
        Money totalAmount,
        @Schema(
                description = "Source account identifier",
                example = "ACC12345",
                minLength = 5,
                maxLength = 20,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String sourceAccount,
        @Schema(
                description = "Target account identifier",
                example = "ACC12345",
                minLength = 5,
                maxLength = 20,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String targetAccount,
        @Schema(
                description = "Date when the transaction should be executed",
                example = "2026-02-15",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        LocalDate scheduleDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static UpdateTransactionScheduleResponse from(TransactionSchedule transactionSchedule) {
        return new UpdateTransactionScheduleResponse(
                transactionSchedule.getId(),
                transactionSchedule.getNetAmount(),
                transactionSchedule.getFeeAmount(),
                transactionSchedule.getTotalAmount(),
                transactionSchedule.getSourceAccount(),
                transactionSchedule.getTargetAccount(),
                transactionSchedule.getScheduleDate(),
                transactionSchedule.getCreatedAt(),
                transactionSchedule.getUpdatedAt()
        );
    }
}
