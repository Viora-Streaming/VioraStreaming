package com.viora.demo.controllers;

import com.viora.demo.controllers.api.UsersControllerApi;
import com.viora.demo.dto.UserResponseDTO;
import com.viora.demo.dto.UserUpdateRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController implements UsersControllerApi {

  @Override
  public ResponseEntity<UserResponseDTO> me() {
    return ResponseEntity.ok(StubDataFactory.user());
  }

  @Override
  public ResponseEntity<UserResponseDTO> update(UserUpdateRequestDTO request) {
    return ResponseEntity.ok(StubDataFactory.user());
  }

  @Override
  public ResponseEntity<Void> delete() {
    return ResponseEntity.noContent().build();
  }
}
