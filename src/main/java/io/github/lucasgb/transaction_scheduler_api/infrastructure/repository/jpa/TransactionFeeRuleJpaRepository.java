package io.github.lucasgb.transaction_scheduler_api.infrastructure.repository.jpa;

import io.github.lucasgb.transaction_scheduler_api.domain.entity.TransactionFeeRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionFeeRuleJpaRepository extends JpaRepository<TransactionFeeRule, Long> {
    List<TransactionFeeRule> findByActiveTrue();
}
