package org.viora.viorastreamingcore.account.service;

import org.viora.viorastreamingcore.account.dto.Account;
import org.viora.viorastreamingcore.account.dto.RegisterUserRequest;

public interface RegisterUserUseCase {

    Account registerUser(RegisterUserRequest request);

}
