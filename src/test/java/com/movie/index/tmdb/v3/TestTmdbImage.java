package com.movie.index.tmdb.v3;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestTmdbImage {

  @Test
  public void testBackdropUrlBuilder() {
    Assert.assertEquals(TmdbMediaUrl.getBackdrop300("/image.jpg"),
        "https://image.tmdb.org/t/p/w300/image.jpg");

    Assert.assertEquals(TmdbMediaUrl.getBackdrop780("/image.jpg"),
        "https://image.tmdb.org/t/p/w780/image.jpg");

    Assert.assertEquals(TmdbMediaUrl.getBackdrop1280("/image.jpg"),
        "https://image.tmdb.org/t/p/w1280/image.jpg");

    Assert.assertEquals(TmdbMediaUrl.getBackdropOriginal("/image.jpg"),
        "https://image.tmdb.org/t/p/original/image.jpg");

    Assert.assertEquals(TmdbMediaUrl.getPoster92("/image.jpg"),
        "https://image.tmdb.org/t/p/w92/image.jpg");
  }

  @Test
  public void testPosterUrlBuilder() {
    Assert.assertEquals(TmdbMediaUrl.getPoster154("/image.jpg"),
        "https://image.tmdb.org/t/p/w154/image.jpg");

    Assert.assertEquals(TmdbMediaUrl.getPoster185("/image.jpg"),
        "https://image.tmdb.org/t/p/w185/image.jpg");

    Assert.assertEquals(TmdbMediaUrl.getPoster342("/image.jpg"),
        "https://image.tmdb.org/t/p/w342/image.jpg");

    Assert.assertEquals(TmdbMediaUrl.getPoster500("/image.jpg"),
        "https://image.tmdb.org/t/p/w500/image.jpg");

    Assert.assertEquals(TmdbMediaUrl.getPoster780("/image.jpg"),
        "https://image.tmdb.org/t/p/w780/image.jpg");

    Assert.assertEquals(TmdbMediaUrl.getPosterOriginal("/image.jpg"),
        "https://image.tmdb.org/t/p/original/image.jpg");
  }
}
