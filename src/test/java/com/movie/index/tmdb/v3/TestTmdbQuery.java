package com.movie.index.tmdb.v3;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.movie.index.tmdb.v3.model.TmdbCredits;
import com.movie.index.tmdb.v3.model.TmdbKeywords;
import com.movie.index.tmdb.v3.model.TmdbMovie;
import com.movie.index.tmdb.v3.model.TmdbMovieSearch;

public class TestTmdbQuery {

  @Test
  public void testUrlsBuilder() {
    TmdbManager tmdbQuery = new TmdbManager("test-api-key");

    Assert.assertEquals(tmdbQuery.toSearchUrl("Ronin", 1998),
        "https://api.themoviedb.org/3/search/movie?api_key=test-api-key&query=Ronin&year=1998");

    Assert.assertEquals(tmdbQuery.toDetailUrl(8195),
        "https://api.themoviedb.org/3/movie/8195?api_key=test-api-key&append_to_response=images");

    Assert.assertEquals(tmdbQuery.toCreditsUrl(8195),
        "https://api.themoviedb.org/3/movie/8195/credits?api_key=test-api-key");

    Assert.assertEquals(tmdbQuery.toKeywordsUrl(8195),
        "https://api.themoviedb.org/3/movie/8195/keywords?api_key=test-api-key");
}

  @Test
  public void testSearchDeserialization() throws IOException {
    Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    TmdbMovieSearch movies = gson.fromJson(Helper.getResource(this, "ronin-search.json"), TmdbMovieSearch.class);

    Assert.assertEquals(movies.getPage(), 1);
    Assert.assertEquals(movies.getTotalPages(), 1);
    Assert.assertEquals(movies.getTotalResults(), 1);
    Assert.assertEquals(movies.getSize(), 1);

    TmdbMovie movie = movies.getMovies().iterator().next();

    Assert.assertEquals(movie.getTitle(), "Ronin");

    Assert.assertTrue(movie.getOverview().startsWith("A briefcase with undisclosed contents"));
    Assert.assertEquals(movie.getPosterPath(), "/UoFUXemMYGKuCN01Y111JlPjjr.jpg");
    Assert.assertEquals(movie.getBackdropPath(), "/tzAtSkpEyAftOuNqcShaBOxWIkw.jpg");
    Assert.assertEquals(movie.getVoteAverage(), 6.7f);
    Assert.assertEquals(movie.getVoteCount(), 489);
  }

  @Test
  public void testDetailDeserialization() throws IOException {
    Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    TmdbMovie movie = gson.fromJson(Helper.getResource(this, "ronin-details.json"), TmdbMovie.class);

    Assert.assertEquals(movie.getTitle(), "Ronin");
    Assert.assertTrue(movie.getOverview().startsWith("A briefcase with undisclosed contents"));
    Assert.assertEquals(movie.getPosterPath(), "/UoFUXemMYGKuCN01Y111JlPjjr.jpg");
    Assert.assertEquals(movie.getBackdropPath(), "/tzAtSkpEyAftOuNqcShaBOxWIkw.jpg");
    Assert.assertEquals(movie.getVoteAverage(), 6.7f);
    Assert.assertEquals(movie.getVoteCount(), 491);
    Assert.assertEquals(movie.getBudget(), 55000000);
    Assert.assertEquals(movie.getRevenue(), 41610884);

    Assert.assertEquals(movie.getGenres().size(), 4);
    Assert.assertEquals(movie.getGenres().get(0), "Action");
    Assert.assertEquals(movie.getGenres().get(1), "Thriller");
    Assert.assertEquals(movie.getGenres().get(2), "Crime");
    Assert.assertEquals(movie.getGenres().get(3), "Adventure");

    Assert.assertEquals(movie.getPosters().size(), 24);
    Assert.assertEquals(movie.getBackdrops().size(), 12);

    TmdbCredits credits = gson.fromJson(Helper.getResource(this, "ronin-credits.json"), TmdbCredits.class);

    movie.setCredits(credits);

    Assert.assertEquals(movie.getDirectors().stream().limit(3).collect(Collectors.toList()),
        Arrays.asList("John Frankenheimer"));

    Assert.assertEquals(movie.getActors().stream().limit(7).collect(Collectors.toList()),
        Arrays.asList("Robert De Niro", "Jean Reno", "Natascha McElhone",
            "Stellan Skarsg\u00E5rd", "Sean Bean", "Jonathan Pryce", "Skipp Sudduth"));
  }

  @Test
  public void testKeywordsDeserialization() throws IOException {
    Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    TmdbKeywords keywords = gson.fromJson(Helper.getResource(this, "ronin-keywords.json"), TmdbKeywords.class);

    Assert.assertNotNull(keywords.getKeywords());
    Assert.assertEquals(keywords.getKeywords().size(), 3);
    Assert.assertEquals(keywords.getKeywords(), Arrays.asList("arms deal", "merry go round", "shot in the chest"));
  }

  @Test(enabled=false)
  public void testLiveQueries() {
    TmdbManager tmdbQuery = new TmdbManager("invalid-key");
    TmdbMovieSearch movies = tmdbQuery.searchMoviesByTitleAndYear("Ronin", 1998);

    Assert.assertEquals(movies.getPage(), 1);
    Assert.assertEquals(movies.getTotalPages(), 1);
    Assert.assertEquals(movies.getTotalResults(), 1);
    Assert.assertEquals(movies.getSize(), 1);
    Assert.assertEquals(movies.getMovies().iterator().next().getId(), 8195);

    TmdbMovie movie = tmdbQuery.getMovieById(8195);

    Assert.assertFalse(movie.getPosters().isEmpty());
    Assert.assertFalse(movie.getBackdrops().isEmpty());
    Assert.assertTrue(movie.getOverview().startsWith("A briefcase with undisclosed contents"));
    Assert.assertEquals(movie.getPosterPath(), "/UoFUXemMYGKuCN01Y111JlPjjr.jpg");
    Assert.assertEquals(movie.getBackdropPath(), "/tzAtSkpEyAftOuNqcShaBOxWIkw.jpg");
  }
}
