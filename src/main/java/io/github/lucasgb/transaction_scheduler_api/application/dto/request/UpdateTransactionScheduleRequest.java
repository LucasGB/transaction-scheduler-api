package io.github.lucasgb.transaction_scheduler_api.application.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateTransactionScheduleRequest(
        BigDecimal newTransferAmount,
        LocalDate scheduleDate
) {}