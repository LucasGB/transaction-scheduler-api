package io.github.lucasgb.transaction_scheduler_api.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

@Schema(
        name = "FetchTransactionScheduleRequest",
        description = "Filter and pagination parameters for fetching scheduled transactions"
)
public record FetchTransactionScheduleRequest(
        @Schema(
                description = "Filter by source account identifier",
                example = "ACC12345",
                minLength = 5,
                maxLength = 20,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @Pattern(regexp = "^[A-Z0-9]{5,20}$", message = "Invalid account format")
        String sourceAccount,

        @Schema(
                description = "Filter by target account identifier",
                example = "ACC12345",
                minLength = 5,
                maxLength = 20,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @Pattern(regexp = "^[A-Z0-9]{5,20}$", message = "Invalid account format")
        String targetAccount,

        @Schema(
                description = "Start date for scheduled transaction search (inclusive)",
                example = "2026-02-01"
        )
        LocalDate scheduleDateFrom,

        @Schema(
                description = "End date for scheduled transaction search (inclusive)",
                example = "2026-02-28"
        )
        LocalDate scheduleDateTo,

        @Schema(
                description = "Zero-based page index",
                example = "0",
                defaultValue = "0"
        )
        Integer page,

        @Schema(
                description = "Page size",
                example = "20",
                defaultValue = "20",
                minimum = "1",
                maximum = "100"
        )
        Integer size
) {
    public FetchTransactionScheduleRequest {
        page = page != null && page >= 0 ? page : 0;
        size = size != null && size > 0 ? size : 20;
    }
}
