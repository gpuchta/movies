package com.movie.index.util.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.movie.index.app.model.Locator;

public class LocatorDeserializer implements JsonDeserializer<Locator> {

  @Override
  public Locator deserialize(JsonElement json, Type typeOfSrc, JsonDeserializationContext context) {
    if(json.isJsonObject()) {
      return Locator.create(json.getAsJsonObject());
    }
    else if(json.isJsonPrimitive()) {
      return Locator.create(json.getAsString());
    }
    else {
      return null;
    }
  }
}
