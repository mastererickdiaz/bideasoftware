package com.bidea.app.advice;

import com.bidea.app.exception.ApiException;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.openapitools.model.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    ErrorResponse error = new ErrorResponse();
    error.setStatusCode(BigDecimal.valueOf(HttpStatus.BAD_REQUEST.value()));
    error.setMessage(errors.toString());
    error.setError(HttpStatus.BAD_REQUEST.name());

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = ApiException.class)
  public ResponseEntity<ErrorResponse> handleException(ApiException ex, WebRequest request) {
    ErrorResponse error = new ErrorResponse();
    error.setStatusCode(BigDecimal.valueOf(HttpStatus.CONFLICT.value()));
    error.setMessage("Invalid Discount");
    error.setError(HttpStatus.CONFLICT.name());
    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Object> handleEntityNotFoundException() {
    ErrorResponse error = new ErrorResponse();
    error.setStatusCode(BigDecimal.valueOf(HttpStatus.NOT_FOUND.value()));
    error.setMessage("Book not find");
    error.setError(HttpStatus.NOT_FOUND.name());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

}
