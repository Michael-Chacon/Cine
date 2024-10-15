package com.cine.utils.handlerException;

import com.cine.utils.Error;
import com.cine.utils.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GenericExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Error> resourceNotFound(Exception e) {
    Error error =
        Error.builder()
            .message(e.getMessage())
            .error("Recurso no encontrado")
            .status(HttpStatus.NOT_FOUND.value())
            .date(new Date())
            .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }
}
