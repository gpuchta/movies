package com.movie.index.util.gson;

import java.io.File;
import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

public class FileDeserializer implements JsonDeserializer<File> {

  @Override
  public File deserialize(JsonElement json, Type typeOfSrc, JsonDeserializationContext context) {
    return new File(json.getAsJsonPrimitive().getAsString());
  }
}
