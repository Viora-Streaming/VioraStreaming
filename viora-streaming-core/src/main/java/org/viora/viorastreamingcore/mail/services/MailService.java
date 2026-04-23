package org.viora.viorastreamingcore.mail.services;

import org.viora.viorastreamingcore.mail.messages.EmailMessage;

public interface MailService {

  void sendEmail(String to, EmailMessage mailMessage) throws Exception;

}
