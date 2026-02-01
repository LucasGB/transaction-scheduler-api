package io.github.lucasgb.transaction_scheduler_api.application.command;

import io.github.lucasgb.transaction_scheduler_api.infrastructure.presentation.dto.request.FetchTransactionScheduleRequest;

import java.time.LocalDate;

public record FetchTransactionScheduleQuery(
        String sourceAccount,
        String targetAccount,
        LocalDate scheduleDateFrom,
        LocalDate scheduleDateTo,
        int page,
        int size
) {
    public static FetchTransactionScheduleQuery fromRequest(FetchTransactionScheduleRequest request) {
        return new FetchTransactionScheduleQuery(
                request.sourceAccount(),
                request.targetAccount(),
                request.scheduleDateFrom(),
                request.scheduleDateTo(),
                request.page(),
                request.size()
        );
    }
}