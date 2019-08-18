package com.movie.index.db;

import java.sql.Connection;

public interface Datastore {

  String DB_NAME = "MovieIndex";

  Datastore startup();

  Connection getConnection();

  void closeConnection(Connection connection);

  void shutdown();
}
