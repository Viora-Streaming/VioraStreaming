package org.viora.viorastreamingcore.account.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.viora.viorastreamingcore.account.dto.Account;
import org.viora.viorastreamingcore.account.dto.AccountDto;
import org.viora.viorastreamingcore.account.exception.AccountNotFoundException;
import org.viora.viorastreamingcore.account.model.AccountModel;
import org.viora.viorastreamingcore.account.repository.AccountRepository;
import org.viora.viorastreamingcore.account.service.GetUserAccountUseCase;
import org.viora.viorastreamingcore.configs.security.SecurityHelpers;

@Service
@RequiredArgsConstructor
public class QueryAccountService implements GetUserAccountUseCase {

  private final AccountRepository accountRepository;
  private final ObjectMapper objectMapper;
  private final SecurityHelpers securityHelpers;

  @Override
  public Account findAccountByLogin(String login) {
    return accountRepository.findByEmail(login)
        .map(acc -> new Account(acc.getId(), acc.getEmail(), acc.getPassword()))
        .orElseThrow(
            () -> new AccountNotFoundException("Account with email " + login + " not found"));
  }

  @Override
  public AccountDto getUserAccount() {
    AccountModel model = accountRepository.findById(
            securityHelpers.getCurrentlyAuthenticatedAccountId())
        .orElseThrow(() -> new AccountNotFoundException("Account not verified"));

    return objectMapper.convertValue(model, AccountDto.class);
  }

}
