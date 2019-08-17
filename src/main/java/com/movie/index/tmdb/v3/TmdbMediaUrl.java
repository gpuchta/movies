package com.movie.index.tmdb.v3;

import static com.movie.index.util.Arguments.arg;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.movie.index.exception.MovieException;
import com.movie.index.util.StringUtils;

public class TmdbMediaUrl {
  private static final String TMDB_BASE_URL = "https://image.tmdb.org/t/p";

  public static Map<String, String> TMDB_MEDIA_BASE_URLS = Collections.unmodifiableMap(getBaseUrls());

  protected static String createImageUrl(String image, String width) {
    return StringUtils.format("{baseurl}/{width}/{image}",
        arg("baseurl", TMDB_BASE_URL).
        arg("width", width).
        arg("image", org.apache.commons.lang3.StringUtils.stripStart(image, "/")));
  }

  public static String getBackdrop300(String image) {
    return createImageUrl(image, "w300");
  }

  public static String getBackdrop780(String image) {
    return createImageUrl(image, "w780");
  }

  public static String getBackdrop1280(String image) {
    return createImageUrl(image, "w1280");
  }

  public static String getBackdropOriginal(String image) {
    return createImageUrl(image, "original");
  }

  public static String getPoster92(String image) {
    return createImageUrl(image, "w92");
  }

  public static String getPoster154(String image) {
    return createImageUrl(image, "w154");
  }

  public static String getPoster185(String image) {
    return createImageUrl(image, "w185");
  }

  public static String getPoster342(String image) {
    return createImageUrl(image, "w342");
  }

  public static String getPoster500(String image) {
    return createImageUrl(image, "w500");
  }

  public static String getPoster780(String image) {
    return createImageUrl(image, "w780");
  }

  public static String getPosterOriginal(String image) {
    return createImageUrl(image, "original");
  }

  private static Map<String, String> getBaseUrls() {
    Map<String, String> result = new HashMap<String, String>();
    try {
      for (Method method : TmdbMediaUrl.class.getMethods()) {
        String methodName = method.getName();
        if(methodName.startsWith("getPoster") || methodName.startsWith("getBackdrop")) {
          methodName = methodName.replace("get", "");
          methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
          String baseUrl = String.valueOf(method.invoke(null, ""));
          result.put(methodName, org.apache.commons.lang3.StringUtils.removeEnd(baseUrl, "/"));
        }
      }
    }
    catch (Exception e) {
      throw new MovieException(e);
    }

    return result;
  }
}
