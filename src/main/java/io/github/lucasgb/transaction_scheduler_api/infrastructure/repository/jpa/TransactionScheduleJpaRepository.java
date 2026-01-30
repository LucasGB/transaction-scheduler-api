package io.github.lucasgb.transaction_scheduler_api.infrastructure.repository.jpa;

import io.github.lucasgb.transaction_scheduler_api.domain.entity.TransactionSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionScheduleJpaRepository extends JpaRepository<TransactionSchedule, Long> {
}
