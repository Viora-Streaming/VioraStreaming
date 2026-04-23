package org.viora.viorastreamingcore.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class AccountAlreadyExistsException extends RuntimeException {

  public AccountAlreadyExistsException(String message) {
    super(message);
  }
}
