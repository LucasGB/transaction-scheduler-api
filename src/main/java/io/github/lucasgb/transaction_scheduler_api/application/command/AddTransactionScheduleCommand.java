package io.github.lucasgb.transaction_scheduler_api.application.command;

import io.github.lucasgb.transaction_scheduler_api.domain.valueObjects.Money;

import java.time.LocalDate;

public record AddTransactionScheduleCommand(
        Money money,
        Money fee,
        Money totalAmount,
        String sourceAccount,
        String targetAccount,
        LocalDate scheduleDate
) {}
