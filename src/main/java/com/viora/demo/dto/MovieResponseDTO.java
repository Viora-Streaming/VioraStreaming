package com.viora.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(name = "MovieResponseDTO")
public record MovieResponseDTO(
    @Schema(format = "uuid", example = "550e8400-e29b-41d4-a716-446655440000")
    UUID id,
    @Schema(example = "Inception")
    String title,
    @Schema(example = "A mind-bending sci-fi thriller")
    String description,
    @Schema(example = "Sci-Fi")
    String genre,
    @Schema(example = "2010")
    Integer releaseYear,
    @Schema(example = "148")
    Integer durationMinutes,
    @Schema(example = "8.8")
    Double rating,
    @Schema(example = "2026-04-09T12:00:00Z")
    OffsetDateTime createdAt
) {
}
