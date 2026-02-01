package io.github.lucasgb.transaction_scheduler_api.application.dto;

import io.github.lucasgb.transaction_scheduler_api.domain.entity.TransactionSchedule;

public record AddTransactionScheduleCommandResult(
        boolean success,
        TransactionSchedule transactionSchedule,
        String errorMessage
) {
    public static AddTransactionScheduleCommandResult sucess(TransactionSchedule transactionSchedule) {
        return new AddTransactionScheduleCommandResult(true, transactionSchedule, null);
    }

    public static AddTransactionScheduleCommandResult failure(String errorMessage) {
        return new AddTransactionScheduleCommandResult(false, null, errorMessage);
    }
}
