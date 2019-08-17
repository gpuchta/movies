package com.movie.index.exception;

@SuppressWarnings("serial")
public class SealedException extends MovieException {

  public SealedException() {
    super();
  }

  public SealedException(String message) {
    super(message);
  }

  public SealedException(String message, Throwable e) {
    super(message, e);
  }

  public SealedException(Throwable e) {
    super(e);
  }
}
