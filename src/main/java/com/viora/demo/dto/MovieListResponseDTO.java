package com.viora.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "MovieListResponseDTO")
public record MovieListResponseDTO(
    List<MovieResponseDTO> items,
    @Schema(example = "0")
    Integer page,
    @Schema(example = "20")
    Integer size,
    @Schema(example = "120")
    Long total
) {
}
