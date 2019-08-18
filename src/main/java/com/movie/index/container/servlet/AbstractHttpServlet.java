package com.movie.index.container.servlet;

import com.movie.index.Config;
import com.movie.index.app.model.Environment;
import com.movie.index.app.model.Settings;
import com.movie.index.container.service.MovieServletContext;
import com.movie.index.container.service.ServiceManager;
import com.movie.index.db.dao.DaoFactory;
import com.movie.index.tmdb.v3.TmdbManager;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;


@SuppressWarnings("serial")
public abstract class AbstractHttpServlet extends HttpServlet {

  protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

  protected boolean checkSettingsIncomplete(HttpServletResponse resp) throws IOException {
    if(!getSettings().isSetupComplete()) {
      write(resp, "Setup not complete");
      resp.setStatus(HttpStatus.SC_SERVICE_UNAVAILABLE);
      return true;
    }
    return false;
  }

  public void setEnvironment(Environment environment) {
    MovieServletContext.setEnvironment(getServletContext(), environment);
  }

  public Environment getEnvironment() {
    return MovieServletContext.getEnvironment(getServletContext());
  }

  public void setSettings(Settings settings) {
    MovieServletContext.setSettings(getServletContext(), settings);
  }

  public Settings getSettings() {
    return MovieServletContext.getSettings(getServletContext());
  }

  public void setConfig(Config config) {
    MovieServletContext.setConfig(getServletContext(), config);
  }

  public Config getConfig() {
    return MovieServletContext.getConfig(getServletContext());
  }

  public void setDaoFactory(DaoFactory daoFactory) {
    MovieServletContext.setDaoFactory(getServletContext(), daoFactory);
  }

  public DaoFactory getDaoFactory() {
    return MovieServletContext.getDaoFactory(getServletContext());
  }

  public void setTmdbManager(TmdbManager tmdbManager) {
    MovieServletContext.setTmdbManager(getServletContext(), tmdbManager);
  }

  public TmdbManager getTmdbManager() {
    return MovieServletContext.getTmdbManager(getServletContext());
  }


  protected void reloadServiceManager() {
    ServiceManager serviceManager = MovieServletContext.getServiceManager(getServletContext());
    serviceManager.reload();
  }

//  protected void dispatchToSetupIncomplete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//    RequestDispatcher view = req.getRequestDispatcher("/setup-incomplete.html");
//    view.forward(req, resp);
//  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setStatus(HttpStatus.SC_METHOD_NOT_ALLOWED);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setStatus(HttpStatus.SC_METHOD_NOT_ALLOWED);
  }

  @Override
  protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setStatus(HttpStatus.SC_METHOD_NOT_ALLOWED);
  }

  @Override
  protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setStatus(HttpStatus.SC_METHOD_NOT_ALLOWED);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setStatus(HttpStatus.SC_METHOD_NOT_ALLOWED);
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setStatus(HttpStatus.SC_METHOD_NOT_ALLOWED);
  }

  @Override
  protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setStatus(HttpStatus.SC_METHOD_NOT_ALLOWED);
  }


  protected String getPath(HttpServletRequest req) throws IOException {
    return req.getPathInfo();
  }

  protected String getInputAsString(HttpServletRequest req) throws IOException {
    ServletInputStream inputStream = req.getInputStream();
    String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    inputStream.close();
    return content;
  }

  protected void write(HttpServletResponse resp, String template, Object... args) throws IOException {
    resp.setCharacterEncoding("UTF-8");
    PrintWriter writer = resp.getWriter();
    writer.append(String.format(template, args));
    writer.close();
  }

  protected void writePlain(HttpServletResponse resp, String... args) throws IOException {
    resp.setCharacterEncoding("UTF-8");
    PrintWriter writer = resp.getWriter();
    for (int i = 0; i < args.length; i++) {
      writer.append(args[i]);
    }
    writer.close();
  }

  protected void download(HttpServletResponse resp, String filename, String... args) throws IOException {
    resp.setCharacterEncoding("UTF-8");
    resp.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", filename));
    PrintWriter writer = resp.getWriter();
    for (int i = 0; i < args.length; i++) {
      writer.append(args[i]);
    }
    writer.close();
  }
}
