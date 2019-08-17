package com.movie.index.util;

import java.util.HashMap;

public class Arguments {

  @SuppressWarnings("serial")
  public static class Args extends HashMap<String, Object> {

    public Args arg(String key, Object value) {
      put(key, value);
      return this;
    }
  }

  public static Args arg(String key, Object value) {
    Args args = new Args();
    args.put(key, value);
    return args;
  }
}
