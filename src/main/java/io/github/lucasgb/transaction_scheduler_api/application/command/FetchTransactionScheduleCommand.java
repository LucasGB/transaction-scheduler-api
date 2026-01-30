package io.github.lucasgb.transaction_scheduler_api.application.command;

import io.github.lucasgb.transaction_scheduler_api.application.dto.request.FetchTransactionScheduleRequest;

import java.time.LocalDate;

public record FetchTransactionScheduleCommand(
        String sourceAccount,
        String targetAccount,
        LocalDate scheduleDateFrom,
        LocalDate scheduleDateTo,
        int page,
        int size
) {
    public static FetchTransactionScheduleCommand fromRequest(FetchTransactionScheduleRequest request) {
        return new FetchTransactionScheduleCommand(
                request.sourceAccount(),
                request.targetAccount(),
                request.scheduleDateFrom(),
                request.scheduleDateTo(),
                request.page(),
                request.size()
        );
    }
}