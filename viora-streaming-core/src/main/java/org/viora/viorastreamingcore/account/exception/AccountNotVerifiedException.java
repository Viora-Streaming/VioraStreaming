package org.viora.viorastreamingcore.account.exception;

import org.viora.viorastreamingcore.exceptions.AccountNotAllowedException;

public class AccountNotVerifiedException extends AccountNotAllowedException {

  public AccountNotVerifiedException(String message) {
    super(message);
  }
}
