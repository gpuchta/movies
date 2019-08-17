package com.movie.index.exception;

@SuppressWarnings("serial")
public class MovieDaoException extends MovieException {

  public MovieDaoException() {
    super();
  }

  public MovieDaoException(String message) {
    super(message);
  }

  public MovieDaoException(String message, Throwable e) {
    super(message, e);
  }

  public MovieDaoException(Throwable e) {
    super(e);
  }
}
