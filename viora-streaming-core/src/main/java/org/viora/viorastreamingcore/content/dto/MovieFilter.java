package org.viora.viorastreamingcore.content.dto;

import java.util.Set;

public record MovieFilter(
    String search,
    Set<Long> genresIds,
    Float rating,
    String releaseYear,
    Duration duration
) {

}
