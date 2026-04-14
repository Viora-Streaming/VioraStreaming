package com.viora.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserUpdateRequestDTO")
public record UserUpdateRequestDTO(
    @Schema(example = "John Doe")
    String name,
    @Schema(example = "https://cdn.example.com/avatar.png")
    String avatar
) {
}
