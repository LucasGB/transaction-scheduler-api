package io.github.lucasgb.transaction_scheduler_api.application.command;

import io.github.lucasgb.transaction_scheduler_api.application.dto.request.AddTransactionScheduleRequest;
import io.github.lucasgb.transaction_scheduler_api.domain.enums.CurrencyEnum;
import io.github.lucasgb.transaction_scheduler_api.domain.valueObjects.Money;

import java.time.LocalDate;

public record AddTransactionScheduleCommand(
        Money money,
        Money fee,
        Money totalAmount,
        String sourceAccount,
        String targetAccount,
        LocalDate scheduleDate
) {
    public static AddTransactionScheduleCommand fromRequest(
            AddTransactionScheduleRequest request
    ) {
        return new AddTransactionScheduleCommand(
                new Money(request.transferAmount(),
                        CurrencyEnum.valueOf(request.currency())),
                new Money(request.feeAmount(),
                        CurrencyEnum.valueOf(request.currency())),
                new Money(request.totalAmount(),
                        CurrencyEnum.valueOf(request.currency())),
                request.sourceAccount(),
                request.targetAccount(),
                request.scheduleDate()
        );
    }
}
