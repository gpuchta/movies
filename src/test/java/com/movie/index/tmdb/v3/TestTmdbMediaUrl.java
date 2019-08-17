package com.movie.index.tmdb.v3;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestTmdbMediaUrl {

  @Test
  public void testBaseUrlMap() {
    Map<String, String> mediaBaseUrls = TmdbMediaUrl.TMDB_MEDIA_BASE_URLS;
    Assert.assertNotNull(mediaBaseUrls);
    Assert.assertTrue(mediaBaseUrls.size() > 0);

    Assert.assertNotNull(mediaBaseUrls.get("poster92"), TmdbMediaUrl.getPoster92(""));
    Assert.assertNotNull(mediaBaseUrls.get("poster154"), TmdbMediaUrl.getPoster154(""));
    Assert.assertNotNull(mediaBaseUrls.get("poster185"), TmdbMediaUrl.getPoster185(""));
    Assert.assertNotNull(mediaBaseUrls.get("poster342"), TmdbMediaUrl.getPoster342(""));
    Assert.assertNotNull(mediaBaseUrls.get("poster780"), TmdbMediaUrl.getPoster780(""));
    Assert.assertNotNull(mediaBaseUrls.get("poster500"), TmdbMediaUrl.getPoster500(""));
    Assert.assertNotNull(mediaBaseUrls.get("posterOriginal"), TmdbMediaUrl.getPosterOriginal(""));

    Assert.assertNotNull(mediaBaseUrls.get("backdrop300"), TmdbMediaUrl.getBackdrop300(""));
    Assert.assertNotNull(mediaBaseUrls.get("backdrop780"), TmdbMediaUrl.getBackdrop780(""));
    Assert.assertNotNull(mediaBaseUrls.get("backdrop1280"), TmdbMediaUrl.getBackdrop1280(""));
    Assert.assertNotNull(mediaBaseUrls.get("backdropOriginal"), TmdbMediaUrl.getBackdropOriginal(""));

    Assert.assertNotNull(mediaBaseUrls.get("poster92"), "https://image.tmdb.org/t/p/w92");
    Assert.assertNotNull(mediaBaseUrls.get("poster154"), "https://image.tmdb.org/t/p/w154");
    Assert.assertNotNull(mediaBaseUrls.get("poster185"), "https://image.tmdb.org/t/p/w185");
    Assert.assertNotNull(mediaBaseUrls.get("poster342"), "https://image.tmdb.org/t/p/w342");
    Assert.assertNotNull(mediaBaseUrls.get("poster780"), "https://image.tmdb.org/t/p/w780");
    Assert.assertNotNull(mediaBaseUrls.get("poster500"), "https://image.tmdb.org/t/p/w500");
    Assert.assertNotNull(mediaBaseUrls.get("posterOriginal"), "https://image.tmdb.org/t/p/original");

    Assert.assertNotNull(mediaBaseUrls.get("backdrop300"), "https://image.tmdb.org/t/p/300");
    Assert.assertNotNull(mediaBaseUrls.get("backdrop780"), "https://image.tmdb.org/t/p/780");
    Assert.assertNotNull(mediaBaseUrls.get("backdrop1280"), "https://image.tmdb.org/t/p/1280");
    Assert.assertNotNull(mediaBaseUrls.get("backdropOriginal"), "https://image.tmdb.org/t/p/original");
  }
}
