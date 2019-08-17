package com.movie.index.tmdb.v3.model;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.annotations.SerializedName;

public class TmdbCredits {

  private static class Crew {

    @SerializedName("name")
    String _name;

    @SerializedName("job")
    String _job;
  }

  private static class Cast {

    @SerializedName("name")
    String _name;

    @SerializedName("order")
    int _order;
  }

  @SerializedName("cast")
  private List<Cast> _cast;

  @SerializedName("crew")
  private List<Crew> _crew;

  public List<String> getCast() {
    return _cast.stream()
        .sorted((c1, c2) -> Integer.compare(c1._order, c2._order))
        .map(c -> c._name)
        .collect(Collectors.toList());
  }

  public List<String> getDirectors() {
    return _crew.stream()
        .filter(c -> "Director".equals(c._job))
        .map(c -> c._name)
        .collect(Collectors.toList());
  }
}
