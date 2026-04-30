package org.viora.viorastreamingcore.streaming.repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.SneakyThrows;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class LocalMovieRepository implements StreamingRepository {

  //TODO this is going to be changed when I will fix docker compose
  // Currently it is working only on local machine
  private static final String ROOT_SEGMENTS_FOLDER_PATH =
      System.getProperty("user.home") + "/Desktop/movies/segments/";
  private static final int MINIMAL_SEGMENT_ID_LENGTH = 3;

  @Override
  @SneakyThrows
  public Resource getMoviePlayback(String movieId) {
    Path playlistPath = Paths.get(ROOT_SEGMENTS_FOLDER_PATH, movieId, "playlist.m3u8");
    if (!Files.exists(playlistPath)) {
      throw new IllegalArgumentException("Playlist not found for movie " + movieId);
    }
    return new FileSystemResource(playlistPath);
  }

  @Override
  @SneakyThrows
  public Resource getMovieSegment(String movieId, Long segment) {
    String segmentStringId = String.format("segment_%s.ts", getSegmentStringId(segment));
    Path segmentPath = Paths.get(ROOT_SEGMENTS_FOLDER_PATH, movieId, segmentStringId);
    if (!Files.exists(segmentPath)) {
      throw new IllegalArgumentException("Segment " + segment + " not found for movie " + movieId);
    }
    return new FileSystemResource(segmentPath);
  }

  private String getSegmentStringId(Long segment) {
    if (segment >= 100) {
      return segment.toString();
    }

    StringBuilder resultBuilder = new StringBuilder();
    long currentNumber = segment;
    while (currentNumber > 0) {
      long floatingNumber = currentNumber % 10;
      currentNumber /= 10;
      resultBuilder.append(floatingNumber);
    }

    int resultLength = resultBuilder.length();
    if (resultLength < MINIMAL_SEGMENT_ID_LENGTH) {
      resultBuilder.append("0".repeat(MINIMAL_SEGMENT_ID_LENGTH - resultLength));
    }

    resultBuilder.reverse();
    return resultBuilder.toString();
  }

}
