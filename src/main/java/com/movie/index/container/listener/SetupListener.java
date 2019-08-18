package com.movie.index.container.listener;

import com.movie.index.container.service.MovieServletContext;
import com.movie.index.container.service.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class SetupListener implements ServletContextListener, ServletContextAttributeListener {
  private final Logger LOG = LoggerFactory.getLogger(getClass());

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
