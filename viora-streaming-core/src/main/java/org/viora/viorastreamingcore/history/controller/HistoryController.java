package org.viora.viorastreamingcore.history.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.viora.viorastreamingcore.history.dto.HistoryDto;
import org.viora.viorastreamingcore.history.service.GetHistoryUseCase;
import java.util.List;

@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
public class HistoryController {

  private final GetHistoryUseCase getHistoryUseCase;

  @GetMapping
  public List<HistoryDto> getHistory() {
    return getHistoryUseCase.getHistory();
  }

}
