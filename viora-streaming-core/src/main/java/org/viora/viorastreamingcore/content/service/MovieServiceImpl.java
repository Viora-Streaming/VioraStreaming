package org.viora.viorastreamingcore.content.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.viora.viorastreamingcore.content.dto.MovieDto;
import org.viora.viorastreamingcore.content.dto.MovieSummary;
import org.viora.viorastreamingcore.content.exception.MovieNotFoundException;
import org.viora.viorastreamingcore.content.model.Genre;
import org.viora.viorastreamingcore.content.model.Movie;
import org.viora.viorastreamingcore.content.repository.MovieRepository;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

  private final MovieRepository movieRepository;
  private final ObjectMapper objectMapper;

  @Override
  public Page<MovieSummary> getMovies(Pageable pageable) {
    var movies = movieRepository.findAllWithGenres(pageable);
    return movies.map(this::mapToMovieSummary);
  }

  @Override
  public MovieDto getMovieById(Long id) {
    return movieRepository.findFullMovieById(id)
        .map(movie -> objectMapper.convertValue(movie, MovieDto.class))
        .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));
  }

  private MovieSummary mapToMovieSummary(Movie m) {
    return new MovieSummary(m.getId(), m.getTitle(), m.getPoster(), m.getReleaseDate(),
        m.getGenres().stream().map(Genre::getName).collect(Collectors.toSet()), m.getRating());
  }
}
