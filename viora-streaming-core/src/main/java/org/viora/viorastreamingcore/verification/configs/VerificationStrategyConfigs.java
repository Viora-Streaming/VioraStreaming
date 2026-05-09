package org.viora.viorastreamingcore.verification.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.viora.viorastreamingcore.verification.dto.VerificationType;
import org.viora.viorastreamingcore.verification.service.VerificationStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class VerificationStrategyConfigs {

  @Bean
  public Map<VerificationType, List<VerificationStrategy>> verificationStrategies(
      List<VerificationStrategy> strategies) {
    Map<VerificationType, List<VerificationStrategy>> verificationTypeListMap = new HashMap<>();

    for (VerificationType type : VerificationType.values()) {
      List<VerificationStrategy> strategiesByType = strategies.stream()
          .filter(s -> s.canVerify(type)).toList();
      verificationTypeListMap.put(type, strategiesByType);
    }

    return verificationTypeListMap;
  }

}
