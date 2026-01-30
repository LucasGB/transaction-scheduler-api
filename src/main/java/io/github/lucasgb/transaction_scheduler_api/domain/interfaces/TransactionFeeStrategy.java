package io.github.lucasgb.transaction_scheduler_api.domain.interfaces;

import io.github.lucasgb.transaction_scheduler_api.domain.valueObjects.Money;

import java.math.BigDecimal;

public interface TransactionFeeStrategy {
    boolean appliesTo(BigDecimal m, long days);
    Money calculate(Money money);
}
