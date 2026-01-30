package io.github.lucasgb.transaction_scheduler_api.application.command;

import io.github.lucasgb.transaction_scheduler_api.application.dto.request.UpdateTransactionScheduleRequest;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateTransactionScheduleCommand(
        Long id,
        BigDecimal newTransferAmount,
        LocalDate scheduleDate
) {
    public static UpdateTransactionScheduleCommand fromRequest(Long id, UpdateTransactionScheduleRequest request) {
        return new UpdateTransactionScheduleCommand(id, request.newTransferAmount(), request.scheduleDate());
    }

    public boolean hasUpdates() {
        return newTransferAmount != null || scheduleDate != null;
    }
}