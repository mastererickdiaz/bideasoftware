package com.bidea.app.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"cause", "localizedMessage", "suppressed", "stackTrace"})
public class ApiException extends RuntimeException {

  public ApiException(String errorMessage) {
    super(errorMessage);
  }

}
