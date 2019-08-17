package com.movie.index.container.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;

import com.movie.index.tmdb.v3.TmdbMediaUrl;
import com.movie.index.util.GsonHelper;

/**
 * http://localhost:8080/movies/detail?tmdb_id=8195
 */
@SuppressWarnings("serial")
public class TmdbMediaUrlServlet extends AbstractHttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      write(resp, GsonHelper.PRETTY.toJson(TmdbMediaUrl.TMDB_MEDIA_BASE_URLS));
    }
    catch(Throwable e) {
      resp.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
