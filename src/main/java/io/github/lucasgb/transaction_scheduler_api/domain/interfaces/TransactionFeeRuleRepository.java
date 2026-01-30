package io.github.lucasgb.transaction_scheduler_api.domain.interfaces;

import io.github.lucasgb.transaction_scheduler_api.domain.entity.TransactionFeeRule;

import java.util.List;

public interface TransactionFeeRuleRepository {
    List<TransactionFeeRule> findActiveRules();
}
