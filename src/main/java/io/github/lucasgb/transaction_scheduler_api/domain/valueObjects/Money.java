package io.github.lucasgb.transaction_scheduler_api.domain.valueObjects;

import io.github.lucasgb.transaction_scheduler_api.domain.enums.CurrencyEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Value Object for financial amounts.
 * Mandatory for avoiding floating point issues.
 */
@Embeddable
public class Money {
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private CurrencyEnum currency;

    public static final Money ZERO = new Money(BigDecimal.ZERO, CurrencyEnum.EUR); // Default

    protected Money() {}

    public Money(BigDecimal amount, CurrencyEnum currency) {
        this.amount = Objects.requireNonNull(amount);
        this.currency = Objects.requireNonNull(currency);

        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getAmount() { return amount; }
    public CurrencyEnum getCurrency() { return currency; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money money)) return false;
        return amount.compareTo(money.amount) == 0 && currency == money.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount.stripTrailingZeros(), currency);
    }

    @Override
    public String toString() {
        return String.format("%s %s", amount, currency);
    }
}
