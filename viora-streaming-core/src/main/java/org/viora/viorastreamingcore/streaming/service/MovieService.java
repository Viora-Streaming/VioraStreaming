package org.viora.viorastreamingcore.streaming.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.viora.viorastreamingcore.streaming.repository.StreamingRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MovieService implements GetMovieUseCase {

  private final StreamingRepository streamingRepository;

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
    return streamingRepository.getMovieSegment(id, segmentId);
  }

}
