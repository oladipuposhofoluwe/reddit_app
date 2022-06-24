package com.reddit.exception;

public class AccountDisabledException extends RuntimeException {

  private final static String ERROR_CODE = "403";

  private static final long serialVersionUID = 1L;

  public AccountDisabledException() {
    super("Account disabled");
  }

  public AccountDisabledException( String message) {
    super(message);
  }

//  public AccountDisabledException(String message) {
//    super(ERROR_CODE,message);
//  }

//  public AccountDisabledException(String message, Throwable e) {
//    super(message,e);
//    this.setErrorCode(ERROR_CODE);
//  }
}
