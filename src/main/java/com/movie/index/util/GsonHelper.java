package com.movie.index.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.movie.index.app.model.Locator;
import com.movie.index.util.gson.FileDeserializer;
import com.movie.index.util.gson.FileSerializer;
import com.movie.index.util.gson.LocatorDeserializer;
import com.movie.index.util.gson.LocatorSerializer;

import java.io.File;

public class GsonHelper {

  public static final Gson PRETTY = new GsonBuilder()
      .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
      .serializeNulls()
      .registerTypeAdapter(File.class, new FileSerializer())
      .registerTypeAdapter(File.class, new FileDeserializer())
      .registerTypeAdapter(Locator.class, new LocatorSerializer())
      .registerTypeAdapter(Locator.class, new LocatorDeserializer())
      .setPrettyPrinting()
      .create();

  public static final Gson BASIC = new GsonBuilder()
      .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
      .serializeNulls()
      .registerTypeAdapter(File.class, new FileSerializer())
      .registerTypeAdapter(File.class, new FileDeserializer())
      .registerTypeAdapter(Locator.class, new LocatorSerializer())
      .registerTypeAdapter(Locator.class, new LocatorDeserializer())
      .create();

  public static final Gson UI = new GsonBuilder()
      .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
      .serializeNulls()
      .registerTypeAdapter(File.class, new FileSerializer())
      .registerTypeAdapter(File.class, new FileDeserializer())
      .registerTypeAdapter(Locator.class, new LocatorDeserializer())
      .create();

  public static final Gson UI_PRETTY = new GsonBuilder()
      .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
      .serializeNulls()
      .registerTypeAdapter(File.class, new FileSerializer())
      .registerTypeAdapter(File.class, new FileDeserializer())
      .registerTypeAdapter(Locator.class, new LocatorDeserializer())
      .setPrettyPrinting()
      .create();


  private GsonHelper() { }
}
