package io.github.lucasgb.transaction_scheduler_api.domain.entity;

import io.github.lucasgb.transaction_scheduler_api.domain.valueObjects.Money;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction_schedule")
public class TransactionSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "net_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "net_currency"))
    })
    private Money netAmount;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "fee_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "fee_currency"))
    })
    private Money feeAmount;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "total_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "total_currency"))
    })
    private Money totalAmount;
    private String sourceAccount;
    private String targetAccount;
    private LocalDate scheduleDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void reschedule(BigDecimal newAmount, LocalDate newDate, Money calculatedFee) {
        if (newDate != null)
            this.scheduleDate = newDate;

        if (newAmount != null)
            this.totalAmount = new Money(newAmount, this.totalAmount.getCurrency());

        if (calculatedFee != null) {
            this.feeAmount = calculatedFee;
            this.netAmount = new Money(
                    this.totalAmount.getAmount().subtract(calculatedFee.getAmount()),
                    this.totalAmount.getCurrency()
            );
        }

        this.updatedAt = LocalDateTime.now();
    }
}
