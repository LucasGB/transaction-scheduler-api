package io.github.lucasgb.transaction_scheduler_api.application.dto;

public record DeleteTransactionScheduleCommandResult(
        boolean success,
        String errorMessage
) {}