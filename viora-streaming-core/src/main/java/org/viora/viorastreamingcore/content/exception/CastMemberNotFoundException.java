package org.viora.viorastreamingcore.content.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CastMemberNotFoundException extends RuntimeException {

  public CastMemberNotFoundException(String message) {
    super(message);
  }
}
