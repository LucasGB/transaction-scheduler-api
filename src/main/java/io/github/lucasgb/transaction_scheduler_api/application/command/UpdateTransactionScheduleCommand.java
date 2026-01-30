package io.github.lucasgb.transaction_scheduler_api.application.command;

import io.github.lucasgb.transaction_scheduler_api.application.dto.request.UpdateTransactionScheduleRequest;

import java.time.LocalDate;

public record UpdateTransactionScheduleCommand(
        Long id,
        LocalDate scheduleDate
) {
    public static UpdateTransactionScheduleCommand fromRequest(Long id, UpdateTransactionScheduleRequest request) {
        return new UpdateTransactionScheduleCommand(id, request.scheduleDate());
    }
}