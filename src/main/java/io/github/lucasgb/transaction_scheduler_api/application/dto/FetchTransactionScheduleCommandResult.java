package io.github.lucasgb.transaction_scheduler_api.application.dto;

import io.github.lucasgb.transaction_scheduler_api.domain.entity.TransactionSchedule;
import org.springframework.data.domain.Page;

public record FetchTransactionScheduleCommandResult(
        Page<TransactionSchedule> transactions
) {}
