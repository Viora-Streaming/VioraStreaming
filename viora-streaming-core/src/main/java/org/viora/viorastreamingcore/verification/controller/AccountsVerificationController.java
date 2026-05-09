package org.viora.viorastreamingcore.verification.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.viora.viorastreamingcore.verification.dto.VerificationType;
import org.viora.viorastreamingcore.verification.service.VerificationService;

import static org.viora.viorastreamingcore.account.controller.DropPasswordController.DROP_PASSWORD_TOKEN;

@Controller
@RequestMapping("/api/v1/verification/accounts")
@RequiredArgsConstructor
public class AccountsVerificationController {

  private final VerificationService verificationService;

  @GetMapping("register")
  public String verifyUserAccount(@RequestParam String token,
      @Value("${client.register-callback-url}") String callback) {
    verificationService.verify(VerificationType.VERIFY_EMAIL, token);
    return "redirect:" + callback;
  }

  @GetMapping("drop-password")
  public String verifyDropPassword(
      @RequestParam String token,
      @Value("${client.dropped-callback-url}") String callback,
      HttpServletResponse response) {

    verificationService.verify(VerificationType.VERIFY_DROP_PASSWORD, token);

    Cookie cookie = new Cookie(DROP_PASSWORD_TOKEN, token);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setMaxAge(15 * 60);
    response.addCookie(cookie);

    return "redirect:" + callback;
  }


}
