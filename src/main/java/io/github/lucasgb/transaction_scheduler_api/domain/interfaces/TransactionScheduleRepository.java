package io.github.lucasgb.transaction_scheduler_api.domain.interfaces;

import io.github.lucasgb.transaction_scheduler_api.domain.entity.TransactionSchedule;

public interface TransactionScheduleRepository {
    TransactionSchedule save(TransactionSchedule transactionSchedule);
}
