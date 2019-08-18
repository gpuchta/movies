package com.movie.index;

import com.movie.index.util.Assertion;
import com.movie.index.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Config {
  private static final String DB_FOLDER = "db";
  private static final String IMAGE_FOLDER = "image";

  private final Logger LOG = LoggerFactory.getLogger(getClass());
  private File _configFolder;
  private File _dataFolder;
  private File _imageFolder;
  private File _dbFolder;

  public Config() {
  }

  public Config setDataFolder(File dataFolder) {
    if(!dataFolder.exists() || !dataFolder.isDirectory()) {
      LOG.warn("data folder {} does not exist or is not a directory", dataFolder.getAbsolutePath());
      return this;
    }
    _dataFolder = FileUtils.makeDir(dataFolder);
    _dbFolder = FileUtils.makeDir(_dataFolder, DB_FOLDER);
    _imageFolder = FileUtils.makeDir(_dataFolder, IMAGE_FOLDER);
    return this;
  }

  public Config setConfigFolder(File configFolder) {
    _configFolder = configFolder;
    return this;
  }

  public File getConfigFolder() {
    return Assertion.assertNotNull(_configFolder);
  }

  public File getDataFolder() {
    return Assertion.assertNotNull(_dataFolder);
  }

  public File getImageFolder() {
    return Assertion.assertNotNull(_imageFolder);
  }

  public File getDbFolder() {
    return Assertion.assertNotNull(_dbFolder);
  }

  public void log() {
    LOG.info("Config folder   {}", _configFolder.getAbsolutePath());
    LOG.info("Data folder     {}", _dataFolder.getAbsolutePath());
    LOG.info("Image folder    {}", _imageFolder.getAbsolutePath());
    LOG.info("Database folder {}", _dbFolder.getAbsolutePath());
  }
}
