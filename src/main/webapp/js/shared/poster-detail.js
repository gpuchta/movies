/* --------------------------------------------
 *
 * -------------------------------------------- */
movieDb.posterDetail = (function() {

  var _scrollable;
  var _detailTemplate;
  var _grid;
  var _detailPosterTemplate;

  function init() {
    console.log('init posterDetail');
    _scrollable = $('.scrollable');
    _detailTemplate = $('li.detail.template').clone().removeClass('template');
    _grid = $("ul.grid");
    _grid.click(movieDb.app.handlePosterDetailClick);
    _detailPosterTemplate = $('li.detail-poster.template');
  }

  function showIndexSelector(listItem) {
    console.log('posterDetail: show index selector');
    let indexEditIcon = $('.index-edit', listItem);
    let indexNode = $('.index', listItem);
    let select = movieDb.location.createSelector(listItem);
    indexEditIcon.hide();
    indexNode.hide();
    indexNode.after(select);
  }

  function updateBinderIndex(listItem, binder, page) {
    let movie = $('.grid-poster.selected').data('movie');
    movie.locator = movieDb.location.binder(binder, page);
    $('.index', listItem)
      .empty()
      .append('Binder ')
      .append($('<span>').addClass('binder').text(binder))
      .append(' Page ')
      .append($('<span>').addClass('page').text(page));
  }

  function updateProviderIndex(listItem, provider) {
    let movie = $('.grid-poster.selected').data('movie');
    movie.locator = movieDb.location.provider(provider);
    $('.index', listItem)
      .empty()
      .append($('<span>').addClass('provider').text(provider));
  }

  function createDetail(movie) {
    var listItem = _detailTemplate.clone().removeClass('template');
    listItem.attr('tmdb-id', movie.tmdb_id);
    listItem.attr('movie', movie.tmdb_id);
    $('.title', listItem).text(movie.title);
    $('.year', listItem).text(movie.year);

    $('.tmdb-link', listItem).attr('href',
        movieDb.shared.toTheMovieDbUrl(movie.tmdb_id));

    $('.tmdb-detail-lookup', listItem)
      .attr('href', movieDb.shared.toManageUrl(movie.tmdb_id))
      .attr('target', '_blank');

    $('.overview', listItem).text(movie.overview);

    // genre
    var genreNodes = [];
    for(var n in movie.genres) {
      genreNodes.push($('<span>').addClass('genre').text(movie.genres[n]));
      genreNodes.push(" "); // so that it can wrap
    }
    $('.genres', listItem)
      .empty()
      .append(genreNodes);

    // directors
    var directorNodes = [];
    for(var n in movie.directors) {
      directorNodes.push($('<span>').addClass('director').text(movie.directors[n]));
      directorNodes.push(" "); // so that it can wrap
    }
    $('.directors', listItem)
      .empty()
      .append(directorNodes);

   // actors
    var actorNodes = [];
    for(var n in movie.actors) {
      actorNodes.push($('<span>').addClass('actor').text(movie.actors[n]));
      actorNodes.push(" "); // so that it can wrap
    }
    $('.actors', listItem)
      .empty()
      .append(actorNodes);

    // keywords
    if(movie.keywords) {
      $('.keywords-input', listItem)
        .val(movie.keywords.join(', '));
    }

    // index
    if(movie.locator) {
      if(movie.locator.type === movieDb.config.binderKey) {
        updateBinderIndex(listItem, movie.locator.binder, movie.locator.page);
      }
      else if(movie.locator.type === movieDb.config.providerKey) {
        updateProviderIndex(listItem, movie.locator.provider);
      }
    }

    if (movie.posters) {
      console.log('found %d posters', movie.posters.length);
      let posterList = $('<ul>');

      for(var n in movie.posters) {
        var detailPoster = _detailPosterTemplate
          .clone()
          .removeClass('template')
          .attr('poster_path', movie.posters[n]);

        var posterUrl = movieDb.shared.toPosterUrl(
            movieDb.shared.POSTER_154, movie.posters[n]);

        $('img', detailPoster).attr('src', posterUrl);
        posterList.append(detailPoster);
      }
      $('.posters', listItem).append(posterList);
    }

    return listItem;
  }

  function showDetail(listItem, movie) {
    hideDetail();
    listItem.addClass('selected');

    // create and insert panel
    var detailListItem = createDetail(movie);
    let selectedIndex = listItem.index();
    let rowSize = Math.round($('ul.grid').width() / $('ul.grid>li').width());
    let rowIndex = Math.floor(selectedIndex/rowSize);
    let insertIndex = Math.min((rowIndex + 1) * rowSize, _grid.children().length);

    if(!movie.locator) {
      movieDb.posterDetail.showIndexSelector(detailListItem);
    }

    $('li:nth-child(' + insertIndex + ')', _grid).after(detailListItem);
    detailListItem.show();

    // scroll to align card at the top
    let toScrollTop = $('.header').height()
                    + listItem.offset().top
                    - $('ul.grid').offset().top
                    - _scrollable.offset().top
                    + 20;

    _scrollable.animate({scrollTop: toScrollTop }, 1000);
  }

  function hideDetail() {
    $('li.detail').remove();
  }

  return {
    init: init,
    showDetail: showDetail,
    hideDetail: hideDetail,
    showIndexSelector: showIndexSelector,
    updateBinderIndex: updateBinderIndex,
    updateProviderIndex: updateProviderIndex
  };
})();
