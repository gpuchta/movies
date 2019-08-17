package com.movie.index.db.dao;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.movie.index.Config;
import com.movie.index.app.model.Movie;
import com.movie.index.app.model.Settings;
import com.movie.index.db.Datastore;
import com.movie.index.db.DatastoreFactory;
import com.movie.index.tmdb.v3.Helper;

public class TestDaoFactory {

  @Test
  public void testMovie() throws Exception {
    File tempDir = Files.createTempDirectory("testFactory").toFile();

    Config config = new Config().setDataFolder(tempDir);
    Datastore store = DatastoreFactory.create(DatastoreFactory.Type.HSQLDB, config);
    DaoFactory daoFactory = DaoFactory.create(store.startup());

    MovieDao movieDao = daoFactory.getMovieDao();
    movieDao.persist(Helper.getTestMovie());

    List<Movie> allMovies = movieDao.getAllMovies();
    Assert.assertNotNull(allMovies);
    Assert.assertEquals(allMovies.size(), 1);
    Movie movie = allMovies.get(0);
    Assert.assertNotNull(movie);
    Assert.assertEquals(movie.getTitle(), "Ronin");
    Assert.assertTrue(movie.getOverview().startsWith("A briefcase with undisclosed contents"));
    Assert.assertEquals(movie.getPosterPath(), "/UoFUXemMYGKuCN01Y111JlPjjr.jpg");
    Assert.assertEquals(movie.getBackdropPath(), "/tzAtSkpEyAftOuNqcShaBOxWIkw.jpg");
    Assert.assertEquals(movie.getBudget(), 55000000);
    Assert.assertEquals(movie.getRevenue(), 41610884);

    Assert.assertEquals(movie.getGenres().length, 4);
    Assert.assertEquals(movie.getGenres()[0], "Action");
    Assert.assertEquals(movie.getGenres()[1], "Thriller");
    Assert.assertEquals(movie.getGenres()[2], "Crime");
    Assert.assertEquals(movie.getGenres()[3], "Adventure");

    Optional<Movie> optionalMovie = movieDao.getMovieById(movie.getTmdbId());
    Assert.assertTrue(optionalMovie.isPresent());

    store.shutdown();
  }

  @Test
  public void testSettings() throws Exception {
    File tempDir = Files.createTempDirectory("testFactory").toFile();

    Config config = new Config().setDataFolder(tempDir);
    Datastore store = DatastoreFactory.create(DatastoreFactory.Type.HSQLDB, config);
    DaoFactory daoFactory = DaoFactory.create(store.startup());

    SettingsDao settingsDao = daoFactory.getSettingsDao();
    Settings settings = settingsDao.load();

    Assert.assertNull(settings.getTmdbApiKey());
    Assert.assertFalse(settings.hasTmdbApiKey());

    settings.setTmdbApiKey("test");
    settingsDao.persist(settings);

    settings = settingsDao.load();

    Assert.assertTrue(settings.hasTmdbApiKey());
    Assert.assertEquals(settings.getTmdbApiKey(), "test");

    store.shutdown();
  }
}
