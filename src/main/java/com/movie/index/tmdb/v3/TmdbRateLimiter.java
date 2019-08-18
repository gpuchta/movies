package com.movie.index.tmdb.v3;

import com.movie.index.exception.MovieException;
import org.apache.http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public final class TmdbRateLimiter {
  private static final String X_RATELIMIT_LIMIT     = "X-RateLimit-Limit";
  private static final String X_RATELIMIT_REMAINING = "X-RateLimit-Remaining";
  private static final String X_RATELIMIT_RESET     = "X-RateLimit-Reset";
  protected final Logger LOG = LoggerFactory.getLogger(getClass());

  /**
   *
   * Request Headers:
   * - Accept: application/json
   * - Content-Type: application/json; charset=utf-8
   *
   * Response Headers:
   * - Access-Control-Allow-Origin: *
   * - Cache-Control: public, max-age=28800
   * - Content-Type: application/json;charset=utf-8
   * - Date: Mon, 06 Mar 2017 04:57:03 GMT
   * - ETag: W/"74ca88a58daba8b692aab5865a8692d8"
   * - Server: openresty
   * - Status: 200 OK
   * - Vary: Accept-Encoding
   * - X-Memc: HIT
   * - X-Memc-Age: 705
   * - X-Memc-Expires: 28095
   * - X-Memc-Key: 30436b144be5f693c6ccb36398632404
   * - X-RateLimit-Limit: 40
   * - X-RateLimit-Remaining: 39
   * - X-RateLimit-Reset: 1488776233
   * - Connection: keep-alive
   *
   * @param headers
   */
  public void wait(Header[] headers) {

    Integer limit = null;
    Integer remaining = null;
    Long reset = null;

    for (Header header : headers) {
      String name = header.getName();
      if(X_RATELIMIT_LIMIT.equals(name)) {
        limit = Integer.parseInt(header.getValue());
      }
      else if(X_RATELIMIT_REMAINING.equals(name)) {
        remaining = Integer.parseInt(header.getValue());
      }
      else if(X_RATELIMIT_RESET.equals(name)) {
        reset = Long.parseLong(header.getValue());
      }
    }

    if(limit != null && remaining != null && reset != null) {
      if(remaining < 3) {
        long delta = new Date(reset).getTime() - new Date().getTime();
        try {
          LOG.info("waiting for tmdb rate limit ...");
          Thread.sleep(delta);
        }
        catch (InterruptedException e) {
          throw new MovieException(e);
        }
      }
    }
  }
}
