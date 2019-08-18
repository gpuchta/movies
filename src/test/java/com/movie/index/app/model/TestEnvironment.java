package com.movie.index.app.model;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestEnvironment {

  @Test
  public void testSerialization() {
    String json = new Environment().setDataFolder(".").toJson();
    Environment environment = Environment.fromJson(json);

    Assert.assertNotNull(environment);
    Assert.assertNotNull(environment.getDataFolder());
    Assert.assertEquals(".", environment.getDataFolder().getName());
  }
}
