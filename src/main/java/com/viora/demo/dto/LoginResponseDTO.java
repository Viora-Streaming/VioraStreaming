package com.viora.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginResponseDTO")
public record LoginResponseDTO(
    @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    String token
) {
}
