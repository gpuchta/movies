var movieDb = movieDb || {};

//movieDb.config.filterCriteriaNode.unbind().bind('customAction', updateFilterCount);

//movieDb.config.filterCriteriaNode.triggerHandler('customAction');

//var updateFilterCount = function(event, data) {
//  console.log('customAction called!');
//};

/* --------------------------------------------
 *
 * -------------------------------------------- */
movieDb.config = (function() {

  function init(settings) {
    console.log('init config');

    // init function will no longer be accessible
    movieDb.config = {
      movieDbUrl: "https://www.themoviedb.org/movie/{tmdb_id}",
      movieDbSearchUrl: "https://www.themoviedb.org/search/movie?query={title}+y:{year}",

      binderKey: 'BINDER',
      providerKey: 'PROVIDER',
      RENDER_MORE_HEIGHT: 2000
    };

    $.extend(movieDb.config, settings);
  }

  return {
    init: init
  };
})();
