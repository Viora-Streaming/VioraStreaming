package org.viora.viorastreamingcore.history.dto;

import org.viora.viorastreamingcore.content.dto.MovieSummary;

public record HistoryDto(
    MovieSummary movie,
    Long lastWatchedAt
) {

}
