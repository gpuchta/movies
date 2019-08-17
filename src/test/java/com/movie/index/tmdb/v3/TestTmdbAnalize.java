package com.movie.index.tmdb.v3;

import static com.movie.index.util.Arguments.arg;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

import com.movie.index.Config;
import com.movie.index.app.model.Movie;
import com.movie.index.db.Datastore;
import com.movie.index.db.DatastoreFactory;
import com.movie.index.db.dao.DaoFactory;
import com.movie.index.db.dao.MovieDao;
import com.movie.index.tmdb.v3.model.TmdbMovie;
import com.movie.index.tmdb.v3.model.TmdbMovieSearch;
import com.movie.index.util.StringUtils;

public class TestTmdbAnalize {

  // A1|Man of Steel (2013)
  private static final Pattern MAPPING_PATTERN = Pattern.compile("(?<locator>[^\\|]*)\\|(?<title>[^\\(]*) \\((?<year>[\\d]{4})\\) *");

  @Test(enabled = false)
  public void determineTmdbIds() throws Exception {
    TmdbManager tmdbQuery = new TmdbManager("<tmdb-api-key>");

    File mappingFile = new File("C:\\Users\\<username>\\home\\plex\\prod\\index-mapping.csv");

    try(LineNumberReader mappingReader = new LineNumberReader(new FileReader(mappingFile))) {
      String line = mappingReader.readLine();
      while(line != null) {
        if(line.trim().length() > 0) {
          Matcher matcher = MAPPING_PATTERN.matcher(line);
          if(!matcher.matches()) {
            System.out.println("[ERROR] " + line);
          }
          else {
            String locator = matcher.group("locator");
            String title = matcher.group("title");
            String yearString = matcher.group("year");

            int year = Integer.parseInt(yearString);

            TmdbMovieSearch movies = tmdbQuery.searchMoviesByTitleAndYear(title, year);
            if(movies.getMovies().size() == 1) {
              System.out.println(StringUtils.format("{locator}|{title}|{year}|{tmdb_id}",
                  arg("locator", locator)
                  .arg("title", title)
                  .arg("year", year)
                  .arg("tmdb_id", movies.getMovies().get(0).getId())));
            }
            else {
              System.out.println(StringUtils.format("[WARNING] {locator}|{title}|{year}|{tmdb_ids}",
                  arg("locator", locator)
                  .arg("title", title)
                  .arg("year", year)
                  .arg("tmdb_ids", movies.getMovies().stream().map(m -> m.getId()).collect(Collectors.toList()).toString())));
            }
          }
        }
        line = mappingReader.readLine();
      }
    }
  }

  @Test(enabled = false)
  public void loadTmdbMovies() throws Exception {
    Config config = new Config().setDataFolder(new File("C:\\Users\\<username>\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\movies"));
    Datastore datastore = DatastoreFactory.create(DatastoreFactory.Type.HSQLDB, config);
    DaoFactory daoFactory = DaoFactory.create(datastore);
    MovieDao movieDao = daoFactory.getMovieDao();
    TmdbManager tmdbQuery = new TmdbManager("<tmdb-api-key>");

    datastore.startup();

    File mappingFile = new File("C:\\Users\\<username>\\home\\plex\\dev\\java\\movies\\documentation\\mapping.txt");

    try(LineNumberReader mappingReader = new LineNumberReader(new FileReader(mappingFile))) {
      String line = mappingReader.readLine();

      if(line.contains("locator") && line.contains("title")) {
        line = mappingReader.readLine();
      }

      while(line != null) {
        System.out.println("- processing " + line);
        String[] fields = line.split("\\|");
        if(fields.length == 4) {

          String locator = fields[0];
          String title = fields[1];
          String yearString = fields[2];
          String tmdbIdString = fields[3];

          int tmdbId = Integer.parseInt(tmdbIdString);

          TmdbMovie tmdbMovie = tmdbQuery.getMovieById(tmdbId);
          Movie movie = Movie.fromTmdbMovie(tmdbMovie);

          movie.setLocator(locator);
          movieDao.persist(movie);
        }

        line = mappingReader.readLine();
      }
    }
    finally {
      datastore.shutdown();
    }
  }
}
