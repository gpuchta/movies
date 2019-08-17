package com.movie.index.container.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.movie.index.container.service.MovieServletContext;
import com.movie.index.container.service.ServiceManager;
import com.movie.index.util.ExtLogger;


public class SetupListener implements ServletContextListener, ServletContextAttributeListener {

  private static final ExtLogger LOG = ExtLogger.getLogger(SetupListener.class);

  @Override
  public void contextInitialized(ServletContextEvent event) {
    LOG.info("Initialize application");
    ServletContext context = event.getServletContext();
    MovieServletContext.setServiceManager(context, new ServiceManager(context).init());
  }

  @Override
  public void contextDestroyed(ServletContextEvent event) {
    LOG.info("Teardown application ...");
    ServletContext context = event.getServletContext();
    MovieServletContext.getServiceManager(context).shutdown();
  }

  @Override
  public void attributeAdded(ServletContextAttributeEvent event) {
  }

  @Override
  public void attributeReplaced(ServletContextAttributeEvent event) {
  }

  @Override
  public void attributeRemoved(ServletContextAttributeEvent event) {
  }
}
