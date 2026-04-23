package org.viora.viorastreamingcore.content.dto;

import java.time.LocalDate;
import java.util.Set;

public record MovieSummary(
    String title,
    String poster,
    LocalDate releaseDate,
    Set<String> genres
) {

}
