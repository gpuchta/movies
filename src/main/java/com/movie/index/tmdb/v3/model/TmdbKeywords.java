package com.movie.index.tmdb.v3.model;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.annotations.SerializedName;

public class TmdbKeywords {

  private static class Keyword {

    @SerializedName("name")
    String _name;
  }

  @SerializedName("keywords")
  private List<Keyword> _keywords;

  public List<String> getKeywords() {
    return _keywords.stream()
        .sorted((c1, c2) -> c1._name.compareTo(c2._name))
        .map(c -> c._name)
        .collect(Collectors.toList());
  }
}
