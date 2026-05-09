package org.viora.viorastreamingcore.verification.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.viora.viorastreamingcore.account.dto.AccountDto;
import org.viora.viorastreamingcore.mail.messages.EmailMessage;
import org.viora.viorastreamingcore.mail.services.MailService;
import org.viora.viorastreamingcore.verification.dto.VerificationType;
import org.viora.viorastreamingcore.verification.service.VerificationStrategy;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public abstract class DefaultVerificationStrategy implements VerificationStrategy {

  private final VerificationType type;
  private final MailService mailService;

  @Override
  public void sendVerification(AccountDto accountDto) {
    try {
      EmailMessage message = getMessage(accountDto);
      mailService.sendEmail(accountDto.email(), message);
    } catch (Exception e) {
      log.error("Error sending email, e: {}", e.getMessage());
      throw new RuntimeException(e);
    }
  }

  @Override
  public void verify(String token, Consumer<Object> callback) {
    verify(token);
  }

  @Override
  public boolean canVerify(VerificationType type) {
    return this.type.equals(type);
  }

  protected abstract EmailMessage getMessage(AccountDto accountDto) throws Exception;

}
