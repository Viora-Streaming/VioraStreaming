package org.viora.viorastreamingcore.account.service;

import org.viora.viorastreamingcore.account.dto.AccountDto;
import org.viora.viorastreamingcore.account.dto.UpdateAccountRequest;

public interface UpdateAccountUseCase {

  AccountDto updateUser(UpdateAccountRequest request);

}
