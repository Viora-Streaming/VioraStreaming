package com.viora.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.List;

@Schema(name = "ErrorResponseDTO", description = "Unified API error response")
public record ErrorResponseDTO(
    @Schema(description = "Timestamp when error occurred", example = "2026-04-09T12:10:00Z")
    OffsetDateTime timestamp,
    @Schema(description = "Technical error code", example = "VALIDATION_ERROR")
    String errorCode,
    @Schema(description = "Human-readable message", example = "Invalid request body")
    String message,
    @Schema(description = "Optional details for validation or diagnostics")
    List<String> details
) {
}
