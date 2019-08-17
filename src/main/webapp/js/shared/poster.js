/* --------------------------------------------
 *
 * -------------------------------------------- */
movieDb.poster = (function() {

  const POSTER_DEFAULT_IMAGE = 'icon/poster-default.svg';

  var _posterTemplate;
  var _grid;

  function init() {
    console.log('init poster');
    _posterTemplate = $('li.grid-poster.template');
    _grid = $("ul.grid");
    _grid.click(clickHandler);
  }

  function clickHandler(e) {
    let target = $(e.target);
    let listItem = target.closest("li");

    if (listItem.hasClass('grid-poster')) {
      console.log('poster: grid poster clickHandler called');

      if (listItem.hasClass('selected')) {
        console.log('poster: deselect movie');
        listItem.removeClass('selected');
        movieDb.posterDetail.hideDetail();
      } else {
        console.log('poster: select movie');
        deselect();
        movieDb.posterDetail.hideDetail();

        movieDb.app.getMovieDetails(listItem, function(listItem, movie) {
          movieDb.posterDetail.showDetail(listItem, movie);
        });
      }
    }
  }

  function create(movie) {
    let index = '';

    if(movie.locator && movie.locator.type === movieDb.config.binderKey) {
      index = movie.locator.binder + movie.locator.page;
    } else if(movie.locator && movie.locator.type === movieDb.config.providerKey) {
      index = movie.locator.provider;
    }

    var poster = _posterTemplate.clone().removeClass('template');

    let imageUrl;
    if(movieDb.config.isTmdbData) {
      // TMDB query movies
      if(movie.poster_path) {
        imageUrl = movieDb.shared.toPosterUrl(movieDb.shared.POSTER_154, movie.poster_path);
      } else {
        imageUrl = 'poster/use-default-image.jpg';
      }
    } else {
      // stored movie
      imageUrl = 'poster' + movie.poster_path;
    }

    $('img', poster).attr('src', imageUrl);
    $('.index-preview', poster).html(index.replace(/(.)/gi, '$1\&#8203;'));
    $('.title', poster).text(movie.title);
    $(poster).data('movie', movie);

    return poster;
  }

  function deselect() {
    $('li.selected').removeClass('selected');
  }

  return {
    init: init,
    create: create,
    deselect: deselect
  };
})();
