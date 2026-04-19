package org.viora.viorastreamingcore.content.service.use_cases;

import org.viora.viorastreamingcore.content.dto.Content;

public interface GetContentUseCase {
  Content findContentByTitle(String title);
}
