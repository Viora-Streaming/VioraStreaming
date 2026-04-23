package org.viora.viorastreamingcore.configs.handlers;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record ApiError(
    LocalDateTime timeStamp,
    int errorCode,
    String message
) {
}
