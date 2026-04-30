package org.viora.viorastreamingcore.streaming.service;

import org.springframework.core.io.Resource;

public interface GetMovieUseCase {

  Resource getMoviePlayback(String id);

  Resource getMovieSegment(String id, Long segmentId);

}
