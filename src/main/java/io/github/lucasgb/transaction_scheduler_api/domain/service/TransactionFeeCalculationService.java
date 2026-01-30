package io.github.lucasgb.transaction_scheduler_api.domain.service;

import io.github.lucasgb.transaction_scheduler_api.domain.interfaces.TransactionFeeStrategy;
import io.github.lucasgb.transaction_scheduler_api.domain.valueObjects.Money;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TransactionFeeCalculationService {

    public Money calculate(Money amount, LocalDate scheduleDate, List<TransactionFeeStrategy> strategies) {
        long days = ChronoUnit.DAYS.between(LocalDate.now(), scheduleDate);

        return strategies.stream()
                .filter(s -> s.appliesTo(amount.getAmount(), days))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No matching fee rule"))
                .calculate(amount);
    }
}
