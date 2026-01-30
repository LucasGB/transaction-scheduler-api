package io.github.lucasgb.transaction_scheduler_api.application.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AddTransactionScheduleRequest (
        BigDecimal transferAmount,
        String currency,
        BigDecimal feeAmount,
        BigDecimal totalAmount,
        String sourceAccount,
        String targetAccount,
        LocalDate scheduleDate
){
}
