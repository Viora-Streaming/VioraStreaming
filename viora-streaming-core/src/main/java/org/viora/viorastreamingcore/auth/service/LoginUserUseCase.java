package org.viora.viorastreamingcore.auth.service;

import org.viora.viorastreamingcore.auth.dto.LoginUserRequest;

public interface LoginUserUseCase {

  String loginUser(LoginUserRequest request);

}
