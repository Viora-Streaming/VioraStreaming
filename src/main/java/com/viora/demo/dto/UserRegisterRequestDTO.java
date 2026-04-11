package com.viora.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserRegisterRequestDTO")
public record UserRegisterRequestDTO(
    @Schema(example = "user@example.com", format = "email")
    String email,
    @Schema(example = "secret123", minLength = 6)
    String password,
    @Schema(example = "John Doe")
    String fullName
) {
}
