package org.viora.viorastreamingcore.verification.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.viora.viorastreamingcore.account.dto.AccountDto;
import org.viora.viorastreamingcore.verification.dto.VerificationType;
import org.viora.viorastreamingcore.verification.service.VerificationService;
import org.viora.viorastreamingcore.verification.service.VerificationStrategy;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class StrategyVerificationService implements VerificationService {

  private final Map<VerificationType, List<VerificationStrategy>> verificationStrategies;

  @Override
  public void sendVerification(VerificationType type, AccountDto accountDto) {
    if (type == null || accountDto == null) {
      throw new IllegalArgumentException("Type and accountDto cannot be null");
    }
    List<VerificationStrategy> strategies = verificationStrategies.get(type);
    strategies.forEach(strategy -> strategy.sendVerification(accountDto));
  }

  @Override
  public void verify(VerificationType type, String token, Consumer<Object> callback) {
    if (type == null || token == null) {
      throw new IllegalArgumentException("Type and token cannot be null");
    }
    List<VerificationStrategy> strategies = verificationStrategies.get(type);
    strategies.forEach(strategy -> strategy.verify(token, callback));
  }

  @Override
  public void verify(VerificationType type, String token) {
    if (type == null || token == null) {
      throw new IllegalArgumentException("Type and token cannot be null");
    }
    List<VerificationStrategy> strategies = verificationStrategies.get(type);
    strategies.forEach(strategy -> strategy.verify(token));
  }
}
