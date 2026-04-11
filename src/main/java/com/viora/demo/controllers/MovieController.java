package com.viora.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Аутентифікація користувачів")
public class AuthController {

  @Operation(
      summary = "Реєстрація користувача",
      description = "Доступно для неавторизованих користувачів. Створює новий акаунт."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "201",
          description = "Користувач успішно створений",
          content = @Content(
              mediaType = "application/json",
              examples = @ExampleObject(value = """
                {
                  "id": "a1b2c3d4",
                  "email": "user@example.com",
                  "fullName": "John Doe",
                  "role": "USER",
                  "createdAt": "2026-04-09T12:00:00Z"
                }
                """)
          )
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Помилка валідації",
          content = @Content(
              mediaType = "application/json",
              examples = @ExampleObject(value = """
                {
                  "timestamp": "2026-04-09T12:10:00Z",
                  "errorCode": "VALIDATION_ERROR",
                  "message": "Invalid request body",
                  "details": ["email must contain @"]
                }
                """)
          )
      )
  })
  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody UserRegisterRequestDTO request) {
    return ResponseEntity.status(201).build();
  }
}
