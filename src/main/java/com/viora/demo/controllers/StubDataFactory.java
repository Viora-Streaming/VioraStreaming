package com.viora.demo.controllers;

import com.viora.demo.dto.AiMessageResponseDTO;
import com.viora.demo.dto.HistoryListResponseDTO;
import com.viora.demo.dto.HistoryResponseDTO;
import com.viora.demo.dto.LoginResponseDTO;
import com.viora.demo.dto.MovieListResponseDTO;
import com.viora.demo.dto.MovieResponseDTO;
import com.viora.demo.dto.MovieShortDTO;
import com.viora.demo.dto.RecommendationResponseDTO;
import com.viora.demo.dto.RecommendedMovieDTO;
import com.viora.demo.dto.UserResponseDTO;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

final class StubDataFactory {

  private StubDataFactory() {
  }

  static UserResponseDTO user() {
    return new UserResponseDTO(
        UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
        "user@example.com",
        "John Doe",
        "USER",
        OffsetDateTime.parse("2026-04-09T12:00:00Z")
    );
  }

  static LoginResponseDTO token() {
    return new LoginResponseDTO("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...");
  }

  static MovieResponseDTO movie() {
    return new MovieResponseDTO(
        UUID.fromString("660e8400-e29b-41d4-a716-446655440000"),
        "Inception",
        "A mind-bending sci-fi thriller",
        "Sci-Fi",
        2010,
        148,
        8.8,
        OffsetDateTime.parse("2026-04-09T12:00:00Z")
    );
  }

  static MovieListResponseDTO movieList() {
    return new MovieListResponseDTO(List.of(movie()), 0, 20, 1L);
  }

  static HistoryResponseDTO history() {
    return new HistoryResponseDTO(
        UUID.fromString("770e8400-e29b-41d4-a716-446655440000"),
        new MovieShortDTO(UUID.fromString("660e8400-e29b-41d4-a716-446655440000"), "Inception"),
        3600,
        OffsetDateTime.parse("2026-04-09T12:00:00Z")
    );
  }

  static HistoryListResponseDTO historyList() {
    return new HistoryListResponseDTO(List.of(history()));
  }

  static AiMessageResponseDTO aiMessage() {
    return new AiMessageResponseDTO(
        "You may like Inception based on your watch history.",
        List.of(new MovieShortDTO(UUID.fromString("660e8400-e29b-41d4-a716-446655440000"), "Inception"))
    );
  }

  static RecommendationResponseDTO recommendations() {
    return new RecommendationResponseDTO(
        List.of(new RecommendedMovieDTO(
            UUID.fromString("660e8400-e29b-41d4-a716-446655440000"),
            "Inception",
            "Sci-Fi",
            8.7
        ))
    );
  }
}
