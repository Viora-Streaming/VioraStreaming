package org.viora.viorastreamingcore.auth.exception;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = FORBIDDEN, reason = "Account is disabled")
public class AccountDisabledException extends RuntimeException {

  public AccountDisabledException(String message) {
    super(message);
  }
}
