package org.viora.viorastreamingcore.content.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.viora.viorastreamingcore.content.dto.requests.CreateContentRequest;
import org.viora.viorastreamingcore.content.service.implementations.ContentManagementService;

@RestController
@RequestMapping("/api/v1/contents")
@RequiredArgsConstructor
public class ContentController {
  private final ContentManagementService contentManagementService;

  @PostMapping("/create")
  public ResponseEntity<Void> createContent(@Valid @RequestBody CreateContentRequest request) {
    contentManagementService.createContent(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
