package com.movie.index.container.service;

import com.movie.index.Config;
import com.movie.index.app.environment.EnvironmentManager;
import com.movie.index.app.image.ImageManager;
import com.movie.index.app.model.Environment;
import com.movie.index.app.model.Settings;
import com.movie.index.db.Datastore;
import com.movie.index.db.DatastoreFactory;
import com.movie.index.db.dao.DaoFactory;
import com.movie.index.tmdb.v3.TmdbManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.Optional;

public class ServiceManager {
  private static final String WEB_INF = "/WEB-INF";
  private final Logger LOG = LoggerFactory.getLogger(getClass());
  private final ServletContext _context;
  private Optional<Datastore> _store = Optional.empty();

  public ServiceManager(ServletContext context) {
    _context = context;
  }

  public ServiceManager init() {
    LOG.info("start service manager");
    File configFolder = new File(_context.getRealPath(WEB_INF));

    Config config = new Config().setConfigFolder(configFolder);
    LOG.trace("set config instance on servlet context");
    MovieServletContext.setConfig(_context, config);

    Environment environment = EnvironmentManager.load(config);
    LOG.trace("set environment instance on servlet context");
    MovieServletContext.setEnvironment(_context, environment);

    ImageManager imageManager = ImageManager.init(_context, config);
    LOG.trace("set image manager instance on servlet context");
    MovieServletContext.setImageManager(_context, imageManager);

    Settings settings = null;

    if(environment.hasDataFolder()) {
      config.setDataFolder(environment.getDataFolder());

      _store = Optional.of(DatastoreFactory.create(DatastoreFactory.Type.HSQLDB, config));
      _store.get().startup();

      DaoFactory daoFactory = DaoFactory.create(_store.get());
      LOG.trace("set dao factory on servlet context");
      MovieServletContext.setDaoFactory(_context, daoFactory);

      settings = daoFactory.getSettingsDao().load();
    }
    else {
      _store = Optional.empty();
    }

    if(settings != null && settings.hasTmdbApiKey()) {
      LOG.trace("set tmdb manager instance on servlet context");
      MovieServletContext.setTmdbManager(_context, new TmdbManager(settings.getTmdbApiKey()));
    }

    LOG.trace("set settings instance on servlet context");
    MovieServletContext.setSettings(_context, settings);

    config.log();
    environment.log();
    settings.log();
    imageManager.log();

    return this;
  }

  public void reload() {
    LOG.info("Restart service manager");
    shutdown();
    init();
  }

  public void shutdown() {
    LOG.info("Shutdown service manager");
    if(_store.isPresent()) {
      _store.get().shutdown();
    }
  }


}
