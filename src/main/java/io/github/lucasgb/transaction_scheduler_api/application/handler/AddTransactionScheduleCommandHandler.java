package io.github.lucasgb.transaction_scheduler_api.application.handler;

import io.github.lucasgb.transaction_scheduler_api.application.command.AddTransactionScheduleCommand;
import io.github.lucasgb.transaction_scheduler_api.application.dto.AddTransactionScheduleCommandResult;
import io.github.lucasgb.transaction_scheduler_api.domain.entity.TransactionSchedule;
import io.github.lucasgb.transaction_scheduler_api.domain.interfaces.TransactionScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AddTransactionScheduleCommandHandler {
    private final TransactionScheduleRepository transactionScheduleRepository;

    public AddTransactionScheduleCommandHandler(TransactionScheduleRepository transactionScheduleRepository) {
        this.transactionScheduleRepository = transactionScheduleRepository;
    }

    @Transactional
    public AddTransactionScheduleCommandResult handle(AddTransactionScheduleCommand command) {

        try {
            final TransactionSchedule transactionSchedule = TransactionSchedule.builder()
                    .money(command.money())
                    .fee(command.fee())
                    .totalAmount(command.totalAmount())
                    .sourceAccount(command.sourceAccount())
                    .targetAccount(command.targetAccount())
                    .scheduleDate(command.scheduleDate())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            transactionScheduleRepository.save(transactionSchedule);
            return new AddTransactionScheduleCommandResult(true, transactionSchedule, null);
        } catch (Exception ex) {
            return new AddTransactionScheduleCommandResult(false, null, ex.getMessage());
        }
    }
}
