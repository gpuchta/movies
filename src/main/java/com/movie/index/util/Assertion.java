package com.movie.index.util;

import com.movie.index.exception.SealedException;

public class Assertion {

  public static <T> T assertNotNull(T value) {
    if(value == null) {
      throw new NullPointerException();
    }
    return value;
  }

  public static void assertNotSealed(Sealable sealable) {
    if(sealable.isSealed()) {
      throw new SealedException("object is already sealed");
    }
  }

  public static void assertSealed(Sealable sealable) {
    if(!sealable.isSealed()) {
      throw new SealedException("object is not sealed yet");
    }
  }
}
