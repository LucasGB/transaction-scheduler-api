package io.github.lucasgb.transaction_scheduler_api.infrastructure.repository.jpa;

import io.github.lucasgb.transaction_scheduler_api.domain.entity.TransactionSchedule;
import io.github.lucasgb.transaction_scheduler_api.domain.interfaces.TransactionScheduleRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionScheduleRepositoryImpl implements TransactionScheduleRepository {

    private final TransactionScheduleRepository transactionScheduleRepository;

    public TransactionScheduleRepositoryImpl(TransactionScheduleRepository transactionScheduleRepository) {
        this.transactionScheduleRepository = transactionScheduleRepository;
    }

    @Override
    public TransactionSchedule save(TransactionSchedule transactionSchedule) {
        return transactionScheduleRepository.save(transactionSchedule);
    }
}
