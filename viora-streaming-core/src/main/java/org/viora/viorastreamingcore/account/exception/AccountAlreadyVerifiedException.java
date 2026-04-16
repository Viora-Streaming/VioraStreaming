package org.viora.viorastreamingcore.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class AccountAlreadyVerifiedException extends RuntimeException {

  public AccountAlreadyVerifiedException(String message) {
    super(message);
  }
}
