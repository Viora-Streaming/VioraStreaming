package org.viora.viorastreamingcore.account.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.viora.viorastreamingcore.account.exception.AccountNotFoundException;
import org.viora.viorastreamingcore.account.model.AccountModel;
import org.viora.viorastreamingcore.account.repository.AccountRepository;
import org.viora.viorastreamingcore.account.service.EnableAccountUseCase;

@Service
@RequiredArgsConstructor
public class AccountAuthoritiesService implements EnableAccountUseCase {

  private final AccountRepository accountRepository;

  @Override
  public void enableAccount(String email) {
    AccountModel account = accountRepository.findByEmail(email)
        .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    account.setEnabled(true);
    accountRepository.save(account);
  }
}
