package org.viora.viorastreamingcore.streaming.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.viora.viorastreamingcore.streaming.service.GetMovieUseCase;

@RestController
@RequestMapping("/api/v1/streaming")
@RequiredArgsConstructor
public class VideoStreamingController {

  private final GetMovieUseCase getMovieUseCase;

  @GetMapping("/movies/{id}/index.m3u8")
  public ResponseEntity<Resource> getPlaylist(@PathVariable String id) {
    Resource resource = getMovieUseCase.getMoviePlayback(id);
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType("application/vnd.apple.mpegurl"))
        .body(resource);
  }

  @GetMapping("/movies/{id}/segment_{segment}.ts")
  public ResponseEntity<Resource> getSegment(@PathVariable String id, @PathVariable Long segment) {
    Resource resource = getMovieUseCase.getMovieSegment(id, segment);
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType("video/mp2t"))
        .body(resource);
  }

}
