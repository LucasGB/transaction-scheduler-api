package io.github.lucasgb.transaction_scheduler_api.application.handler;

import io.github.lucasgb.transaction_scheduler_api.application.command.DeleteTransactionScheduleCommand;
import io.github.lucasgb.transaction_scheduler_api.application.dto.DeleteTransactionScheduleCommandResult;
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
    public DeleteTransactionScheduleCommandResult handle(DeleteTransactionScheduleCommand command) {
        try {
            transactionScheduleRepository.findById(command.id())
                    .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));

            transactionScheduleRepository.deleteById(command.id());
            return new DeleteTransactionScheduleCommandResult(true, null);
        } catch (Exception ex) {
            return new DeleteTransactionScheduleCommandResult(false, ex.getMessage());
        }
    }
}