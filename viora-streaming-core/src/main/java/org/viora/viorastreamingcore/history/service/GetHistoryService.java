package org.viora.viorastreamingcore.history.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.viora.viorastreamingcore.configs.security.SecurityHelpers;
import org.viora.viorastreamingcore.content.dto.MovieSummary;
import org.viora.viorastreamingcore.content.service.MovieService;
import org.viora.viorastreamingcore.history.dto.HistoryDto;
import org.viora.viorastreamingcore.history.model.History;
import org.viora.viorastreamingcore.history.repository.HistoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class GetHistoryService implements GetHistoryUseCase {

  private final MovieService movieService;
  private final SecurityHelpers securityHelpers;
  private final HistoryRepository repository;

  @Override
  public List<HistoryDto> getHistory() {
    List<History> userHistory = repository.getHistoryByAccountId(
        securityHelpers.getCurrentlyAuthenticatedAccountId());

    Set<Long> movieIds = userHistory.stream().map(h -> h.getMovie().getId())
        .collect(Collectors.toSet());

    List<MovieSummary> movies = movieService.getMoviesByIds(movieIds);

    Map<Long, MovieSummary> movieSummaryMap = movies.stream()
        .collect(Collectors.toMap(MovieSummary::id, movie -> movie));

    List<HistoryDto> historyDtos = new ArrayList<>();
    for (History h : userHistory) {
      MovieSummary movieSummary = movieSummaryMap.get(h.getMovie().getId());
      HistoryDto dto = new HistoryDto(movieSummary, h.getLastWatchedAt());
      historyDtos.add(dto);
    }

    return historyDtos;
  }

  @Override
  public HistoryDto getHistoryById(Long movieId) {
    MovieSummary summary = movieService.getMovieSummaryById(movieId);
    return repository.findByAccountIdAndMovieId(
            securityHelpers.getCurrentlyAuthenticatedAccountId(), movieId)
        .map(h -> new HistoryDto(summary, h.getLastWatchedAt()))
        .orElse(new HistoryDto(summary, 0L));
  }
}
