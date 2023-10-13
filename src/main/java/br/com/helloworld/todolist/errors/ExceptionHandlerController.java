package br.com.helloworld.todolist.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMostSpecificCause().getMessage());
  }

  @ExceptionHandler(UnableToParseUUIDError.class)
  public ResponseEntity<String> handleUnableToParseUUID(UnableToParseUUIDError e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }
}
