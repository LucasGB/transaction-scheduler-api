package io.github.lucasgb.transaction_scheduler_api.domain.interfaces;

import io.github.lucasgb.transaction_scheduler_api.domain.entity.TransactionSchedule;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Optional;

public interface TransactionScheduleRepository {
    TransactionSchedule save(TransactionSchedule transactionSchedule);
    Optional<TransactionSchedule> findById(Long id);
    Page<TransactionSchedule> findByFilters(String sourceAccount, String targetAccount,
                                            LocalDate scheduleDateFrom, LocalDate scheduleDateTo,
                                            int page, int size);

}
