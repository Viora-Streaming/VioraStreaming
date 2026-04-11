package com.viora.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(name = "HistoryCreateRequestDTO")
public record HistoryCreateRequestDTO(
    @Schema(format = "uuid", example = "550e8400-e29b-41d4-a716-446655440000")
    UUID movieId,
    @Schema(example = "3600")
    Integer progressSeconds
) {
}
