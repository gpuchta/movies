package com.movie.index.container.servlet;

import com.movie.index.app.model.Movie;
import com.movie.index.util.GsonHelper;
import org.apache.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")
public class MovieDeleteServlet extends AbstractHttpServlet {

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      String jsonString = getInputAsString(req);
      Movie movie = GsonHelper.BASIC.fromJson(jsonString, Movie.class);

      getDaoFactory().getMovieDao().delete(movie.getTmdbId());
      resp.setStatus(HttpStatus.SC_OK);
    }
    catch(Throwable e) {
      resp.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
      write(resp, e.getMessage());
    }
  }
}
