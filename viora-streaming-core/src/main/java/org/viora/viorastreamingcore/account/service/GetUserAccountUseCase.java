package org.viora.viorastreamingcore.account.service;

import org.viora.viorastreamingcore.account.dto.Account;

public interface GetUserAccountUseCase {

  Account findAccountByLogin(String login);

}
