package io.github.lucasgb.transaction_scheduler_api.application.service.handler;

import io.github.lucasgb.transaction_scheduler_api.application.command.FetchTransactionScheduleQuery;
import io.github.lucasgb.transaction_scheduler_api.infrastructure.presentation.dto.response.FetchTransactionScheduleQueryResponse;
import io.github.lucasgb.transaction_scheduler_api.domain.interfaces.TransactionScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FetchTransactionScheduleCommandHandler {

    private final TransactionScheduleRepository transactionScheduleRepository;

    public FetchTransactionScheduleCommandHandler(TransactionScheduleRepository transactionScheduleRepository) {
        this.transactionScheduleRepository = transactionScheduleRepository;
    }

    @Transactional(readOnly = true)
    public FetchTransactionScheduleQueryResponse handle(FetchTransactionScheduleQuery query) {
        var transactions = transactionScheduleRepository.findByFilters(
                query.sourceAccount(),
                query.targetAccount(),
                query.scheduleDateFrom(),
                query.scheduleDateTo(),
                query.page(),
                query.size()
        );

        return FetchTransactionScheduleQueryResponse.from(transactions);
    }
}