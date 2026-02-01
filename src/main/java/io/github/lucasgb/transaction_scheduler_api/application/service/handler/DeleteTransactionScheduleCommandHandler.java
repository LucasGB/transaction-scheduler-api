package io.github.lucasgb.transaction_scheduler_api.application.service.handler;

import io.github.lucasgb.transaction_scheduler_api.application.command.DeleteTransactionScheduleCommand;
import io.github.lucasgb.transaction_scheduler_api.domain.interfaces.TransactionScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteTransactionScheduleCommandHandler {

    private final TransactionScheduleRepository transactionScheduleRepository;

    public DeleteTransactionScheduleCommandHandler(TransactionScheduleRepository transactionScheduleRepository) {
        this.transactionScheduleRepository = transactionScheduleRepository;
    }

    @Transactional
    public void handle(DeleteTransactionScheduleCommand command) {
        transactionScheduleRepository.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));

        transactionScheduleRepository.deleteById(command.id());
    }
}