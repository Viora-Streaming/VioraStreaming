package org.viora.viorastreamingcore.account.service;

import org.viora.viorastreamingcore.account.dto.Account;

public interface VerifyUserAccountUseCase {

  void verifyUserAccount(String token);

  void sendVerificationForAccount(Account account);

  void sendDropPasswordVerification(Account account);

  void verifyDropPassword(String token);

}
