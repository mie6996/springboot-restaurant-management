package com.restaurant.exception;

import com.restaurant.dto.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Handle exceptions
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler({RepeatDataException.class})
  protected ResponseEntity<ErrorResponse> handleRepeatDataException(RuntimeException exception) {
    ErrorResponse error = new ErrorResponse(HttpStatus.OK.toString(), exception.getMessage());
    return new ResponseEntity<>(error, HttpStatus.OK);
  }

  @ExceptionHandler({IllegalArgumentException.class})
  protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(RuntimeException exception) {
    ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), exception.getMessage());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({NoContentException.class})
  protected ResponseEntity<ErrorResponse> handleNoContentException(RuntimeException exception) {
    ErrorResponse error = new ErrorResponse(HttpStatus.NO_CONTENT.toString(), exception.getMessage());
    return new ResponseEntity<>(error, HttpStatus.OK);
  }

  // Handle MethodArgumentNotValid
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String message = error.getDefaultMessage();
      errors.put(fieldName, message);
    });
    ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), "The argument is not valid!", errors);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  // Handle MethodArgumentTypeMismatchException
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
    String name = ex.getName();
    String type = Objects.requireNonNull(ex.getRequiredType()).getSimpleName();
    Object value = ex.getValue();
    String message = String.format("The '%s' should be a valid '%s' and '%s' isn't!", name, type, value);
    ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), message);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

}
