package org.viora.viorastreamingcore.content.dto;

import java.time.LocalDate;
import java.util.Set;

public record MovieSummary(
    Long id,
    String title,
    String poster,
    LocalDate releaseDate,
    Set<GenreDto> genres,
    Float rating
) {

}
