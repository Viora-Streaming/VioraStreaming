package org.viora.viorastreamingcore.content.service;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.viora.viorastreamingcore.content.dto.Duration;
import org.viora.viorastreamingcore.content.dto.MovieFilter;
import org.viora.viorastreamingcore.content.model.Genre;
import org.viora.viorastreamingcore.content.model.Movie;
import java.util.List;
import java.util.Set;

public class MovieSpecification {

  public static Specification<Movie> hasTitle(String name) {
    return (root, query, cb) ->
        name == null ? null
            : cb.like(cb.lower(root.get("title")), "%" + name.toLowerCase() + "%");
  }

  public static Specification<Movie> hasGenres(Set<Long> genreIds) {
    return (root, query, cb) -> {
      if (genreIds == null || genreIds.isEmpty()) {
        return null;
      }
      query.distinct(true);
      Join<Movie, Genre> genreJoin = root.join("genres", JoinType.INNER);
      return genreJoin.get("id").in(genreIds);
    };
  }

  public static Specification<Movie> hasRating(Float rating) {
    return (root, query, cb) ->
        rating == null ? null : cb.greaterThan(root.get("rating"), rating);
  }

  public static Specification<Movie> hasReleaseDate(String releaseDate) {
    return (root, query, cb) ->
        releaseDate == null ? null : cb.greaterThan(root.get("releaseDate"), releaseDate);
  }

  public static Specification<Movie> hasDurationBetween(Duration duration) {
    return (root, query, cb) ->
        duration == null ? null
            : cb.between(root.get("durationInMinutes"), duration.from(), duration.to());
  }

  public static Specification<Movie> buildSpecification(MovieFilter filter) {
    List<Specification<Movie>> specs = List.of(
        hasTitle(filter.search()),
        hasGenres(filter.genresIds()),
        hasRating(filter.rating()),
        hasReleaseDate(filter.releaseYear()),
        hasDurationBetween(filter.duration())
    );
    return Specification.allOf(specs);
  }

}