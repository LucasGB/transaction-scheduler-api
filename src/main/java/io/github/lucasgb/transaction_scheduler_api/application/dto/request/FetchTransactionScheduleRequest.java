package io.github.lucasgb.transaction_scheduler_api.application.dto.request;

import java.time.LocalDate;

public record FetchTransactionScheduleRequest(
        String sourceAccount,
        String targetAccount,
        LocalDate scheduleDateFrom,
        LocalDate scheduleDateTo,
        Integer page,
        Integer size
) {
    public FetchTransactionScheduleRequest {
        page = page != null && page >= 0 ? page : 0;
        size = size != null && size > 0 ? size : 20;
    }
}
