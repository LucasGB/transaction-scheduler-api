package io.github.lucasgb.transaction_scheduler_api.application.handler;

import io.github.lucasgb.transaction_scheduler_api.application.command.UpdateTransactionScheduleCommand;
import io.github.lucasgb.transaction_scheduler_api.application.dto.UpdateTransactionScheduleCommandResult;
import io.github.lucasgb.transaction_scheduler_api.application.service.TransactionFeeStrategyFactory;
import io.github.lucasgb.transaction_scheduler_api.domain.entity.TransactionSchedule;
import io.github.lucasgb.transaction_scheduler_api.domain.interfaces.TransactionFeeStrategy;
import io.github.lucasgb.transaction_scheduler_api.domain.interfaces.TransactionScheduleRepository;
import io.github.lucasgb.transaction_scheduler_api.domain.service.TransactionFeeCalculationService;
import io.github.lucasgb.transaction_scheduler_api.domain.valueObjects.Money;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

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
        if (command == null || !command.hasUpdates())
            return new UpdateTransactionScheduleCommandResult(false, null,
                    "At least one of 'newTransferAmount' or 'scheduleDate' must be provided.");

        if (command.scheduleDate() != null && command.scheduleDate().isBefore(LocalDate.now()))
            return new UpdateTransactionScheduleCommandResult(false, null,
                    "Cannot schedule data to the past.");

        try {
            final TransactionSchedule transaction = transactionScheduleRepository.findById(command.id())
                    .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

            final BigDecimal transferAmount = Objects.requireNonNullElse(command.newTransferAmount(), transaction.getTotalAmount().getAmount());
            final List<TransactionFeeStrategy> strategies = transactionFeeStrategyFactory.createStrategies();

            final Money calculatedFee = transactionFeeCalculationService.calculate(
                    new Money(transferAmount, transaction.getNetAmount().getCurrency()),
                    command.scheduleDate(),
                    strategies
            );

            transaction.reschedule(transferAmount, command.scheduleDate(), calculatedFee);
            transactionScheduleRepository.save(transaction);
            return new UpdateTransactionScheduleCommandResult(true, transaction, null);
        } catch (EntityNotFoundException enfe) {
            return new UpdateTransactionScheduleCommandResult(false, null, enfe.getMessage());
        } catch (IllegalArgumentException iae) {
            return new UpdateTransactionScheduleCommandResult(false, null, iae.getMessage());
        } catch (Exception e) {
            // log?
            return new UpdateTransactionScheduleCommandResult(false, null, "Internal server error");
        }
    }
}