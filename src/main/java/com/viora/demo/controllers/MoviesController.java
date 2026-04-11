package com.viora.demo.controllers;

import com.viora.demo.controllers.api.MoviesControllerApi;
import com.viora.demo.dto.MovieCreateRequestDTO;
import com.viora.demo.dto.MovieListResponseDTO;
import com.viora.demo.dto.MoviePatchRequestDTO;
import com.viora.demo.dto.MovieResponseDTO;
import com.viora.demo.dto.MovieUpdateRequestDTO;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoviesController implements MoviesControllerApi {

  @Override
  public ResponseEntity<MovieListResponseDTO> list(Integer page, Integer size, String genre) {
    return ResponseEntity.ok(StubDataFactory.movieList());
  }

  @Override
  public ResponseEntity<MovieResponseDTO> getById(UUID id) {
    return ResponseEntity.ok(StubDataFactory.movie());
  }

  @Override
  public ResponseEntity<MovieListResponseDTO> search(String query) {
    return ResponseEntity.ok(StubDataFactory.movieList());
  }

  @Override
  public ResponseEntity<MovieResponseDTO> create(MovieCreateRequestDTO request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(StubDataFactory.movie());
  }

  @Override
  public ResponseEntity<MovieResponseDTO> update(UUID id, MovieUpdateRequestDTO request) {
    return ResponseEntity.ok(StubDataFactory.movie());
  }

  @Override
  public ResponseEntity<MovieResponseDTO> patch(UUID id, MoviePatchRequestDTO request) {
    return ResponseEntity.ok(StubDataFactory.movie());
  }

  @Override
  public ResponseEntity<Void> delete(UUID id) {
    return ResponseEntity.noContent().build();
  }
}
