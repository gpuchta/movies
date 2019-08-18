package com.movie.index.container.servlet;

import com.movie.index.app.model.Movie;
import com.movie.index.tmdb.v3.model.TmdbMovie;
import com.movie.index.tmdb.v3.model.TmdbMovieSearch;
import com.movie.index.util.GsonHelper;
import com.movie.index.util.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * http://localhost:8080/movies/search?title=Ronin&year=1998
 */
@SuppressWarnings("serial")
public class TmdbMovieAnalyzerServlet extends AbstractHttpServlet {

  private boolean isValidYear(int year) {
    return year >= 1900 && year <= Calendar.getInstance().get(Calendar.YEAR) + 2;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if(checkSettingsIncomplete(resp)) {
      return;
    }

    // load index mapping csv and analyze how many move searches come back with one result

    String title = req.getParameter("title");
    Integer year = NumberUtils.toInteger(req.getParameter("year"));

    if(StringUtils.isBlank(title)) {
      resp.setStatus(HttpStatus.SC_NOT_FOUND);
      return;
    }

    if(year != null && !isValidYear(year)) {
      resp.setStatus(HttpStatus.SC_BAD_REQUEST);
      return;
    }

    TmdbMovieSearch tmdbMovieSearch = getTmdbManager().searchMoviesByTitleAndYear(title, year);

    List<Movie> movieList = new ArrayList<>();
    for (TmdbMovie tmdbMovie : tmdbMovieSearch.getMovies()) {
      movieList.add(Movie.fromTmdbMovie(tmdbMovie));
    }

    resp.setStatus(HttpStatus.SC_OK);
    write(resp, GsonHelper.BASIC.toJson(movieList));
  }
}
