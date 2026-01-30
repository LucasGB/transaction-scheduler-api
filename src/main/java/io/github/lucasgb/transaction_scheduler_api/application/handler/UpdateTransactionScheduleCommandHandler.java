package io.github.lucasgb.transaction_scheduler_api.application.handler;

import io.github.lucasgb.transaction_scheduler_api.application.command.UpdateTransactionScheduleCommand;
import io.github.lucasgb.transaction_scheduler_api.application.dto.UpdateTransactionScheduleCommandResult;
import io.github.lucasgb.transaction_scheduler_api.application.service.TransactionFeeStrategyFactory;
import io.github.lucasgb.transaction_scheduler_api.domain.interfaces.TransactionFeeStrategy;
import io.github.lucasgb.transaction_scheduler_api.domain.interfaces.TransactionScheduleRepository;
import io.github.lucasgb.transaction_scheduler_api.domain.service.TransactionFeeCalculationService;
import io.github.lucasgb.transaction_scheduler_api.domain.valueObjects.Money;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UpdateTransactionScheduleCommandHandler {

    private final TransactionScheduleRepository transactionScheduleRepository;
    private final TransactionFeeStrategyFactory transactionFeeStrategyFactory;
    private final TransactionFeeCalculationService transactionFeeCalculationService;

    public UpdateTransactionScheduleCommandHandler(
            TransactionScheduleRepository transactionScheduleRepository,
            TransactionFeeStrategyFactory transactionFeeStrategyFactory,
            TransactionFeeCalculationService transactionFeeCalculationService) {
        this.transactionScheduleRepository = transactionScheduleRepository;
        this.transactionFeeStrategyFactory = transactionFeeStrategyFactory;
        this.transactionFeeCalculationService = transactionFeeCalculationService;
    }

    @Transactional
    public UpdateTransactionScheduleCommandResult handle(UpdateTransactionScheduleCommand command) {
        try {
            var transaction = transactionScheduleRepository.findById(command.id())
                    .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

            if (command.scheduleDate() != null && !command.scheduleDate().equals(transaction.getScheduleDate())) {
                transaction.setScheduleDate(command.scheduleDate());

                final List<TransactionFeeStrategy> strategies = transactionFeeStrategyFactory.createStrategies();
                final Money calculatedFee = transactionFeeCalculationService.calculate(
                        transaction.getTotalAmount(),
                        command.scheduleDate(),
                        strategies
                );

                final Money transferAmount = new Money(
                        transaction.getTotalAmount().getAmount().subtract(calculatedFee.getAmount()),
                        transaction.getTotalAmount().getCurrency()
                );

                transaction.setMoney(transferAmount);
                transaction.setFee(calculatedFee);
                transaction.setUpdatedAt(LocalDateTime.now());
            }

            transactionScheduleRepository.save(transaction);
            return new UpdateTransactionScheduleCommandResult(true, transaction, null);
        } catch (Exception ex) {
            return new UpdateTransactionScheduleCommandResult(false, null, ex.getMessage());
        }
    }
}