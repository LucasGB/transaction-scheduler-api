package io.github.lucasgb.transaction_scheduler_api.infrastructure.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standard API error response")
public record ApiError(
        @Schema(example = "SCHEDULE_DATE_VIOLATION")
        String code,

        @Schema(example = "Schedule date cannot be in the past")
        String message
) {}