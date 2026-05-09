package org.viora.viorastreamingcore.account.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.viora.viorastreamingcore.account.dto.AccountDto;
import org.viora.viorastreamingcore.account.exception.AccountNotFoundException;
import org.viora.viorastreamingcore.account.model.AccountModel;
import org.viora.viorastreamingcore.account.repository.AccountRepository;
import org.viora.viorastreamingcore.account.service.DropPasswordUseCase;
import org.viora.viorastreamingcore.verification.dto.VerificationType;
import org.viora.viorastreamingcore.verification.service.VerificationService;

@Service
@RequiredArgsConstructor
public class DropAccountsPasswordService implements DropPasswordUseCase {

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;
  private final VerificationService verificationService;
  private final ObjectMapper objectMapper;

  @Override
  public void dropPassword(String email) {
    AccountModel model = accountRepository.findByEmail(email)
        .orElseThrow(
            () -> new AccountNotFoundException("Account with email " + email + " not found"));
    AccountDto accountDto = objectMapper.convertValue(model, AccountDto.class);
    verificationService.sendVerification(VerificationType.VERIFY_DROP_PASSWORD, accountDto);
  }

  @Override
  public void updatePassword(String email, String password) {
    AccountModel model = accountRepository.findByEmail(email)
        .orElseThrow(
            () -> new AccountNotFoundException("Account with email " + email + " not found"));
    model.setPassword(passwordEncoder.encode(password));
    accountRepository.save(model);
  }

}
