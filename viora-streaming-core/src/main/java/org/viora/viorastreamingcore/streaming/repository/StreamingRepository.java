package org.viora.viorastreamingcore.streaming.repository;

import org.springframework.core.io.Resource;

public interface StreamingRepository {

  Resource getMoviePlayback(String id);

  Resource getMovieSegment(String id, Long segmentId);
}
