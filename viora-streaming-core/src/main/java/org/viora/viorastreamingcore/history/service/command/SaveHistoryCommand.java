package org.viora.viorastreamingcore.history.service.command;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SaveHistoryCommand extends ApplicationEvent {

  private final Long accountId;
  private final Long movieId;
  private final Long segment;

  public SaveHistoryCommand(Object source, Long accountId, Long movieId, Long segment) {
    super(source);
    this.accountId = accountId;
    this.movieId = movieId;
    this.segment = segment;
  }

}
