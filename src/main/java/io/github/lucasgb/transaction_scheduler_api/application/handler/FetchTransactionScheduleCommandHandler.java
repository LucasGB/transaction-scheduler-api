package io.github.lucasgb.transaction_scheduler_api.application.handler;

import io.github.lucasgb.transaction_scheduler_api.application.command.FetchTransactionScheduleCommand;
import io.github.lucasgb.transaction_scheduler_api.application.dto.FetchTransactionScheduleCommandResult;
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
    public FetchTransactionScheduleCommandResult handle(FetchTransactionScheduleCommand query) {
        var transactions = transactionScheduleRepository.findByFilters(
                query.sourceAccount(),
                query.targetAccount(),
                query.scheduleDateFrom(),
                query.scheduleDateTo(),
                query.page(),
                query.size()
        );

        return new FetchTransactionScheduleCommandResult(transactions);
    }
}