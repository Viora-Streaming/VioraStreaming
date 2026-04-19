package org.viora.viorastreamingcore.content.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ContentAlreadyExistsException extends RuntimeException {

  public ContentAlreadyExistsException(String message) {
    super(message);
  }
}
