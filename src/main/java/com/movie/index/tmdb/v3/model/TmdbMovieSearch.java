package com.movie.index.tmdb.v3.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TmdbMovieSearch {

  @SerializedName("page")
  private int _page;

  @SerializedName("total_results")
  private int _totalResults;

  @SerializedName("total_pages")
  private int _totalPages;

  @SerializedName("results")
  private List<TmdbMovie> _movies;

  public int getPage() {
    return _page;
  }

  public int getTotalPages() {
    return _totalPages;
  }

  public int getTotalResults() {
    return _totalResults;
  }

  public int getSize() {
    return _movies.size();
  }

  public List<TmdbMovie> getMovies() {
    return _movies;
  }
}
