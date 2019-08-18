package com.movie.index.tmdb.v3;

import com.movie.index.exception.MovieException;
import com.movie.index.tmdb.v3.model.TmdbCredits;
import com.movie.index.tmdb.v3.model.TmdbKeywords;
import com.movie.index.tmdb.v3.model.TmdbMovie;
import com.movie.index.tmdb.v3.model.TmdbMovieSearch;
import com.movie.index.util.Assertion;
import com.movie.index.util.GsonHelper;
import com.movie.index.util.HttpConstants;
import com.movie.index.util.StringUtils;
import com.movie.index.util.URLEncoderUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.movie.index.util.Arguments.arg;

public class TmdbManager implements TmdbApi {
  protected final Logger LOG = LoggerFactory.getLogger(getClass());
  private static final String TMDB_MOVIE_SEARCH_PATH = "search/movie";
  private static final String TMDB_MOVIE_DETAIL_PATH = "movie";
  private final TmdbRateLimiter _tmdbRateLimiter = new TmdbRateLimiter();
  private String _apiKey;

  public TmdbManager(String apiKey) {
    _apiKey = Assertion.assertNotNull(apiKey);
  }

  /**
   * Query tmdb for the given title and year.
   *
   * @param title title to search for
   * @param year year the movie was produced
   * @return TmdbMovies which provides access to
   *   the list of movies and the pagination index
   */
  public TmdbMovieSearch searchMoviesByTitleAndYear(String title, Integer year) {
    return GsonHelper.BASIC.fromJson(getAsJson(toSearchUrl(title, year)), TmdbMovieSearch.class);
  }

  /**
   * Query tmdb for the given movie id.
   *
   * @param id tmdb id of the movie
   * @return TmdbMovie which provides access to
   *   movie details
   */
  public TmdbMovie getMovieById(int id) {
    TmdbMovie movie = GsonHelper.BASIC.fromJson(getAsJson(toDetailUrl(id)), TmdbMovie.class);
    TmdbCredits credits = GsonHelper.BASIC.fromJson(getAsJson(toCreditsUrl(id)), TmdbCredits.class);
    TmdbKeywords keywords = GsonHelper.BASIC.fromJson(getAsJson(toKeywordsUrl(id)), TmdbKeywords.class);
    movie.setCredits(credits);
    movie.setKeywords(keywords);

    return movie;
  }

  protected String getBaseUrl() {
    return StringUtils.format("https://{host}/{version}",
        arg("host", TMDB_HOST).
        arg("version", TMDB_API_VERSION));
  }

  /**
   * Generate tmdb URL to search for the given
   * movie title and its release year.
   *
   * @param title title of the movie
   * @param year year the movie was released
   * @return tmdb URL
   */
  protected String toSearchUrl(String title, Integer year) {
    if(year == null) {
      return StringUtils.format("{baseurl}/{path}?api_key={apikey}&query={title}",
          arg("baseurl", getBaseUrl()).
          arg("path", TMDB_MOVIE_SEARCH_PATH).
          arg("apikey", _apiKey).
          arg("title", URLEncoderUtils.encodeUtf8(title)));
    }
    else {
      return StringUtils.format("{baseurl}/{path}?api_key={apikey}&query={title}&year={year}",
          arg("baseurl", getBaseUrl()).
          arg("path", TMDB_MOVIE_SEARCH_PATH).
          arg("apikey", _apiKey).
          arg("title", URLEncoderUtils.encodeUtf8(title)).
          arg("year", year));
    }
  }

  /**
   * Generate tmdb URL to query details for
   * the given tmdb movie id.
   *
   * @param id tmdb movie id
   * @return tmdb URL
   */
  protected String toDetailUrl(int id) {
    return StringUtils.format("{baseurl}/{path}/{id}?api_key={apikey}&append_to_response=images",
        arg("baseurl", getBaseUrl()).
        arg("path", TMDB_MOVIE_DETAIL_PATH).
        arg("id", id).
        arg("apikey", _apiKey));
  }

  /**
   * Generate tmdb URL to query credits for
   * the given tmdb movie id.
   *
   * @param id tmdb movie id
   * @return tmdb URL
   */
  protected String toCreditsUrl(int id) {
    return StringUtils.format("{baseurl}/{path}/{id}/credits?api_key={apikey}",
        arg("baseurl", getBaseUrl()).
        arg("path", TMDB_MOVIE_DETAIL_PATH).
        arg("id", id).
        arg("apikey", _apiKey));
  }

  /**
   * Generate tmdb URL to query credits for
   * the given tmdb movie id.
   *
   * @param id tmdb movie id
   * @return tmdb URL
   */
  protected String toKeywordsUrl(int id) {
    return StringUtils.format("{baseurl}/{path}/{id}/keywords?api_key={apikey}",
        arg("baseurl", getBaseUrl()).
        arg("path", TMDB_MOVIE_DETAIL_PATH).
        arg("id", id).
        arg("apikey", _apiKey));
  }

  /**
   * Perform a GET request for the given URL
   * and return its response body as a JSON
   * string.
   *
   * @param url url to use for GET request
   * @return JSON response string
   */
  protected String getAsJson(String url) {
    try {
      LOG.trace("GET {}", url);
      HttpGet httpGet = new HttpGet(url);
      httpGet.addHeader(new BasicHeader(HttpHeaders.ACCEPT, HttpConstants.APPLICATION_JSON));
      httpGet.addHeader(new BasicHeader(HttpHeaders.CONTENT_TYPE, HttpConstants.CONTENT_TYPE_JSON_UTF_8));

      CloseableHttpClient httpclient = HttpClients.createDefault();
      try(CloseableHttpResponse response = httpclient.execute(httpGet)) {

        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
          LOG.trace("Status {}", response.getStatusLine().getStatusCode());
          LOG.trace("Response {}", response.getStatusLine().getReasonPhrase());
          throw new MovieException(response.getStatusLine().getReasonPhrase());
        }

        HttpEntity entity = response.getEntity();
        String content = new BufferedReader(new InputStreamReader(entity.getContent(), StandardCharsets.UTF_8)).lines()
            .collect(Collectors.joining("\n"));

        String actualContentType = response.getHeaders(HttpHeaders.CONTENT_TYPE)[0].getValue();
        if (!actualContentType.contains(HttpConstants.APPLICATION_JSON)) {
          StringBuilder buffer = new StringBuilder();
          buffer.append(String.format("Expected content type %s, but found %s\n", HttpConstants.APPLICATION_JSON, actualContentType));
          buffer.append(String.format("Url: %s\n", url));
          buffer.append(String.format("Request Headers:\n"));
          Arrays.asList(httpGet.getAllHeaders()).stream().forEach(header -> {
            buffer.append(String.format("- %s: %s\n", header.getName(), header.getValue()));
          });
          buffer.append(String.format("Response Headers:\n"));
          Arrays.asList(response.getAllHeaders()).stream().forEach(header -> {
            buffer.append(String.format("- %s: %s\n", header.getName(), header.getValue()));
          });
          throw new MovieException(buffer.toString());
        }

        _tmdbRateLimiter.wait(httpGet.getAllHeaders());

        return content;
      }
    }
    catch(Throwable e) {
      throw new MovieException(String.format("Failed to fetch content for URL %s", url), e);
    }
  }
}
