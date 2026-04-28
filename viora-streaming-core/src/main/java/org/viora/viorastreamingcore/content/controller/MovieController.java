package org.viora.viorastreamingcore.content.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.viora.viorastreamingcore.content.dto.MovieDto;
import org.viora.viorastreamingcore.content.dto.MovieFilter;
import org.viora.viorastreamingcore.content.dto.MovieSummary;
import org.viora.viorastreamingcore.content.service.MovieService;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

  private final MovieService movieService;

  @GetMapping
  public Page<MovieSummary> searchMovies(@ModelAttribute MovieFilter filter,
      @PageableDefault(size = 20) Pageable pageable) {
    return movieService.searchMovies(filter, pageable);
  }

  @GetMapping("/popular")
  public Page<MovieSummary> getMovies(@PageableDefault(size = 20) Pageable pageable) {
    return movieService.getMovies(pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
    return ResponseEntity.ok(movieService.getMovieById(id));
  }

}
