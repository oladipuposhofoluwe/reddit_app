package com.reddit.exception;

public class UnAuthorizeException extends RuntimeException {

  private final static String ERROR_CODE = "401";

  private static final long serialVersionUID = 1L;

  public UnAuthorizeException() {
    super("Not authorized");
  }

//  public UnAuthorizeException(String errorCode, String message) {
//    super(errorCode);
//  }

  public UnAuthorizeException(String message) {
    super(message);
  }

  public UnAuthorizeException(String message, Throwable e) {
    super(message);
  }
}
