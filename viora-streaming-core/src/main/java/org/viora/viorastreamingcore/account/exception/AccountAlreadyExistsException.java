package org.viora.viorastreamingcore.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.viora.viorastreamingcore.exceptions.EntityConflictException;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class AccountAlreadyExistsException extends EntityConflictException {

  public AccountAlreadyExistsException(String message) {
    super(message);
  }
}
