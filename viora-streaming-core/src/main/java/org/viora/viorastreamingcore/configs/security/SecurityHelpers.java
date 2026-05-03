package org.viora.viorastreamingcore.configs.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.viora.viorastreamingcore.account.dto.Account;

@Service
public class SecurityHelpers {

  public Long getCurrentlyAuthenticatedAccountId() {
    return ((Account) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal()).getId();
  }

}
