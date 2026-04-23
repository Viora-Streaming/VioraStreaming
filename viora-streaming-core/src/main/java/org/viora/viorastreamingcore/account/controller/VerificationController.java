package org.viora.viorastreamingcore.account.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.viora.viorastreamingcore.account.service.VerifyUserAccountUseCase;

@Controller
@RequiredArgsConstructor
public class VerificationController {

  private static final String JWT_TOKEN = "JWT_TOKEN";
  private final VerifyUserAccountUseCase verifyUserAccountUseCase;

  @GetMapping("/api/v1/accounts/verify")
  public String verifyUserAccount(@RequestParam String token,
      @Value("${client.callback-url}") String callback, HttpServletResponse response) {
    verifyUserAccountUseCase.verifyUserAccount(token);
    response.addCookie(new Cookie(JWT_TOKEN, token));
    return "redirect:" + callback;
  }

}
