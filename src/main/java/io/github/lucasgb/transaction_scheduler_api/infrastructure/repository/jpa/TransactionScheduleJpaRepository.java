package io.github.lucasgb.transaction_scheduler_api.infrastructure.repository.jpa;

import io.github.lucasgb.transaction_scheduler_api.domain.entity.TransactionSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransactionScheduleJpaRepository extends JpaRepository<TransactionSchedule, Long>, JpaSpecificationExecutor<TransactionSchedule> {
}
