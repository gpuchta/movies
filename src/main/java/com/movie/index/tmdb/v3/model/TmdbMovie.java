package com.movie.index.tmdb.v3.model;

import com.google.gson.annotations.SerializedName;
import com.movie.index.util.NumberUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TmdbMovie {

  static class TmdbGenre {

    @SerializedName("id")
    public int _id;

    @SerializedName("name")
    public String _name;
  }

  static class TmdbImages {

    @SerializedName("posters")
    private List<TmdbImage> _posters;

    @SerializedName("backdrops")
    private List<TmdbImage> _backdrops;
  }

  static class TmdbImage {

    @SerializedName("file_path")
    public String _filePath;

    @SerializedName("width")
    public int _width;

    @SerializedName("height")
    public int _height;
  }

  /* attributes which come with search query */

  @SerializedName("id")
  private int _id;

  @SerializedName("title")
  private String _title;

  @SerializedName("release_date")
  private String _releaseDate;

  @SerializedName("overview")
  private String _overview;

  @SerializedName("poster_path")
  private String _posterPath;

  @SerializedName("backdrop_path")
  private String _backdropPath;

  @SerializedName("vote_average")
  private float _voteAverage;

  @SerializedName("vote_count")
  private int _voteCount;


  public int getId() {
    return _id;
  }

  public String getTitle() {
    return _title;
  }

  public Integer getReleaseYear() {
    // "1998-09-25"
    return NumberUtils.toInteger(_releaseDate != null ? _releaseDate.split("-")[0] : null);
  }

  public String getOverview() {
    return _overview;
  }

  public String getPosterPath() {
    return _posterPath;
  }

  public String getBackdropPath() {
    return _backdropPath;
  }

  public float getVoteAverage() {
    return _voteAverage;
  }

  public int getVoteCount() {
    return _voteCount;
  }



  /* attributes which require detail query */

  @SerializedName("budget")
  private long _budget;

  @SerializedName("revenue")
  private long _revenue;

  @SerializedName("genres")
  private List<TmdbGenre> _genres;

  @SerializedName("images")
  private TmdbImages _images;

  public long getBudget() {
    return _budget;
  }

  public long getRevenue() {
    return _revenue;
  }

  public List<String> getGenres() {
    if(_genres == null) {
      return Collections.emptyList();
    }
    return _genres.stream().map(g -> g._name).collect(Collectors.toList());
  }

  public List<String> getPosters() {
    if(_images == null || _images._posters == null) {
      return Collections.emptyList();
    }
    return _images._posters.stream().map(p -> p._filePath).collect(Collectors.toList());
  }

  public List<String> getBackdrops() {
    if(_images == null || _images._backdrops == null) {
      return Collections.emptyList();
    }
    return _images._backdrops.stream().map(p -> p._filePath).collect(Collectors.toList());
  }


  /* attributes which are set */

  @SerializedName("directors")
  private List<String> _directors;

  @SerializedName("actors")
  private List<String> _actors;

  public void setCredits(TmdbCredits credits) {
    _directors = credits.getDirectors();
    _actors = credits.getCast();
  }

  public List<String> getDirectors() {
    return _directors == null ? Collections.emptyList() : _directors;
  }

  public List<String> getActors() {
    return _actors == null ? Collections.emptyList() : _actors;
  }

  @SerializedName("keywords")
  private List<String> _keywords;

  public void setKeywords(TmdbKeywords keywords) {
    _keywords = keywords.getKeywords();
  }

  public List<String> getKeywords() {
    return _keywords == null ? Collections.emptyList() : _keywords;
  }
}
