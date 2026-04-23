package org.viora.viorastreamingcore.mail.messages;

import lombok.Getter;
import org.thymeleaf.context.Context;

@Getter
public class VerifyEmailMessage extends EmailMessage {

  private static final String DEFAULT_ACTION_TEXT = "Confirm";

  private final String actionUrl;
  private final String actionText;

  public VerifyEmailMessage(String actionUrl, String actionText) {
    super("templates/emails/base-mail-layout");
    this.actionUrl = actionUrl;
    this.actionText = actionText;
  }

  public VerifyEmailMessage(String actionUrl) {
    super("templates/emails/base-mail-layout");
    this.actionUrl = actionUrl;
    this.actionText = DEFAULT_ACTION_TEXT;
  }

  @Override
  protected Context getContext() {
    Context ctx = new Context();
    ctx.setVariable("actionUrl", actionUrl);
    ctx.setVariable("actionText", actionText);
    return ctx;
  }
}
