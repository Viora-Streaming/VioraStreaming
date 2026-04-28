package org.viora.viorastreamingcore.content.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.viora.viorastreamingcore.content.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>,
    JpaSpecificationExecutor<Movie> {

  @Query(value = """
      SELECT m FROM Movie m
      LEFT JOIN FETCH m.genres
      """,
      countQuery = """
          SELECT COUNT(m) AS moviesCount FROM Movie m
          """,
      countName = "moviesCount"
  )
  Page<Movie> findAllWithGenres(Pageable pageable);

  @Query("""
      SELECT m FROM Movie m
      LEFT JOIN FETCH m.actors
      LEFT JOIN FETCH m.director
      LEFT JOIN FETCH m.writer
      LEFT JOIN FETCH m.genres
      WHERE m.id = :id
      """)
  Optional<Movie> findFullMovieById(@Param("id") Long id);

}
