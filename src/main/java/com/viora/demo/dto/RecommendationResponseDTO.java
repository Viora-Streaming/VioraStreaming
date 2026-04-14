package com.viora.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "RecommendationResponseDTO")
public record RecommendationResponseDTO(
    List<RecommendedMovieDTO> recommendations
) {
}
