package com.movie.index.util;

import com.movie.index.exception.MovieException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLEncoderUtils {

  private static final String UTF_8 = "UTF-8";

  public static String encodeUtf8(String value) {
    try {
      return URLEncoder.encode(value, UTF_8);
    }
    catch (UnsupportedEncodingException e) {
      throw new MovieException(e);
    }
  }
}
