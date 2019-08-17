package com.movie.index.app.callback;

import com.movie.index.app.model.Movie;

public interface Callback<T> {

  static Callback<Movie> MOVIE_NOOP_CALLBACK = new Callback<Movie>() {
    @Override
    public void handle(Movie item) { }
  };

  void handle(T item);
}
