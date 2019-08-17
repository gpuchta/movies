/* --------------------------------------------
 *
 * -------------------------------------------- */
movieDb.app = (function() {

  function init() {
    console.log('init app-index');
    $(window).resize(handleResize);
  }

  function handleResize() {
    movieDb.posterDetail.hideDetail();
    movieDb.poster.deselect();
  }

  function updateSearch(doUpdateHash) {
    console.log('app-index: updateSearch');
    var criteria = movieDb.filterCriteria.current();
    var filteredMovies = movieDb.searchIndex.filter(criteria);
    if(doUpdateHash) {
      movieDb.browserHash.update(criteria);
    }
    movieDb.filterCriteria.updateCount(filteredMovies.length);
    movieDb.app.moviesToRender = filteredMovies;
    movieDb.posterGrid.render();
  }

  function getMovieDetails(listItem, callback) {
    callback(listItem, listItem.data('movie'));
  }

  function handlePosterDetailClick(e) {
    let target = $(e.target);
    let listItem = target.closest("li");

    if (listItem.hasClass('detail')) {
      if(target.hasClass('index-edit')) {
        movieDb.posterDetail.showIndexSelector(listItem);
      }
      else if(target.hasClass('movie-delete')) {
        let listItemToDelete = $('.grid-poster.selected');
        let movie = listItemToDelete.data('movie');
        movieDb.shared.confirmDialog('Delete Movie', 'Do you want to delete this movie?', function() {
          console.log('delete movie ' + movie.tmdb_id);
          movieDb.serverApi.deleteMovie(movie, function() {
            movieDb.posterDetail.hideDetail();
            listItemToDelete.remove();
            movieDb.searchIndex.deleteMovie(movie);
          });
        });
      }
      else if(target.is('span')) {
        console.log('posterDetail: clicked %o', target.get(0));
        movieDb.filterCriteria.add(target);
        updateSearch();
      }
    }
  }

  return {
    init: init,
    isManagePage: function() { return false; },
    isIndexPage: function() { return true; },
    updateSearch: updateSearch,
    getMovieDetails: getMovieDetails,
    handlePosterDetailClick: handlePosterDetailClick
  };
})();

/* --------------------------------------------
 *
 * -------------------------------------------- */
$(function() {
 movieDb.serverApi.getAllMovies(function(movies) {
   movieDb.config.init({ isTmdbData: false, movies: movies });

   for(var component in movieDb) {
     var func = movieDb[component].init;
     if ($.isFunction(func)) {
       func();
     }
   }

   var criteria = movieDb.browserHash.toCriteria();
   movieDb.filterCriteria.set(criteria);
   movieDb.app.updateSearch();
 });
});
