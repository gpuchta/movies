package com.movie.index.app.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class Locator {

  private static final String TYPE = "type";
  private static final String PROVIDER = "provider";
  private static final String BINDER = "binder";
  private static final String PAGE = "page";
  private static final String STREAMING = "streaming";

  public enum Type {
    BINDER,
    PROVIDER
  }

  @SerializedName(TYPE)
  private Type _type;

  @SerializedName(BINDER)
  private String _binder;

  @SerializedName(PAGE)
  private String _page;

  @SerializedName(PROVIDER)
  private String _provider;

  @SerializedName(STREAMING)
  private boolean _streaming;

  private static Pattern BINDER_PATTERN = Pattern.compile("(?<" + BINDER + ">[A-Z])(?<" + PAGE + ">[0-9]+)(:(?<" + STREAMING + ">" + STREAMING + "))?");

  public static Locator create(JsonObject location) {
    Locator locator = new Locator();
    locator._type = Type.valueOf(location.getAsJsonPrimitive(TYPE).getAsString());
    locator._binder = nullableString(location.get(BINDER));
    locator._page = nullableString(location.get(PAGE));
    locator._provider = nullableString(location.get(PROVIDER));
    locator._streaming = location.getAsJsonPrimitive(STREAMING).getAsBoolean();
    return locator;
  }

  private static String nullableString(JsonElement jsonElement) {
    if(jsonElement == null || jsonElement instanceof JsonNull) {
      return null;
    }
    return jsonElement.getAsString();
  }

  public static Locator create(String location) {
    if(location == null) {
      return null;
    }

    Locator locator = new Locator();
    Matcher matcher = BINDER_PATTERN.matcher(location);
    if(matcher.matches()) {
      locator._type = Type.BINDER;
      locator._binder = matcher.group(BINDER);
      locator._page = matcher.group(PAGE);
      locator._streaming = STREAMING.equals(matcher.group(STREAMING));
    }
    else {
      locator._type = Type.PROVIDER;
      locator._provider = location;
      locator._streaming = true;
    }
    return locator;
  }

  @Override
  public String toString() {
    if(_type == Type.PROVIDER) {
      return _provider;
    }
    else {
      String result = _binder + _page;
      if(_streaming) {
        result += ":streaming";
      }
      return result;
    }
  }
}
