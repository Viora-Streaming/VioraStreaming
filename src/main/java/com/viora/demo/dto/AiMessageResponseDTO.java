package com.viora.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "AiMessageResponseDTO")
public record AiMessageResponseDTO(
    @Schema(example = "You may like Inception based on your watch history.")
    String response,
    List<MovieShortDTO> suggestedMovies
) {
}
