package com.movie.index.container.service;

import javax.servlet.ServletContext;

import com.movie.index.Config;
import com.movie.index.app.image.ImageManager;
import com.movie.index.app.model.Environment;
import com.movie.index.app.model.Settings;
import com.movie.index.db.dao.DaoFactory;
import com.movie.index.tmdb.v3.TmdbManager;

public class MovieServletContext {

  private static String _PREFIX                  = MovieServletContext.class.getSimpleName() + ":";
  private static String CONTEXT_CONFIG           = _PREFIX + "config";
  private static String CONTEXT_DAO_FACTORY      = _PREFIX + "daoFactory";
  private static String CONTEXT_TMDB_MANAGER     = _PREFIX + "tmdbManager";
  private static String CONTEXT_SETTINGS         = _PREFIX + "settings";
  private static String CONTEXT_ENVIRONMENT      = _PREFIX + "environment";
  private static String CONTEXT_SERVICE_MANAGER  = _PREFIX + "serviceManager";
  private static String CONTEXT_IMAGE_MANAGER    = _PREFIX + "imageManager";

  public static void setImageManager(ServletContext servletContext, ImageManager imageManager) {
    servletContext.setAttribute(CONTEXT_IMAGE_MANAGER, imageManager);
  }

  public static ImageManager getImageManager(ServletContext servletContext) {
    return (ImageManager)servletContext.getAttribute(CONTEXT_IMAGE_MANAGER);
  }

  public static void setEnvironment(ServletContext servletContext, Environment environment) {
    servletContext.setAttribute(CONTEXT_ENVIRONMENT, environment);
  }

  public static Environment getEnvironment(ServletContext servletContext) {
    return (Environment)servletContext.getAttribute(CONTEXT_ENVIRONMENT);
  }

  public static void setSettings(ServletContext servletContext, Settings settings) {
    servletContext.setAttribute(CONTEXT_SETTINGS, settings);
  }

  public static Settings getSettings(ServletContext servletContext) {
    return (Settings)servletContext.getAttribute(CONTEXT_SETTINGS);
  }

  public static void setConfig(ServletContext servletContext, Config config) {
    servletContext.setAttribute(CONTEXT_CONFIG, config);
  }

  public static Config getConfig(ServletContext servletContext) {
    return (Config)servletContext.getAttribute(CONTEXT_CONFIG);
  }

  public static void setDaoFactory(ServletContext servletContext, DaoFactory daoFactory) {
    servletContext.setAttribute(CONTEXT_DAO_FACTORY, daoFactory);
  }

  public static DaoFactory getDaoFactory(ServletContext servletContext) {
    return (DaoFactory)servletContext.getAttribute(CONTEXT_DAO_FACTORY);
  }

  public static void setTmdbManager(ServletContext servletContext, TmdbManager tmdbManager) {
    servletContext.setAttribute(CONTEXT_TMDB_MANAGER, tmdbManager);
  }

  public static TmdbManager getTmdbManager(ServletContext servletContext) {
    return (TmdbManager)servletContext.getAttribute(CONTEXT_TMDB_MANAGER);
  }

  public static void setServiceManager(ServletContext servletContext, ServiceManager serviceManager) {
    servletContext.setAttribute(CONTEXT_SERVICE_MANAGER, serviceManager);
  }

  public static ServiceManager getServiceManager(ServletContext servletContext) {
    return (ServiceManager)servletContext.getAttribute(CONTEXT_SERVICE_MANAGER);
  }
}
