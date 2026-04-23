package org.viora.viorastreamingcore.content.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.viora.viorastreamingcore.content.dto.MovieDto;
import org.viora.viorastreamingcore.content.dto.MovieSummary;

public interface MovieService {

  Page<MovieSummary> getMovies(Pageable pageable);

  MovieDto getMovieById(Long id);

}
