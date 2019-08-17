package com.movie.index.exception;

import java.io.IOException;

@SuppressWarnings("serial")
public class MovieSettingsException extends MovieException {

  public MovieSettingsException() {
    super();
  }

  public MovieSettingsException(String message) {
    super(message);
  }

  public MovieSettingsException(String message, IOException e) {
    super(message, e);
  }

  public MovieSettingsException(IOException e) {
    super(e);
  }
}
