package com.movie.index.db.dao;

import java.sql.Array;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.movie.index.exception.MovieSqlException;

public class DaoUtil {

  public static String[] toStringArray(Array array) {
    try {
      Object[] values = (Object[])array.getArray();
      List<String> stringList = Arrays.asList(values)
        .stream()
        .map(e -> e.toString())
        .collect(Collectors.toList());

      return stringList.toArray(new String[stringList.size()]);
    }
    catch(SQLException e) {
      throw new MovieSqlException(e);
    }
  }
}
