package io.github.lucasgb.transaction_scheduler_api.application.dto;

import io.github.lucasgb.transaction_scheduler_api.domain.entity.TransactionSchedule;

public record UpdateTransactionScheduleCommandResult(
        boolean success,
        TransactionSchedule transactionSchedule,
        String errorMessage
) {}