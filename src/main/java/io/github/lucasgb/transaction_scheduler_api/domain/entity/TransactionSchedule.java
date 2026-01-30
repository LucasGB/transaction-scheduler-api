package io.github.lucasgb.transaction_scheduler_api.domain.entity;

import io.github.lucasgb.transaction_scheduler_api.domain.valueObjects.Money;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Entity
@Table(name = "transaction_schedule")
public class TransactionSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Money money;
    private Money fee;
    private Money totalAmount;
    private String sourceAccount;
    private String targetAccount;
    private LocalDate scheduleDate;
    private String createdAt;
    private String updatedAt;
}
