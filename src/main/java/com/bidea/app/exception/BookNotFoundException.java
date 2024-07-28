package com.bidea.app.exception;

public class BookNotFoundException extends RuntimeException {

  public BookNotFoundException(String errorMessage) {
    super(errorMessage);
  }

  public BookNotFoundException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}
