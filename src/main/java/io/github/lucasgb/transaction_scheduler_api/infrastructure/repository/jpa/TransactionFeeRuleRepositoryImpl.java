package io.github.lucasgb.transaction_scheduler_api.infrastructure.repository.jpa;

import io.github.lucasgb.transaction_scheduler_api.domain.entity.TransactionFeeRule;
import io.github.lucasgb.transaction_scheduler_api.domain.interfaces.TransactionFeeRuleRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionFeeRuleRepositoryImpl implements TransactionFeeRuleRepository {
    private final TransactionFeeRuleJpaRepository transactionFeeRuleJpaRepository;

    public TransactionFeeRuleRepositoryImpl(TransactionFeeRuleJpaRepository transactionFeeRuleJpaRepository) {
        this.transactionFeeRuleJpaRepository = transactionFeeRuleJpaRepository;
    }

    @Override
//    @Cacheable("transactionFeeRules")
    public List<TransactionFeeRule> findActiveRules() {
        return transactionFeeRuleJpaRepository.findByActiveTrue().stream().toList();
    }
}
