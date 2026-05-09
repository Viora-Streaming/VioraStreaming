package org.viora.viorastreamingcore.verification.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.viora.viorastreamingcore.account.dto.AccountDto;
import org.viora.viorastreamingcore.account.service.EnableAccountUseCase;
import org.viora.viorastreamingcore.mail.messages.EmailMessage;
import org.viora.viorastreamingcore.mail.messages.VerifyEmailMessage;
import org.viora.viorastreamingcore.mail.services.MailService;
import org.viora.viorastreamingcore.verification.dto.VerificationType;
import org.viora.viorastreamingcore.verification.service.VerificationTokenIssuer;
import java.util.Map;

@Slf4j
@Service
public class EmailVerificationStrategy extends DefaultVerificationStrategy {

  private final Map<String, Object> EMAIL_TOKEN_CLAIMS = Map.of("verify_email", "true");

  private final String registerCallbackUrl;
  private final EnableAccountUseCase enableAccountUseCase;
  private final VerificationTokenIssuer tokenIssuer;

  public EmailVerificationStrategy(MailService mailService,
      @Value("${verification.register-callback-url}") String registerCallbackUrl,
      EnableAccountUseCase enableAccountUseCase, VerificationTokenIssuer tokenIssuer) {
    super(VerificationType.VERIFY_EMAIL, mailService);
    this.registerCallbackUrl = registerCallbackUrl;
    this.enableAccountUseCase = enableAccountUseCase;
    this.tokenIssuer = tokenIssuer;
  }

  @Override
  public void verify(String token) {
    String email = tokenIssuer.validateAndGetEmailFromToken(token, EMAIL_TOKEN_CLAIMS);
    enableAccountUseCase.enableAccount(email);
  }

  @Override
  protected EmailMessage getMessage(AccountDto accountDto) throws Exception {
    String token = tokenIssuer.issueToken(accountDto, EMAIL_TOKEN_CLAIMS);
    String url = registerCallbackUrl + token;
    return new VerifyEmailMessage(url);
  }
}
