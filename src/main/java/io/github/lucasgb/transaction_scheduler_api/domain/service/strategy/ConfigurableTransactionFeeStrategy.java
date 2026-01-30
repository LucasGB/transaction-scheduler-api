package io.github.lucasgb.transaction_scheduler_api.domain.service.strategy;

import io.github.lucasgb.transaction_scheduler_api.domain.entity.TransactionFeeRule;
import io.github.lucasgb.transaction_scheduler_api.domain.interfaces.TransactionFeeStrategy;
import io.github.lucasgb.transaction_scheduler_api.domain.valueObjects.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConfigurableTransactionFeeStrategy implements TransactionFeeStrategy {

    private final TransactionFeeRule rule;

    public ConfigurableTransactionFeeStrategy(TransactionFeeRule rule) {
        this.rule = rule;
    }

    @Override
    public boolean appliesTo(BigDecimal amount, long days) {
        return rule.matches(amount, days);
    }

    @Override
    public Money calculate(Money money) {
        BigDecimal total = money.getAmount()
                .multiply(rule.getRate())
                .add(rule.getFixedFee())
                .setScale(2, RoundingMode.HALF_UP);

        return new Money(total, money.getCurrency());
    }
}

