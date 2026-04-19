package org.viora.viorastreamingcore.content.service.use_cases;

import org.viora.viorastreamingcore.content.dto.Content;
import org.viora.viorastreamingcore.content.dto.requests.CreateContentRequest;

public interface CreateContentUseCase {
    Content createContent(CreateContentRequest request);
}
