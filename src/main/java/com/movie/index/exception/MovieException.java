package com.movie.index.exception;

@SuppressWarnings("serial")
public class MovieException extends RuntimeException {

  public MovieException() {
    super();
  }

  public MovieException(String message) {
    super(message);
  }

  public MovieException(String message, Throwable e) {
    super(message, e);
  }

  public MovieException(Throwable e) {
    super(e);
  }
}
