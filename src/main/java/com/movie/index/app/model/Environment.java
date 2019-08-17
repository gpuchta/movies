package com.movie.index.app.model;

import java.io.File;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.movie.index.Logging;
import com.movie.index.util.ExtLogger;
import com.movie.index.util.gson.FileDeserializer;
import com.movie.index.util.gson.FileSerializer;

public class Environment implements Logging {

  private static final ExtLogger LOG = ExtLogger.getLogger(Environment.class);

  @SerializedName("data_folder")
  protected File _dataFolder;

  public File getDataFolder() {
    return _dataFolder;
  }

  public Environment setDataFolder(String dataFolder) {
    setDataFolder(new File(dataFolder));
    return this;
  }

  public Environment setDataFolder(File dataFolder) {
    _dataFolder = dataFolder;
    return this;
  }

  public boolean hasDataFolder() {
    return _dataFolder != null && _dataFolder.exists() && _dataFolder.isDirectory();
  }

  public boolean isSetupComplete() {
    return hasDataFolder();
  }

  public String toJson() {
    // serialize nulls so we know what to show
    // on the settings UI
    return new GsonBuilder()
        .serializeNulls()
        .registerTypeAdapter(File.class, new FileSerializer())
        .registerTypeAdapter(File.class, new FileDeserializer())
        .setPrettyPrinting()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
        .create()
        .toJson(this);
  }

  public static Environment fromJson(String jsonString) {
    return new GsonBuilder()
        .registerTypeAdapter(File.class, new FileSerializer())
        .registerTypeAdapter(File.class, new FileDeserializer())
        .create()
        .fromJson(jsonString, Environment.class);
  }

  public void log() {
    LOG.info(CONFIG_LOG_FORMAT, "Data folder", _dataFolder.getAbsolutePath());
  }
}