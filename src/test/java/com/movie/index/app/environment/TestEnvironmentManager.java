package com.movie.index.app.environment;

import java.io.File;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.movie.index.app.model.Environment;

public class TestEnvironmentManager {

  @Test(enabled = false)
  public void testManager() throws Exception {
    File tempDir = Files.createTempDirectory("testFactory").toFile();
    try {
      Environment settings = EnvironmentManager.load(tempDir);
      Assert.assertNull(settings.getDataFolder());

      File dataFolder = new File(tempDir, "data");
      dataFolder.mkdirs();

      settings.setDataFolder(dataFolder);
      EnvironmentManager.store(settings, tempDir);

      settings = EnvironmentManager.load(tempDir);
      Assert.assertEquals(settings.getDataFolder(), dataFolder);
    }
    finally {
      FileUtils.deleteDirectory(tempDir);
    }
  }
}
