package com.movie.index.app.model;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestLocator {

  @Test
  public void testFactory() throws Exception {
    Locator locator;

    locator = Locator.create("A1");
    Assert.assertNotNull(locator);
    Assert.assertEquals(locator.toString(), "A1");

    locator = Locator.create("C130");
    Assert.assertNotNull(locator);
    Assert.assertEquals(locator.toString(), "C130");

    locator = Locator.create("A1:streaming");
    Assert.assertNotNull(locator);
    Assert.assertEquals(locator.toString(), "A1:streaming");

    locator = Locator.create("Amazon");
    Assert.assertNotNull(locator);
    Assert.assertEquals(locator.toString(), "Amazon");
  }
}
