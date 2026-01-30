package io.github.lucasgb.transaction_scheduler_api.application.service;

import io.github.lucasgb.transaction_scheduler_api.domain.interfaces.TransactionFeeRuleRepository;
import io.github.lucasgb.transaction_scheduler_api.domain.interfaces.TransactionFeeStrategy;
import io.github.lucasgb.transaction_scheduler_api.domain.service.strategy.ConfigurableTransactionFeeStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionFeeStrategyFactory {

    private final TransactionFeeRuleRepository repository;

    public TransactionFeeStrategyFactory(TransactionFeeRuleRepository repository) {
        this.repository = repository;
    }

    public List<TransactionFeeStrategy> createStrategies() {
        return repository.findActiveRules()
                .stream()
                .map(rule -> (TransactionFeeStrategy) new ConfigurableTransactionFeeStrategy(rule))
                .toList();
    }
}
