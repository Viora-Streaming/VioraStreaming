package org.viora.viorastreamingcore.verification.service;

import org.viora.viorastreamingcore.account.dto.AccountDto;
import org.viora.viorastreamingcore.verification.dto.VerificationType;
import java.util.function.Consumer;

public interface VerificationStrategy {

  void sendVerification(AccountDto accountDto);

  void verify(String token);

  void verify(String token, Consumer<Object> callback);

  boolean canVerify(VerificationType type);

}
