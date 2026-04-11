package com.viora.demo.controllers;

import com.viora.demo.controllers.api.HistoryControllerApi;
import com.viora.demo.dto.HistoryCreateRequestDTO;
import com.viora.demo.dto.HistoryListResponseDTO;
import com.viora.demo.dto.HistoryResponseDTO;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HistoryController implements HistoryControllerApi {

  @Override
  public ResponseEntity<HistoryListResponseDTO> list() {
    return ResponseEntity.ok(StubDataFactory.historyList());
  }

  @Override
  public ResponseEntity<HistoryResponseDTO> create(HistoryCreateRequestDTO request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(StubDataFactory.history());
  }

  @Override
  public ResponseEntity<Void> delete(UUID id) {
    return ResponseEntity.noContent().build();
  }
}
