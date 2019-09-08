package com.movie.index.db;

import com.movie.index.Config;
import com.movie.index.db.dao.MovieDao;
import com.movie.index.db.dao.SettingsDao;
import com.movie.index.exception.MovieSqlException;
import org.hsqldb.Database;
import org.hsqldb.Server;
import org.hsqldb.jdbc.JDBCDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

class HsqlDb implements Datastore {
  private static final String DB_PROTOCOL_FILE = "jdbc:hsqldb:file";
  private static final String USERNAME = "SA";
  private static final String PASSWORD = "";

  private final Logger LOG = LoggerFactory.getLogger(getClass());
  private Config _config;
  private String _url;
  private Server _server;

  public HsqlDb(Config config) {
    _config = config;
    File dbFolder = config.getDbFolder();
    if(!dbFolder.exists()) {
      LOG.info("creating database folder {}", dbFolder.getAbsolutePath());
      dbFolder.mkdirs();
    }
  }

  @Override
  public Datastore startup() {
    _url = String.format("%s:%s;sql.enforce_strict_size=true",
        DB_PROTOCOL_FILE,
        Paths.get(_config.getDbFolder().getAbsolutePath(), DB_NAME));

    String dbPath = String.format("file:%s;sql.enforce_strict_size=true",
        Paths.get(_config.getDbFolder().getAbsolutePath(), DB_NAME));

    _server = new Server();
    _server.setDatabaseName(0, DB_NAME);
    _server.setDatabasePath(0, dbPath);
    _server.setLogWriter(null);
    _server.setErrWriter(null);
    _server.start();

    if(!isReady()) {
      initialize();
    }

    return this;
  }

  private boolean isReady() {
    try {
      Connection connection = getConnection();
      DatabaseMetaData meta = connection.getMetaData();
      ResultSet result = meta.getTables(null, null, null, new String[]{"TABLE"});
      boolean hasTable = result.next();
      closeConnection(connection);
      return hasTable;
    }
    catch (SQLException e) {
      throw new MovieSqlException(e);
    }
  }

  private void initialize() {
    try {
      Connection connection = getConnection();
  
      SettingsDao.initialize(connection);
      MovieDao.initialize(connection);
  
      closeConnection(connection);
    }
    catch (SQLException e) {
      throw new MovieSqlException(e);
    }
  }

  @Override
  public Connection getConnection() {
    try {
      JDBCDataSource dataSource = new JDBCDataSource();
      dataSource.setDatabase(_url);
      Connection connection = dataSource.getConnection(USERNAME,  PASSWORD);
      connection.setAutoCommit(true);
      return connection;
    }
    catch (SQLException e) {
      throw new MovieSqlException(e);
    }
  }

  @Override
  public void closeConnection(Connection connection) {
    try {
      connection.close();
    }
    catch (SQLException e) {
      throw new MovieSqlException(e);
    }
  }

  @Override
  public void shutdown() {
    LOG.info("Shutdown database");
    _server.shutdownCatalogs(Database.CLOSEMODE_NORMAL);
    try {
      LOG.info("Deregister driver " + _url);
      Driver driver = DriverManager.getDriver(_url);
      DriverManager.deregisterDriver(driver);
    } catch (SQLException e) {
      throw new MovieSqlException(e);
    }
  }
}


//  public void setup() {
//    Server server = new Server();
//
//    try {
//
//      String url = String.format("%s:%s/%s;sql.enforce_strict_size=true", DB_PROTOCOL_FILE, dbFolder.getAbsolutePath(), DB_NAME);
//      String dbPath = String.format("file:%s/%s;sql.enforce_strict_size=true", dbFolder.getAbsolutePath(), DB_NAME);
//
//      server.setDatabaseName(0, DB_NAME);
//      server.setDatabasePath(0, dbPath);
//      server.setLogWriter(null);
//      server.setErrWriter(null);
//      server.start();
//
//      JDBCDataSource dataSource = new JDBCDataSource();
//      dataSource.setDatabase(url);
//      Connection connection = dataSource.getConnection("SA",  "");
//
//      // create table if it doesn't exist
//      Statement statement = connection.createStatement();
//      boolean isNew = statement.execute("CREATE TABLE IF NOT EXISTS INDEX_MAP (index VARCHAR(80), title varchar(120), year integer)");
//      System.out.println("is new db: " + isNew);
//
//      // add a record
//      PreparedStatement pStmnt = connection.prepareStatement("INSERT INTO INDEX_MAP VALUES (?,?,?)");
//      pStmnt.setString(1, "A1");
//      pStmnt.setString(2, "The Title");
//      pStmnt.setInt(3, 2017);
//      pStmnt.executeUpdate();
//
//      statement = connection.createStatement();
//      ResultSet resultSet = statement.executeQuery("SELECT * FROM INDEX_MAP");
//      while(resultSet.next()) {
//        System.out.println(String.format("%s|%s (%d)", resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3)));
//      }
//
//
//      DatabaseMetaData md = connection.getMetaData();
//      System.out.println("                         getURL: " + md.getURL());
//      System.out.println("                    getUserName: " + md.getUserName());
//      System.out.println("        getDatabaseMajorVersion: " + md.getDatabaseMajorVersion());
//      System.out.println("        getDatabaseMinorVersion: " + md.getDatabaseMinorVersion());
//      System.out.println("         getDatabaseProductName: " + md.getDatabaseProductName());
//      System.out.println("      getDatabaseProductVersion: " + md.getDatabaseProductVersion());
//      System.out.println(" getDefaultTransactionIsolation: " + md.getDefaultTransactionIsolation());
//      System.out.println("          getDriverMajorVersion: " + md.getDriverMajorVersion());
//      System.out.println("          getDriverMinorVersion: " + md.getDriverMinorVersion());
//      System.out.println("                  getDriverName: " + md.getDriverName());
//      System.out.println("               getDriverVersion: " + md.getDriverVersion());
//      System.out.println("         getExtraNameCharacters: " + md.getExtraNameCharacters());
//      System.out.println("       getIdentifierQuoteString: " + md.getIdentifierQuoteString());
//      System.out.println("            getJDBCMajorVersion: " + md.getJDBCMajorVersion());
//      System.out.println("            getJDBCMinorVersion: " + md.getJDBCMinorVersion());
//      System.out.println("      getMaxBinaryLiteralLength: " + md.getMaxBinaryLiteralLength());
//      System.out.println("        getMaxCatalogNameLength: " + md.getMaxCatalogNameLength());
//      System.out.println("         getMaxColumnsInGroupBy: " + md.getMaxColumnsInGroupBy());
//      System.out.println("           getMaxColumnsInIndex: " + md.getMaxColumnsInIndex());
//      System.out.println("         getMaxColumnsInOrderBy: " + md.getMaxColumnsInOrderBy());
//      System.out.println("          getMaxColumnsInSelect: " + md.getMaxColumnsInSelect());
//      System.out.println("           getMaxColumnsInTable: " + md.getMaxColumnsInTable());
//      System.out.println("              getMaxConnections: " + md.getMaxConnections());
//      System.out.println("         getMaxCursorNameLength: " + md.getMaxCursorNameLength());
//      System.out.println("              getMaxIndexLength: " + md.getMaxIndexLength());
//      System.out.println("      getMaxProcedureNameLength: " + md.getMaxProcedureNameLength());
//      System.out.println("                  getMaxRowSize: " + md.getMaxRowSize());
//      System.out.println("         getMaxSchemaNameLength: " + md.getMaxSchemaNameLength());
//      System.out.println("          getMaxStatementLength: " + md.getMaxStatementLength());
//      System.out.println("               getMaxStatements: " + md.getMaxStatements());
//      System.out.println("          getMaxTableNameLength: " + md.getMaxTableNameLength());
//      System.out.println("           getMaxUserNameLength: " + md.getMaxUserNameLength());
//      System.out.println("               getProcedureTerm: " + md.getProcedureTerm());
//      System.out.println("        getResultSetHoldability: " + md.getResultSetHoldability());
//      System.out.println("                  getSchemaTerm: " + md.getSchemaTerm());
//      System.out.println("          getSearchStringEscape: " + md.getSearchStringEscape());
//      System.out.println("                 getSQLKeywords: " + md.getSQLKeywords());
//      System.out.println("                getSQLStateType: " + md.getSQLStateType());
//      System.out.println("            getNumericFunctions: " + md.getNumericFunctions());
//      System.out.println("             getStringFunctions: " + md.getStringFunctions());
//      System.out.println("             getSystemFunctions: " + md.getSystemFunctions());
//      System.out.println("           getTimeDateFunctions: " + md.getTimeDateFunctions());
//      System.out.println("             importedKeyCascade: " + DatabaseMetaData.importedKeyCascade);
//      System.out.println("               isCatalogAtStart: " + md.isCatalogAtStart());
//      System.out.println("                     isReadOnly: " + md.isReadOnly());
//
//      connection.close();
//    }
//    finally {
//      server.stop();
//    }
//  }
