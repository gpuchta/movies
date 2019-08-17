package com.movie.index.exception;

import java.io.IOException;

@SuppressWarnings("serial")
public class MovieIOException extends MovieException {

  public MovieIOException() {
    super();
  }

  public MovieIOException(String message) {
    super(message);
  }

  public MovieIOException(String message, IOException e) {
    super(message, e);
  }

  public MovieIOException(IOException e) {
    super(e);
  }
}
