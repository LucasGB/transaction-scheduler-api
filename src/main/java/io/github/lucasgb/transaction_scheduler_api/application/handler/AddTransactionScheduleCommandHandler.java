package io.github.lucasgb.transaction_scheduler_api.application.handler;

import io.github.lucasgb.transaction_scheduler_api.application.command.AddTransactionScheduleCommand;
import io.github.lucasgb.transaction_scheduler_api.application.dto.AddTransactionScheduleCommandResult;
import io.github.lucasgb.transaction_scheduler_api.application.service.TransactionFeeStrategyFactory;
import io.github.lucasgb.transaction_scheduler_api.domain.entity.TransactionSchedule;
import io.github.lucasgb.transaction_scheduler_api.domain.interfaces.TransactionScheduleRepository;
import io.github.lucasgb.transaction_scheduler_api.domain.service.TransactionFeeCalculationService;
import io.github.lucasgb.transaction_scheduler_api.domain.valueObjects.Money;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AddTransactionScheduleCommandHandler {
    private final TransactionScheduleRepository transactionScheduleRepository;
    private final TransactionFeeStrategyFactory transactionFeeStrategyFactory;
    private final TransactionFeeCalculationService transactionFeeCalculationService;

    public AddTransactionScheduleCommandHandler(TransactionScheduleRepository transactionScheduleRepository, TransactionFeeStrategyFactory transactionFeeStrategyFactory, TransactionFeeCalculationService transactionFeeCalculationService) {
        this.transactionScheduleRepository = transactionScheduleRepository;
        this.transactionFeeStrategyFactory = transactionFeeStrategyFactory;
        this.transactionFeeCalculationService = transactionFeeCalculationService;
    }

    @Transactional
    public AddTransactionScheduleCommandResult handle(AddTransactionScheduleCommand command) {

        try {

            var strategies = transactionFeeStrategyFactory.createStrategies();

            final Money calculatedFee = transactionFeeCalculationService.calculate(
                    command.transferAmount(),
                    command.scheduleDate(),
                    strategies
            );

            final Money netAmount = new Money(
                    command.transferAmount().getAmount()
                            .subtract(calculatedFee.getAmount()),
                    command.transferAmount().getCurrency()
            );

            final TransactionSchedule transactionSchedule = TransactionSchedule.builder()
                    .netAmount(netAmount)
                    .feeAmount(calculatedFee)
                    .totalAmount(command.transferAmount())
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
