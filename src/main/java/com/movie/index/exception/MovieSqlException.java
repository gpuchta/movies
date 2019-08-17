package com.movie.index.exception;

import java.sql.SQLException;

@SuppressWarnings("serial")
public class MovieSqlException extends MovieException {

  public MovieSqlException() {
    super();
  }

  public MovieSqlException(String message) {
    super(message);
  }

  public MovieSqlException(String message, SQLException e) {
    super(message, e);
  }

  public MovieSqlException(SQLException e) {
    super(e);
  }
}
