package org.viora.viorastreamingcore.account.service;

import org.viora.viorastreamingcore.account.dto.Account;
import org.viora.viorastreamingcore.account.dto.AccountDto;

public interface GetUserAccountUseCase {

  Account findAccountByLogin(String login);

  AccountDto getUserAccount();

}
