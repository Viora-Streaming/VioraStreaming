package org.viora.viorastreamingcore.content.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.viora.viorastreamingcore.exceptions.EntityNotFoundException;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Movie not found")
public class MovieNotFoundException extends EntityNotFoundException {

  public MovieNotFoundException(String message) {
    super(message);
  }
}
