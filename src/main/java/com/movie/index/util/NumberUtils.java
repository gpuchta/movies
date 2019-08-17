package com.movie.index.util;

public class NumberUtils {

  public static Integer toInteger(String value) {
    if(value == null) {
      return null;
    }

    try {
      return new Integer(Integer.parseInt(value));
    }
    catch(NumberFormatException e) {
      return null;
    }
  }

  public static Integer toInteger(String value, int defaultValue) {
    Integer integer = toInteger(value);
    return integer != null ? integer : defaultValue;
  }

  public static Integer toInteger(Integer integer, int defaultValue) {
    return integer != null ? integer : defaultValue;
  }

  public static int toInt(Integer integer, int defaultValue) {
    return integer != null ? integer : defaultValue;
  }
}
