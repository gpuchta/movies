package com.movie.index.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.movie.index.app.model.Settings;
import com.movie.index.db.Datastore;
import com.movie.index.exception.MovieDaoException;
import com.movie.index.util.ExtLogger;

public class SettingsDao {

  private static final ExtLogger LOG = ExtLogger.getLogger(SettingsDao.class);

  private Datastore _store;

  SettingsDao(Datastore store) {
    _store = store;
  }

  public static void initialize(Connection connection) throws SQLException {
    Statement statement = connection.createStatement();
    statement.execute(""
        + "CREATE TABLE IF NOT EXISTS SETTINGS ("
        + "  id              INTEGER PRIMARY KEY,"
        + "  value           LONGVARCHAR"
        + ")");
  }

  public Settings load() {
    try {
      Connection connection = _store.getConnection();

      try {
        PreparedStatement statement = connection.prepareStatement("SELECT value FROM settings WHERE id = 1");
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()) {
          return SettingsBuilder.build(resultSet.getString("value"));
        }
        else {
          return new Settings();
        }
      }
      catch(Throwable e) {
        throw new MovieDaoException("failed to get a connection");
      }
      finally {
        connection.close();
      }
    }
    catch(Throwable e) {
      throw new MovieDaoException("failed to get a connection");
    }
  }

  public boolean persist(Settings settings) {
    try {
      Connection connection = _store.getConnection();
  
      try {
        LOG.info("Update settings");
        PreparedStatement pStatement = connection.prepareStatement("UPDATE settings SET value = ? WHERE id = 1");
        String jsonString = settings.toJson();
        pStatement.setString(1, jsonString);
        if(pStatement.executeUpdate() == 0) {
          pStatement.close();
  
          LOG.info("Settings record not available yet. Insert settings");
          pStatement = connection.prepareStatement("INSERT INTO SETTINGS VALUES (1, ?)");
          pStatement.setString(1, jsonString);
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
}
