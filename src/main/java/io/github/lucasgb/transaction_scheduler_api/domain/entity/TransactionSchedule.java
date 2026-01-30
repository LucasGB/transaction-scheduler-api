package io.github.lucasgb.transaction_scheduler_api.domain.entity;

import io.github.lucasgb.transaction_scheduler_api.domain.valueObjects.Money;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@Table(name = "transaction_schedule")
public class TransactionSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "transfer_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "transfer_currency"))
    })
    private Money money;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "fee_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "fee_currency"))
    })
    private Money fee;
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
}
