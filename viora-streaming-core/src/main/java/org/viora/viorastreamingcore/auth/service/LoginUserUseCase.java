package org.viora.viorastreamingcore.auth.service;

import org.viora.viorastreamingcore.auth.dto.LoginUserRequest;
import org.viora.viorastreamingcore.auth.dto.LoginUserResponse;

public interface LoginUserUseCase {

  LoginUserResponse loginUser(LoginUserRequest request);

}
