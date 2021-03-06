package com.movie.index.container.servlet;

import com.movie.index.app.model.Movie;
import com.movie.index.util.GsonHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("serial")
public class MovieListingServlet extends AbstractHttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    List<Movie> allMovies = getDaoFactory().getMovieDao().getAllMovies();
    allMovies.forEach(movie -> movie.setPosterDataUri(null));
    allMovies.sort(Movie.TITLE_COMPARATOR);
    write(resp, GsonHelper.UI_PRETTY.toJson(allMovies));
  }
}
