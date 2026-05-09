package org.viora.viorastreamingcore.verification.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.viora.viorastreamingcore.account.dto.AccountDto;
import org.viora.viorastreamingcore.mail.messages.EmailMessage;
import org.viora.viorastreamingcore.mail.messages.VerifyEmailMessage;
import org.viora.viorastreamingcore.mail.services.MailService;
import org.viora.viorastreamingcore.verification.dto.VerificationType;
import org.viora.viorastreamingcore.verification.service.VerificationTokenIssuer;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@Service
public class DropPasswordVerificationStrategy extends DefaultVerificationStrategy {

  private static final String CONFIRM_DROP_PASSWORD_ACTION = "Confirm Drop Password";
  private final Map<String, Object> DROP_PASSWORD_TOKEN_CLAIMS = Map.of("drop_password", "true");

  private final String dropPasswordRedirectUrl;
  private final VerificationTokenIssuer tokenIssuer;

  public DropPasswordVerificationStrategy(MailService mailService,
      @Value("${verification.dropped-callback-url}") String dropPasswordRedirectUrl,
      VerificationTokenIssuer tokenIssuer) {
    super(VerificationType.VERIFY_DROP_PASSWORD, mailService);
    this.dropPasswordRedirectUrl = dropPasswordRedirectUrl;
    this.tokenIssuer = tokenIssuer;
  }

  @Override
  public void verify(String token) {
    tokenIssuer.validateAndGetEmailFromToken(token, DROP_PASSWORD_TOKEN_CLAIMS);
  }

  @Override
  public void verify(String token, Consumer<Object> callback) {
    String email = tokenIssuer.validateAndGetEmailFromToken(token, DROP_PASSWORD_TOKEN_CLAIMS);
    callback.accept(email);
  }

  @Override
  protected EmailMessage getMessage(AccountDto accountDto) throws Exception {
    String token = tokenIssuer.issueToken(accountDto, DROP_PASSWORD_TOKEN_CLAIMS);
    String url = dropPasswordRedirectUrl + token;
    return new VerifyEmailMessage(url, CONFIRM_DROP_PASSWORD_ACTION);
  }
}
