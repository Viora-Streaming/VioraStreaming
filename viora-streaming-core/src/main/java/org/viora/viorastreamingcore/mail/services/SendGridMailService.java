package org.viora.viorastreamingcore.mail.services;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.viora.viorastreamingcore.mail.messages.EmailMessage;

@Service
@RequiredArgsConstructor
public class SendGridMailService implements MailService {

  private final JavaMailSender mailSender;

  private final TemplateEngine engine;

  @Value("${spring.mail.sender.app}")
  private String mailFrom;

  @SneakyThrows
  @Override
  public void sendEmail(String to, EmailMessage message) {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
    helper.setFrom(mailFrom);
    helper.setTo(to);
    helper.setSubject("Confirmation");
    helper.setText(engine.process("emails/base-mail-layout", message.processMailMessage()), true);
    mailSender.send(mimeMessage);
  }


}
