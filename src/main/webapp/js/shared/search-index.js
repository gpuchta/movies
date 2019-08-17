/* --------------------------------------------
 *
 * -------------------------------------------- */
movieDb.searchIndex = (function() {

  var _genres = [];

  function init() {
    console.log('init searchIndex');

    var movies = movieDb.config.movies;
    for(var n = 0; n < movies.length; n++) {
      var movie = movies[n];
      var terms = [];
      $.merge(terms, [ movie.title.replace(/  */g, ' ') ]);
      $.merge(terms, movie.genres.map(movieDb.shared.toGenreTerm));
      $.merge(terms, movie.actors.map(movieDb.shared.toActorTerm));
      $.merge(terms, movie.directors.map(movieDb.shared.toDirectorTerm));
      $.merge(terms, [ movie.year ].map(movieDb.shared.toYearTerm));
      if(movie.keywords) {
        $.merge(terms, movie.keywords);
      }
      if(movie.locator) {
        if(movie.locator.type === movieDb.config.binderKey) {
          $.merge(terms, [ movie.locator.binder + movie.locator.page ]
          .map(movieDb.shared.toIndexTerm));
        }
        else if(movie.locator.type === movieDb.config.providerKey) {
          $.merge(terms, [ movie.locator.provider ]
          .map(movieDb.shared.toIndexTerm));
        }
      }
      else {  // TODO remove me once all are movies have an index
        $.merge(terms, [ 'null' ].map(movieDb.shared.toIndexTerm));
      }
      movie.dataSearchKeywords = terms.join(',').toLowerCase();

      // capture unique genres
      $.merge(_genres, movie.genres
        .map(function(genre) {
          return genre.toLowerCase();
        })
        .filter(function(genre) {
          return _genres.indexOf(genre) == -1;
        }));
    }
  }

  function isGenre(val) {
    return _genres.indexOf(val.toLowerCase()) > -1;
  }

  function filter(criteria) {
    var filteredMovies = [];

    $(movieDb.config.movies).each(function(index, movie) {
      for(i=0; i<criteria.length; i++) {
        let term = criteria[i];

        let excludeTerm = term.charAt(0) === '-';
        let includeTerm = !excludeTerm;

        if(excludeTerm) {
          term = term.substr(1);
        }

        if(movieDb.shared.isLiteral(term)) {
          term = movieDb.shared.categoryValue(term);
        }

        let termNotFound = movie.dataSearchKeywords.indexOf(term.trim().toLowerCase()) == -1;
        let termFound = !termNotFound;

        if((includeTerm && termNotFound) || (excludeTerm && termFound)) {
          return;
        }
      }
      filteredMovies.push(movie);
    });

    return filteredMovies;
  }

  function deleteMovie(movieToDelete) {
    let indexToDelete = -1;
    $(movieDb.config.movies).each(function(index, movie) {
      if(movie.tmdb_id === movieToDelete.tmdb_id) {
        indexToDelete = index;
      }
    });

    if(indexToDelete > -1) {
      console.log('remove movie with array index %d', indexToDelete);
      movieDb.config.movies.splice(indexToDelete, 1);
      init();
    }
  }

  return {
    init: init,
    filter: filter,
    isGenre: isGenre,
    deleteMovie: deleteMovie
  };
})();
