package org.viora.viorastreamingcore.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.viora.viorastreamingcore.exceptions.EntityNotFoundException;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends EntityNotFoundException {

  public AccountNotFoundException(String message) {
    super(message);
  }
}
