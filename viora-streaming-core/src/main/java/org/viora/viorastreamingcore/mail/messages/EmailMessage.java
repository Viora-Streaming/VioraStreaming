package org.viora.viorastreamingcore.mail.messages;

import lombok.RequiredArgsConstructor;
import org.thymeleaf.context.Context;

@RequiredArgsConstructor
public abstract class EmailMessage {

  private final String template;

  public final Context processMailMessage() {
    Context ctx = getContext();
    if (ctx == null) {
      throw new IllegalStateException("Context cannot be null");
    }
    return ctx;
  }

  protected abstract Context getContext();

}
