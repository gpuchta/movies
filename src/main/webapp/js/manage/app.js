/* --------------------------------------------
 *
 * -------------------------------------------- */
movieDb.app = (function() {

  function init() {
    console.log('init app-manage');
    $(window).resize(handleResize);
  }

  function handleResize() {
    movieDb.posterDetail.hideDetail();
    movieDb.poster.deselect();
  }

  function renderResult(movies) {
    movieDb.filterCriteria.updateCount(movies.length);
    movieDb.app.moviesToRender = movies;
    movieDb.posterGrid.render();
  }

  function updateSearch(doUpdateHash) {
    console.log('app-manage: updateSearch TODO');
    var criteria = movieDb.filterCriteria.current();
    if(doUpdateHash) {
      movieDb.browserHash.update(criteria);
    }
    movieDb.serverApi.searchTmdbMovie(criteria, renderResult);
  }

  function getMovieDetails(listItem, callback) {
    let movieData = listItem.data('movie');
    if(!movieData) {
      throw "no movie data available!";
    }
    if(!movieData.has_details) {
      movieDb.serverApi.getTmdbMovieDetails(movieData, function(movie) {
        listItem.data('movie', movie);
        listItem.data('has-details', true);
        callback(listItem, movie);
      });
    } else {
      callback(listItem, movieData);
    }
  }

  function handlePosterDetailClick(e) {
    let target = $(e.target);
    let listItem = target.closest("li");

    console.log('posterDetail(manage): clicked %o', target.get(0));
    if(target.hasClass('movie-save')) {
      let listItem = $('.grid-poster.selected');
      let movie = listItem.data('movie');

      if(movie.locator) {
        movieDb.shared.confirmDialog('Save Movie', 'Do you want to save this movie?', function() {
          console.log('save movie');
          movieDb.serverApi.persistMovie(movie, listItem);
        });
      }
      else {
        movieDb.shared.infoDialog('Save Movie', 'Specify where to find it before saving.');
      }
    }
    else if(target.hasClass('index-edit')) {
      movieDb.posterDetail.showIndexSelector(listItem);
    }
    else if (listItem.hasClass('detail-poster')) {
      console.log('app-manage: detail poster clickHandler called');
      let newPosterPath = listItem.attr('poster_path');
      console.log('new poster path: ', newPosterPath);
      $('.grid-poster.selected').data('movie').poster_path = newPosterPath;
      $('.grid-poster.selected img').attr('src', movieDb.shared.toPosterUrl(
          movieDb.shared.POSTER_154, newPosterPath));
      $('.detail-poster').removeClass('selected');
     listItem.addClass('selected');
    }
  }

  return {
    init: init,
    isManagePage: function() { return true; },
    isIndexPage: function() { return false; },
    updateSearch: updateSearch,
    getMovieDetails: getMovieDetails,
    handlePosterDetailClick: handlePosterDetailClick
  };
})();

/* --------------------------------------------
 *
 * -------------------------------------------- */
$(function() {
 movieDb.config.init({ isTmdbData: true });

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
