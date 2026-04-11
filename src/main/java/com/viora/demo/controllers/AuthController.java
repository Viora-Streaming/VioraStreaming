package com.viora.demo.controllers;

import com.viora.demo.controllers.api.AuthControllerApi;
import com.viora.demo.dto.LoginRequestDTO;
import com.viora.demo.dto.LoginResponseDTO;
import com.viora.demo.dto.UserRegisterRequestDTO;
import com.viora.demo.dto.UserResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthControllerApi {

  @Override
  public ResponseEntity<UserResponseDTO> register(UserRegisterRequestDTO request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(StubDataFactory.user());
  }

  @Override
  public ResponseEntity<LoginResponseDTO> login(LoginRequestDTO request) {
    return ResponseEntity.ok(StubDataFactory.token());
  }
}
