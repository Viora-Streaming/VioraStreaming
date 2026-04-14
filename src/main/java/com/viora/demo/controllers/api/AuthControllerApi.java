package com.viora.demo.controllers.api;

import com.viora.demo.dto.ErrorResponseDTO;
import com.viora.demo.dto.LoginRequestDTO;
import com.viora.demo.dto.LoginResponseDTO;
import com.viora.demo.dto.UserRegisterRequestDTO;
import com.viora.demo.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Authentication and registration")
public interface AuthControllerApi {

  @Operation(summary = "Register user")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Created",
          content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Validation error",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
      @ApiResponse(responseCode = "409", description = "User already exists",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
  })
  @PostMapping("/register")
  ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterRequestDTO request);

  @Operation(summary = "Login")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
      @ApiResponse(responseCode = "401", description = "Invalid credentials",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
  })
  @PostMapping("/login")
  ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request);
}
