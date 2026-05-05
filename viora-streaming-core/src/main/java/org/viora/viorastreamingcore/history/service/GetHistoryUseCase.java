package org.viora.viorastreamingcore.history.service;

import org.viora.viorastreamingcore.history.dto.HistoryDto;
import java.util.List;

public interface GetHistoryUseCase {
  List<HistoryDto> getHistory();

  HistoryDto getHistoryById(Long id);
}
