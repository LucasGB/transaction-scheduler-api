package io.github.lucasgb.transaction_scheduler_api.application.dto.request;

import java.time.LocalDate;

public record UpdateTransactionScheduleRequest(
        LocalDate scheduleDate
) {}