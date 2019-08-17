package com.movie.index.tmdb.v3;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.io.IOUtils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.movie.index.app.model.Movie;
import com.movie.index.tmdb.v3.model.TmdbCredits;
import com.movie.index.tmdb.v3.model.TmdbMovie;

public class Helper {

  public static String getResource(Object caller, String name) throws IOException {
    return getResource(caller.getClass(), name);
  }

  public static String getResource(Class<?> caller, String name) throws IOException {
    Writer writer = new StringWriter();
    IOUtils.copy(caller.getResourceAsStream(name), writer, "UTF-8");
    return writer.toString();
  }

  public static Movie getTestMovie() throws Exception {
    Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    TmdbMovie tmdbMovie = gson.fromJson(getResource(Helper.class, "/com/movie/index/tmdb/v3/ronin-details.json"), TmdbMovie.class);
    TmdbCredits credits = gson.fromJson(getResource(Helper.class, "/com/movie/index/tmdb/v3/ronin-credits.json"), TmdbCredits.class);
    tmdbMovie.setCredits(credits);
    Movie movie = Movie.fromTmdbMovie(tmdbMovie);
    movie.setLocator("C130");
    return movie;
  }
}
