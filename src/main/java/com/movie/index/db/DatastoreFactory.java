package com.movie.index.db;

import com.movie.index.Config;
import com.movie.index.exception.MovieDaoException;

public class DatastoreFactory {

  public enum Type {
    HSQLDB
  }

  private DatastoreFactory() {}

  public static Datastore create(DatastoreFactory.Type type, Config config) {
    if(Type.HSQLDB == type) {
      return new HsqlDb(config);
    }
    throw new MovieDaoException("unsupported datastore type " + type.name());
  }
}
