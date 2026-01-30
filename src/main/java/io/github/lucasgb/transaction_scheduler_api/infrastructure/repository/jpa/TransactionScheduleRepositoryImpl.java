package io.github.lucasgb.transaction_scheduler_api.infrastructure.repository.jpa;

import io.github.lucasgb.transaction_scheduler_api.domain.entity.TransactionSchedule;
import io.github.lucasgb.transaction_scheduler_api.domain.interfaces.TransactionScheduleRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public class TransactionScheduleRepositoryImpl implements TransactionScheduleRepository {

    private final TransactionScheduleJpaRepository transactionScheduleRepository;

    public TransactionScheduleRepositoryImpl(TransactionScheduleJpaRepository transactionScheduleRepository) {
        this.transactionScheduleRepository = transactionScheduleRepository;
    }

    @Override
    public TransactionSchedule save(TransactionSchedule transactionSchedule) {
        return transactionScheduleRepository.save(transactionSchedule);
    }

    @Override
    public Optional<TransactionSchedule> findById(Long id) {
        return transactionScheduleRepository.findById(id);
    }

    @Override
    public Page<TransactionSchedule> findByFilters(String sourceAccount, String targetAccount,
                                                   LocalDate scheduleDateFrom, LocalDate scheduleDateTo,
                                                   int page, int size) {
        final Specification<TransactionSchedule> spec = buildSpecification(sourceAccount, targetAccount, scheduleDateFrom, scheduleDateTo);
        return transactionScheduleRepository.findAll(spec, PageRequest.of(page, size));
    }

    @Override
    public void deleteById(Long id) {
        transactionScheduleRepository.deleteById(id);
    }

    /*
     * Dynamically builds predicates with AND operator. If no predicates are specified, return a predicate that's always true (1=1).
    */
    private Specification<TransactionSchedule> buildSpecification(String sourceAccount, String targetAccount,
                                                                  LocalDate scheduleDateFrom, LocalDate scheduleDateTo) {
        return (root, query, cb) -> {
            var predicates = new ArrayList<Predicate>();

            if (sourceAccount != null && !sourceAccount.isBlank()) {
                predicates.add(cb.equal(root.get("sourceAccount"), sourceAccount));
            }

            if (targetAccount != null && !targetAccount.isBlank()) {
                predicates.add(cb.equal(root.get("targetAccount"), targetAccount));
            }

            if (scheduleDateFrom != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("scheduleDate"), scheduleDateFrom));
            }

            if (scheduleDateTo != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("scheduleDate"), scheduleDateTo));
            }

            return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
