package org.viora.viorastreamingcore.streaming.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.viora.viorastreamingcore.configs.security.SecurityHelpers;
import org.viora.viorastreamingcore.content.dto.MovieSummary;
import org.viora.viorastreamingcore.content.service.MovieService;
import org.viora.viorastreamingcore.history.service.command.SaveHistoryCommand;
import org.viora.viorastreamingcore.streaming.repository.StreamingRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StreamingService implements GetMovieUseCase {

  private final StreamingRepository streamingRepository;
  private final MovieService movieService;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final SecurityHelpers securityHelpers;

  @Override
  public Resource getMoviePlayback(String id) {
    if (Objects.isNull(id)) {
      throw new IllegalArgumentException("Id cannot be null");
    }
    return streamingRepository.getMoviePlayback(id);
  }

  @Override
  public Resource getMovieSegment(String id, Long segmentId) {
    if (id == null || segmentId == null) {
      throw new IllegalArgumentException("Neither id nor segmentId can be null");
    }
    publishEvent(id, segmentId);
    return streamingRepository.getMovieSegment(id, segmentId);
  }

  private void publishEvent(String id, Long segmentId) {
    MovieSummary movieSummary = movieService.getMovieByImdbId(id);
    applicationEventPublisher.publishEvent(
        new SaveHistoryCommand(this, securityHelpers.getCurrentlyAuthenticatedAccountId(),
            movieSummary.id(),
            segmentId));
  }

}
