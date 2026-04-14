package com.viora.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AiMessageRequestDTO")
public record AiMessageRequestDTO(
    @Schema(example = "Suggest me a science fiction movie")
    String message
) {
}
