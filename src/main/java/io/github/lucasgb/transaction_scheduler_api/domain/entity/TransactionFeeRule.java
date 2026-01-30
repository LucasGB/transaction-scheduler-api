package io.github.lucasgb.transaction_scheduler_api.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "transaction_fee_rule")
public class TransactionFeeRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private long minDays;
    private long maxDays;
    private BigDecimal rate;
    private BigDecimal fixedFee;
    private boolean active;

    public boolean matches(BigDecimal amount, long days) {
        return amount.compareTo(minAmount) >= 0 &&
                amount.compareTo(maxAmount) <= 0 &&
                days >= minDays &&
                days <= maxDays;
    }
}
