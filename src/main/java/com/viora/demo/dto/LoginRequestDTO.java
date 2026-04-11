package com.viora.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginRequestDTO")
public record LoginRequestDTO(
    @Schema(example = "user@example.com", format = "email")
    String email,
    @Schema(example = "secret123")
    String password
) {
}
