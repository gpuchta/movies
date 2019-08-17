package com.movie.index;

import java.io.File;

import com.movie.index.util.Assertion;
import com.movie.index.util.ExtLogger;
import com.movie.index.util.FileUtils;

public class Config implements Logging {

  private static final ExtLogger LOG = ExtLogger.getLogger(Config.class);

  private static final String DB_FOLDER = "db";
  private static final String IMAGE_FOLDER = "image";

  private File _configFolder;
  private File _dataFolder;
  private File _imageFolder;
  private File _dbFolder;

  public Config() {
  }

  public Config setDataFolder(File dataFolder) {
    if(!dataFolder.exists() || !dataFolder.isDirectory()) {
      LOG.severe("data folder %s does not exist or is not a directory", dataFolder.getAbsolutePath());
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
    LOG.info(CONFIG_LOG_FORMAT, "Config folder", _configFolder.getAbsolutePath());
    LOG.info(CONFIG_LOG_FORMAT, "Data folder", _dataFolder.getAbsolutePath());
    LOG.info(CONFIG_LOG_FORMAT, "Image folder", _imageFolder.getAbsolutePath());
    LOG.info(CONFIG_LOG_FORMAT, "Database folder", _dbFolder.getAbsolutePath());
  }
}
