package com.movie.index.db.dao;

import com.movie.index.app.model.Locator;
import com.movie.index.app.model.Movie;
import com.movie.index.util.GsonHelper;

class MovieBuilder extends Movie {

  public MovieBuilder setTmdb_id(int tmdb_id) {
    _tmdb_id = tmdb_id;
    return this;
  }

  public MovieBuilder setTitle(String title) {
    _title = title;
    return this;
  }

  public MovieBuilder setYear(int year) {
    _year = year;
    return this;
  }

  public MovieBuilder setOverview(String overview) {
    _overview = overview;
    return this;
  }

  public MovieBuilder setPosterPath(String poster_path) {
    _poster_path = poster_path;
    return this;
  }

  public MovieBuilder setPosterDataUri(String poster_data_uri) {
    _poster_data_uri = poster_data_uri;
    return this;
  }

  public MovieBuilder setBackdropPath(String backdrop_path) {
    _backdrop_path = backdrop_path;
    return this;
  }

  public MovieBuilder setVoteAverage(float vote_average) {
    _vote_average = vote_average;
    return this;
  }

  public MovieBuilder setVoteCount(int vote_count) {
    _vote_count = vote_count;
    return this;
  }

  public MovieBuilder setBudget(int budget) {
    _budget = budget;
    return this;
  }

  public MovieBuilder setRevenue(int revenue) {
    _revenue = revenue;
    return this;
  }

  public MovieBuilder setGenres(String[] genres) {
    _genres = genres;
    return this;
  }

  public MovieBuilder setDirectors(String[] directors) {
    _directors = directors;
    return this;
  }

  public MovieBuilder setActors(String[] actors) {
    _actors = actors;
    return this;
  }

  @Override
  public MovieBuilder setLocator(String locator) {
    _locator = Locator.create(locator);
    return this;
  }

  public static MovieBuilder builder() {
    return new MovieBuilder();
  }

  public Movie build(String json) {
    return GsonHelper.BASIC.fromJson(json, Movie.class);
  }
}
