package com.viora.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(name = "UserResponseDTO")
public record UserResponseDTO(
    @Schema(format = "uuid", example = "550e8400-e29b-41d4-a716-446655440000")
    UUID id,
    @Schema(example = "user@example.com")
    String email,
    @Schema(example = "John Doe")
    String fullName,
    @Schema(example = "USER", allowableValues = {"USER", "ADMIN"})
    String role,
    @Schema(example = "2026-04-09T12:00:00Z")
    OffsetDateTime createdAt
) {
}
