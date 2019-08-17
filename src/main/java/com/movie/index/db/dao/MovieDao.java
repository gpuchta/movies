package com.movie.index.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.movie.index.app.callback.Callback;
import com.movie.index.app.model.Movie;
import com.movie.index.db.Datastore;
import com.movie.index.exception.MovieDaoException;
import com.movie.index.util.ExtLogger;

public class MovieDao {

  private static final ExtLogger LOG = ExtLogger.getLogger(MovieDao.class);

  private Datastore _store;

  MovieDao(Datastore store) {
    _store = store;
  }

  public static void initialize(Connection connection) throws SQLException {
    Statement statement = connection.createStatement();
    statement.execute(""
        + "CREATE TABLE IF NOT EXISTS movies ("
        + "  tmdb_id         INTEGER PRIMARY KEY,"
        + "  value           LONGVARCHAR"
        + ")");
  }

  public List<Movie> getAllMovies() {
    LOG.info("Get all movies");
    List<Movie> movies = new ArrayList<>();
    try {
      Connection connection = _store.getConnection();

      try {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM movies");
        while(resultSet.next()) {
          Movie movie = new MovieBuilder().build(resultSet.getString("value"));
          movies.add(movie);
        }
        return movies;
      }
      catch(Throwable e) {
        throw new MovieDaoException("failed to get a connection", e);
      }
      finally {
        connection.close();
      }
    }
    catch(Throwable e) {
      throw new MovieDaoException("failed to get a connection", e);
    }
  }

  public Optional<Movie> getMovieById(int tmdbId) {
    LOG.info("Get movie %d", tmdbId);
    try {
      Connection connection = _store.getConnection();

      try {
        PreparedStatement pStatement = connection.prepareStatement("SELECT * FROM movies WHERE tmdb_id = ?");
        pStatement.setLong(1, tmdbId);
        ResultSet resultSet = pStatement.executeQuery();
        if(resultSet.next()) {
          return Optional.of(new MovieBuilder().build(resultSet.getString("value")));
        }
        return Optional.empty();
      }
      catch(Throwable e) {
        throw new MovieDaoException("failed to get a connection", e);
      }
      finally {
        connection.close();
      }
    }
    catch(Throwable e) {
      throw new MovieDaoException("failed to get a connection", e);
    }
  }

  public boolean persist(Movie movie) {
    LOG.info("Persist movie %s (%d)", movie.getTitle(), movie.getTmdbId());
    try {
      Connection connection = _store.getConnection();

      try {
        PreparedStatement pStatement = connection.prepareStatement("UPDATE movies SET value = ? WHERE tmdb_id = ?");
        String jsonString = movie.toJson();
        pStatement.setString(1, jsonString);
        pStatement.setLong(2, movie.getTmdbId());
        if(pStatement.executeUpdate() == 0) {
          pStatement.close();

          pStatement = connection.prepareStatement("INSERT INTO movies VALUES (?, ?)");
          pStatement.setLong(1, movie.getTmdbId());
          pStatement.setString(2, jsonString);
          pStatement.executeUpdate();
        }
        pStatement.close();
        connection.commit();
        return true;
      }
      catch(Throwable e) {
        throw new MovieDaoException("failed to get a connection", e);
      }
      finally {
        connection.close();
      }
    }
    catch(Throwable e) {
      throw new MovieDaoException("failed to get a connection", e);
    }
  }

  public boolean delete(Movie movie) {
    return delete(movie.getTmdbId());
  }

  public boolean delete(int tmdbId) {
    LOG.info("Delete movie %d", tmdbId);
    try {
      Connection connection = _store.getConnection();

      try {
        PreparedStatement pStatement = connection.prepareStatement("DELETE FROM movies WHERE tmdb_id = ?");
        pStatement.setLong(1, tmdbId);
        if(pStatement.executeUpdate() == 0) {
          LOG.info("Could not delete tmdb ID %d", tmdbId);
        }
        pStatement.close();
        connection.commit();
        return true;
      }
      catch(Throwable e) {
        throw new MovieDaoException("failed to get a connection", e);
      }
      finally {
        connection.close();
      }
    }
    catch(Throwable e) {
      throw new MovieDaoException("failed to get a connection", e);
    }
  }

  public boolean restore(List<Movie> movies) {
    return restore(movies, Callback.MOVIE_NOOP_CALLBACK);
  }

  public boolean restore(List<Movie> movies, Callback<Movie> movieCallback) {
    LOG.info("Restore %d movies", movies.size());
    try {
      Connection connection = _store.getConnection();

      try {
        PreparedStatement pStatement = connection.prepareStatement("DELETE FROM movies");
        pStatement.executeUpdate();
        pStatement.close();
        connection.commit();
      }
      catch(Throwable e) {
        throw new MovieDaoException("failed to get a connection", e);
      }
      finally {
        connection.close();
      }

      movies.stream().forEach(movie -> {
        movieCallback.handle(movie);
        persist(movie);
      });

      return true;
    }
    catch(Throwable e) {
      throw new MovieDaoException("failed to get a connection", e);
    }
  }
}
