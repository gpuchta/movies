package com.movie.index.app.environment;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;

import com.movie.index.Config;
import com.movie.index.app.model.Environment;
import com.movie.index.exception.MovieIOException;
import com.movie.index.exception.MovieSettingsException;
import com.movie.index.util.ExtLogger;

public class EnvironmentManager {

  private static final ExtLogger LOG = ExtLogger.getLogger(EnvironmentManager.class);

  private static final String CONFIG_FILE = "config.json";

  public static Environment load(Config config) {
    return load(config.getConfigFolder());
  }

  public static Environment load(File configFolder) {
    LOG.info("Reading settings");

    if(!configFolder.exists()) {
      throw new MovieSettingsException("Config folder does not exist: " + configFolder.getAbsolutePath());
    }
    if(!configFolder.isDirectory()) {
      throw new MovieSettingsException("Config folder is not a directory: " + configFolder.getAbsolutePath());
    }

    File configFile = new File(configFolder, CONFIG_FILE);
    if(!configFile.exists()) {
      return new Environment().setDataFolder(configFolder);
    }

    try {
      String content = FileUtils.readFileToString(configFile, StandardCharsets.UTF_8);
      return Environment.fromJson(content).setDataFolder(configFolder);
    }
    catch (IOException e) {
      throw new MovieSettingsException(e);
    }
  }

  public static void store(Environment settings, File configFolder) {
    LOG.info("Store settings");

    File configFile = new File(configFolder, CONFIG_FILE);
    if(configFile.exists()) {
      FileUtils.deleteQuietly(configFile);
    }
    try {
      FileUtils.write(
          configFile,
          settings.toJson(),
          StandardCharsets.UTF_8);
    }
    catch (IOException e) {
      throw new MovieIOException(e);
    }
  }
}
