package com.movie.index.container.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorLoggerServlet extends AbstractHttpServlet {
  private final Logger LOG = LoggerFactory.getLogger(getClass());

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Throwable throwable = (Throwable) req.getAttribute("javax.servlet.error.exception");
    Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
    String servletName = (String) req.getAttribute("javax.servlet.error.servlet_name");

    if (servletName == null) {
      servletName = "Unknown";
    }
    String requestUri = (String) req.getAttribute("javax.servlet.error.request_uri");

    if (requestUri == null) {
      requestUri = "Unknown";
    }

    String message = String.format("%s [status:%d] (%s) %s ", servletName, statusCode, requestUri, throwable.getMessage());
    LOG.error(message, throwable);
  }
}
