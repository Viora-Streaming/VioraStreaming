package com.viora.demo.controllers;

import com.viora.demo.controllers.api.AiControllerApi;
import com.viora.demo.dto.AiMessageRequestDTO;
import com.viora.demo.dto.AiMessageResponseDTO;
import com.viora.demo.dto.RecommendationResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiController implements AiControllerApi {

  @Override
  public ResponseEntity<AiMessageResponseDTO> message(AiMessageRequestDTO request) {
    return ResponseEntity.ok(StubDataFactory.aiMessage());
  }

  @Override
  public ResponseEntity<RecommendationResponseDTO> recommendations(String genre) {
    return ResponseEntity.ok(StubDataFactory.recommendations());
  }
}
