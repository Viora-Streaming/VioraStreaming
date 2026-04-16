package org.viora.viorastreamingcore.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.viora.viorastreamingcore.account.service.VerifyUserAccountUseCase;

@Controller
@RequiredArgsConstructor
public class VerificationController {

  private final VerifyUserAccountUseCase verifyUserAccountUseCase;

  @GetMapping("/api/v1/accounts/verify")
  public String verifyUserAccount(@RequestParam String token) {
    verifyUserAccountUseCase.verifyUserAccount(token);
    return "redirect:http://localhost:8081/main";
  }

}
