package com.movie.index.util;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class StringUtils {

  public static String format(String template, Map<String, Object> args) {
    for (Entry<String, Object> entry : args.entrySet()) {
      String value = entry.getValue() == null ? "null" : entry.getValue().toString();
      template = template.replaceAll("\\{" + entry.getKey() + "\\}", value);
    }
    return template;
  }

  public static String join(Collection<String> values, String separator) {
    String tagListString = values.stream().
        map(Object::toString).
        collect(Collectors.joining(",")).toString();
    return tagListString;
  }

  public static String trimWhitespace(String value) {
    return value == null ? null : value.replaceAll(" {2,}", " ").trim();
  }
}
