package com.viora.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(name = "MovieShortDTO")
public record MovieShortDTO(
    @Schema(format = "uuid", example = "550e8400-e29b-41d4-a716-446655440000")
    UUID id,
    @Schema(example = "Inception")
    String title
) {
}
