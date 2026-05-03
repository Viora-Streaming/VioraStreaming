package org.viora.viorastreamingcore.content.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.viora.viorastreamingcore.content.dto.MovieDto;
import org.viora.viorastreamingcore.content.dto.MovieFilter;
import org.viora.viorastreamingcore.content.dto.MovieSummary;

public interface MovieService {

  Page<MovieSummary> searchMovies(MovieFilter filter, Pageable pageable);

  Page<MovieSummary> getMovies(Pageable pageable);

  MovieDto getMovieById(Long id);

  MovieSummary getMovieByImdbId(String imdbId);

}
