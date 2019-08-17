package com.movie.index.db.dao;

import com.movie.index.db.Datastore;

public class DaoFactory {

  private Datastore _store;

  private DaoFactory(Datastore store) {
    _store = store;
  }

  public static DaoFactory create(Datastore store) {
    return new DaoFactory(store);
  }

  public MovieDao getMovieDao() {
    return new MovieDao(_store);
  }

  public SettingsDao getSettingsDao() {
    return new SettingsDao(_store);
  }
}
