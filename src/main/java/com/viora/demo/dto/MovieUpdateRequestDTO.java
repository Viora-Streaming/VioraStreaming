package com.viora.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MovieUpdateRequestDTO")
public record MovieUpdateRequestDTO(
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
    Double rating
) {
}
