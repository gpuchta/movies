package com.movie.index.util.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.movie.index.app.model.Locator;

public class LocatorSerializer implements JsonSerializer<Locator> {

  @Override
  public JsonElement serialize(Locator src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src.toString());
  }
}
