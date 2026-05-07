package org.viora.viorastreamingcore.account.service;

public interface DropPasswordUseCase {

  void dropPassword(String email);

  void updatePassword(String token, String password);

}
