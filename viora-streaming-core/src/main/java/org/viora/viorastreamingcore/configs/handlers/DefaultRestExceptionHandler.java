package org.viora.viorastreamingcore.configs.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.viora.viorastreamingcore.exceptions.EntityConflictException;
import org.viora.viorastreamingcore.exceptions.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class DefaultRestExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException ex) {
    log.debug("Entity not found: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ApiError(LocalDateTime.now(), 404, ex.getMessage()));
  }

  @ExceptionHandler(EntityConflictException.class)
  public ResponseEntity<ApiError> handleEntityConflictException(EntityNotFoundException ex) {
    log.debug("Conflict: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(new ApiError(LocalDateTime.now(), 409, ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
      final MethodArgumentNotValidException ex) {
    log.debug("Bad request: {}", ex.getMessage());
    return ResponseEntity.badRequest()
        .body(getFieldErrorsMapFromBindingResult(ex.getBindingResult()));
  }

  private Map<String, String> getFieldErrorsMapFromBindingResult(
      final BindingResult bindingResult) {
    return bindingResult.getFieldErrors().stream()
        .collect(
            Collectors.toMap(
                FieldError::getField,
                FieldError::getDefaultMessage,
                (oldValue, newValue) -> oldValue,
                HashMap::new));
  }

}
