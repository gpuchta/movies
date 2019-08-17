package com.movie.index.container.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;

import com.movie.index.app.model.Movie;
import com.movie.index.container.service.MovieServletContext;
import com.movie.index.util.GsonHelper;
import com.movie.index.util.PosterHelper;

@SuppressWarnings("serial")
public class MovieStoreServlet extends AbstractHttpServlet {

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      String jsonString = getInputAsString(req);
      Movie movie = GsonHelper.BASIC.fromJson(jsonString, Movie.class);

      PosterHelper.generatePosterDataUri(movie, MovieServletContext.getImageManager(req.getServletContext()));
      PosterHelper.persistPoster(movie, getConfig());

      getDaoFactory().getMovieDao().persist(movie);
      resp.setStatus(HttpStatus.SC_OK);
    }
    catch(Throwable e) {
      resp.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
      write(resp, e.getMessage());
    }
  }
}
