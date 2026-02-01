package io.github.lucasgb.transaction_scheduler_api.infrastructure.presentation.dto.response;

import io.github.lucasgb.transaction_scheduler_api.domain.entity.TransactionSchedule;
import org.springframework.data.domain.Page;

public record FetchTransactionScheduleQueryResponse(
        PagedResponse<TransactionSchedule> transactions
) {
    public static FetchTransactionScheduleQueryResponse from(Page<TransactionSchedule> transactions) {
        return new FetchTransactionScheduleQueryResponse(PagedResponse.from(transactions));
    }
}
