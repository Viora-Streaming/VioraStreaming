package com.viora.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "HistoryListResponseDTO")
public record HistoryListResponseDTO(
    List<HistoryResponseDTO> items
) {
}
