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
  private static final String DROP_PASSWORD_TOKEN = "DROP_PASSWORD_TOKEN";
  private final VerifyUserAccountUseCase verifyUserAccountUseCase;

  @GetMapping("/api/v1/accounts/verify")
  public String verifyUserAccount(@RequestParam String token,
      @Value("${client.register-callback-url}") String callback, HttpServletResponse response) {
    verifyUserAccountUseCase.verifyUserAccount(token);
    response.addCookie(new Cookie(JWT_TOKEN, token));
    return "redirect:" + callback;
  }

  @GetMapping("/api/v1/accounts/drop-password/verify")
  public String verifyDropPassword(
      @RequestParam String token,
      @Value("${client.dropped-callback-url}") String callback,
      HttpServletResponse response) {

    verifyUserAccountUseCase.verifyDropPassword(token);

    Cookie cookie = new Cookie(DROP_PASSWORD_TOKEN, token);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setMaxAge(15 * 60);
    response.addCookie(cookie);

    return "redirect:" + callback;
  }

}
