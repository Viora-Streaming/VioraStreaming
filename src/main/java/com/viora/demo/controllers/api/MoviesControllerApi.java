package com.viora.demo.controllers.api;

import com.viora.demo.dto.ErrorResponseDTO;
import com.viora.demo.dto.MovieCreateRequestDTO;
import com.viora.demo.dto.MovieListResponseDTO;
import com.viora.demo.dto.MoviePatchRequestDTO;
import com.viora.demo.dto.MovieResponseDTO;
import com.viora.demo.dto.MovieUpdateRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/movies")
@Tag(name = "Movies", description = "Movie catalog and admin movie management")
public interface MoviesControllerApi {

  @Operation(summary = "Get movie list")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = MovieListResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Bad request",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
  })
  @GetMapping
  ResponseEntity<MovieListResponseDTO> list(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "20") Integer size,
      @RequestParam(required = false) String genre
  );

  @Operation(summary = "Get movie details")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = MovieResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "Movie not found",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
  })
  @GetMapping("/{id}")
  ResponseEntity<MovieResponseDTO> getById(@PathVariable UUID id);

  @Operation(summary = "Search movies")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = MovieListResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Bad request",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
  })
  @GetMapping("/search")
  ResponseEntity<MovieListResponseDTO> search(@RequestParam String query);

  @Operation(summary = "Create movie (Admin)")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Created",
          content = @Content(schema = @Schema(implementation = MovieResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Validation error",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
      @ApiResponse(responseCode = "403", description = "Access denied",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
  })
  @PostMapping
  ResponseEntity<MovieResponseDTO> create(@RequestBody MovieCreateRequestDTO request);

  @Operation(summary = "Update movie (Admin)")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = MovieResponseDTO.class))),
      @ApiResponse(responseCode = "403", description = "Access denied",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "Movie not found",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
  })
  @PutMapping("/{id}")
  ResponseEntity<MovieResponseDTO> update(@PathVariable UUID id, @RequestBody MovieUpdateRequestDTO request);

  @Operation(summary = "Partially update movie (Admin)")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = MovieResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Validation error",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
      @ApiResponse(responseCode = "403", description = "Access denied",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "Movie not found",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
  })
  @PatchMapping("/{id}")
  ResponseEntity<MovieResponseDTO> patch(@PathVariable UUID id, @RequestBody MoviePatchRequestDTO request);

  @Operation(summary = "Delete movie (Admin)")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "No Content"),
      @ApiResponse(responseCode = "403", description = "Access denied",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "Movie not found",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
  })
  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable UUID id);
}
