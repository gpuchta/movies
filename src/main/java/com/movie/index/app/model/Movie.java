package com.movie.index.app.model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.annotations.SerializedName;
import com.movie.index.tmdb.v3.model.TmdbMovie;
import com.movie.index.util.GsonHelper;
import com.movie.index.util.NumberUtils;

public class Movie {

  public static Comparator<Movie> ASC_YEAR_COMPARATOR = new Comparator<Movie>() {
    @Override
    public int compare(Movie m1, Movie m2) {
      return Integer.compare(
          NumberUtils.toInt(m1.getYear(), -1),
          NumberUtils.toInt(m2.getYear(), -1));
    }
  };

  public static Comparator<Movie> DESC_YEAR_COMPARATOR = new Comparator<Movie>() {
    @Override
    public int compare(Movie m1, Movie m2) {
      return Integer.compare(
          NumberUtils.toInt(m2.getYear(), -1),
          NumberUtils.toInt(m1.getYear(), -1));
    }
  };

  public static Comparator<Movie> TITLE_COMPARATOR = new Comparator<Movie>() {
    @Override
    public int compare(Movie m1, Movie m2) {
      return m1.getTitle().compareTo(m2.getTitle());
    }
  };

  @SerializedName("tmdb_id")
  protected int _tmdb_id;

  @SerializedName("title")
  protected String _title;

  @SerializedName("year")
  protected Integer _year;

  @SerializedName("overview")
  protected String _overview;

  @SerializedName("poster_path")
  protected String _poster_path;

  @SerializedName("poster_data_uri")
  protected String _poster_data_uri;

  @SerializedName("backdrop_path")
  protected String _backdrop_path;

  @SerializedName("vote_average")
  protected float _vote_average;

  @SerializedName("vote_count")
  protected int _vote_count;

  @SerializedName("budget")
  protected long _budget;

  @SerializedName("revenue")
  protected long _revenue;

  @SerializedName("genres")
  protected String[] _genres;

  @SerializedName("directors")
  protected String[] _directors;

  @SerializedName("actors")
  protected String[] _actors;

  @SerializedName("keywords")
  protected String[] _keywords;

  @SerializedName("posters")
  protected String[] _posters;

  @SerializedName("backdrops")
  protected String[] _backdrops;

  @SerializedName("locator")
  protected Locator _locator;

  @SerializedName("has_details")
  protected boolean _hasDetails;

  public int getTmdbId() {
    return _tmdb_id;
  }

  public String getTitle() {
    return _title;
  }

  public Integer getYear() {
    return _year;
  }

  public String getOverview() {
    return _overview;
  }

  public String getPosterPath() {
    return _poster_path;
  }

  public String getPosterDataUri() {
    return _poster_data_uri;
  }

  public String getBackdropPath() {
    return _backdrop_path;
  }

  public float getVoteAverage() {
    return _vote_average;
  }

  public int getVoteCount() {
    return _vote_count;
  }

  public long getBudget() {
    return _budget;
  }

  public long getRevenue() {
    return _revenue;
  }

  public String[] getGenres() {
    return _genres;
  }

  public String[] getDirectors() {
    return _directors;
  }

  public String[] getActors() {
    return _actors;
  }

  public String[] getKeywords() {
    return _keywords;
  }

  public Locator getLocator() {
    return _locator;
  }

  public Movie setPosterPath(String poster_path) {
    _poster_path = poster_path;
    return this;
  }

  public Movie setPosterDataUri(String poster_data_uri) {
    _poster_data_uri = poster_data_uri;
    return this;
  }

  public Movie setLocator(Locator locator) {
    _locator = locator;
    return this;
  }

  public Movie setLocator(String locator) {
    _locator = Locator.create(locator);
    return this;
  }

  public Movie setHasDetails(boolean hasDetails) {
    _hasDetails = hasDetails;
    return this;
  }

  public static Movie fromTmdbMovie(TmdbMovie tmdbMovie) {
    Movie movie = new Movie();

    movie._tmdb_id = tmdbMovie.getId();
    movie._title = tmdbMovie.getTitle();
    movie._year = tmdbMovie.getReleaseYear();
    movie._overview = tmdbMovie.getOverview();
    movie._poster_path = tmdbMovie.getPosterPath();
    movie._backdrop_path = tmdbMovie.getBackdropPath();
    movie._vote_average = tmdbMovie.getVoteAverage();
    movie._vote_count = tmdbMovie.getVoteCount();
    movie._budget = tmdbMovie.getBudget();
    movie._revenue = tmdbMovie.getRevenue();

    List<String> genres = tmdbMovie.getGenres();
    movie._genres = genres.toArray(new String[genres.size()]);

    List<String> directors = tmdbMovie.getDirectors().stream().limit(3).collect(Collectors.toList());
    movie._directors = directors.toArray(new String[directors.size()]);

    List<String> actors = tmdbMovie.getActors().stream().limit(7).collect(Collectors.toList());
    movie._actors = actors.toArray(new String[actors.size()]);

    List<String> keywords = tmdbMovie.getKeywords().stream().collect(Collectors.toList());
    movie._keywords = keywords.toArray(new String[keywords.size()]);

    List<String> posters = tmdbMovie.getPosters().stream().collect(Collectors.toList());
    movie._posters = posters.toArray(new String[posters.size()]);

    List<String> backdrops = tmdbMovie.getBackdrops().stream().collect(Collectors.toList());
    movie._backdrops = backdrops.toArray(new String[backdrops.size()]);

    return movie;
  }

  public String toJson() {
    return GsonHelper.BASIC.toJson(this);
  }
}
