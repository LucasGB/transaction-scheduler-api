package io.github.lucasgb.transaction_scheduler_api.infrastructure.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "Paginated response wrapper")
public record PagedResponse<T>(
        @Schema(example = "0") int page,
        @Schema(example = "20") int size,
        @Schema(example = "150") long totalItems,
        @Schema(example = "8") int totalPages,
        List<T> items
) {
    public static <T> PagedResponse<T> from(Page<T> pageData) {
        return new PagedResponse<>(
                pageData.getNumber(),
                pageData.getSize(),
                pageData.getTotalElements(),
                pageData.getTotalPages(),
                pageData.getContent()
        );
    }
}
