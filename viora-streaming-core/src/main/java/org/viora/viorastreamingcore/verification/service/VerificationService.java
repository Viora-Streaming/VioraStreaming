package org.viora.viorastreamingcore.verification.service;

import org.viora.viorastreamingcore.account.dto.AccountDto;
import org.viora.viorastreamingcore.verification.dto.VerificationType;
import java.util.function.Consumer;

public interface VerificationService {

  void sendVerification(VerificationType type, AccountDto accountDto);

  void verify(VerificationType type, String token);

  void verify(VerificationType type, String token, Consumer<Object> callback);
}
