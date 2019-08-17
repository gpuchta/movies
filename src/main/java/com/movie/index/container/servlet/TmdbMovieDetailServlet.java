package com.movie.index.container.servlet;

import com.movie.index.app.model.Movie;
import com.movie.index.db.dao.MovieDao;
import com.movie.index.tmdb.v3.model.TmdbMovie;
import com.movie.index.util.ExtLogger;
import com.movie.index.util.GsonHelper;
import com.movie.index.util.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * http://localhost:8080/movies/detail?tmdb_id=8195
 */
@SuppressWarnings("serial")
public class TmdbMovieDetailServlet extends AbstractHttpServlet {
  private static final ExtLogger LOG = ExtLogger.getLogger(MovieDao.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if(checkSettingsIncomplete(resp)) {
      return;
    }

    Integer tmdbId = NumberUtils.toInteger(StringUtils.stripStart(req.getPathInfo(), "/"));

    if(tmdbId == null) {
      write(resp, "Tmdb ID in URL must not be empty");
      resp.setStatus(HttpStatus.SC_BAD_REQUEST);
      return;
    }

    TmdbMovie tmdbMovie = getTmdbManager().getMovieById(tmdbId);
    Movie movie = Movie.fromTmdbMovie(tmdbMovie).setHasDetails(true);

    Optional<Movie> optionalMovie = getDaoFactory().getMovieDao().getMovieById(tmdbId);
    if (optionalMovie.isPresent()) {
      // set/override the info we want to keep
      movie.setLocator(optionalMovie.get().getLocator());
      movie.setPosterDataUri(optionalMovie.get().getPosterDataUri());
      movie.setPosterPath(optionalMovie.get().getPosterPath());
    }

    write(resp, GsonHelper.UI_PRETTY.toJson(movie));
  }
}
