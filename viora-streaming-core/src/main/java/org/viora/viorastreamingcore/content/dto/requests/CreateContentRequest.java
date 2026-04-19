package org.viora.viorastreamingcore.content.dto.requests;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record CreateContentRequest(
    @NotEmpty(message = "Title must not be empty")
    String title,

    @NotEmpty(message = "Description must not be empty")
    String description,

    @NotEmpty(message = "Description must not be empty")
    String synopsis,

    @NotEmpty(message = "Cast must not be empty")
    List<CastAssignmentRequest> cast
) {

}
