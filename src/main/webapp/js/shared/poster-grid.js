/* --------------------------------------------
 *
 * -------------------------------------------- */
movieDb.posterGrid = (function() {

  var _grid;

  function init() {
    console.log('init posterGrid');
    _grid = $("ul.grid");
    $('.scrollable').scroll(renderOnScroll);
  }

  function render(doAppend) {
    var moviesToRender = movieDb.app.moviesToRender;

    if(!doAppend) {
      _grid.empty();
    }

    while(moviesToRender.length > 0) {
      var movie = moviesToRender.splice(0,1)[0]; // remove first one
      var poster = movieDb.poster.create(movie);
      _grid.append(poster);

      var viewportBottom = $(window).scrollTop() + $(window).height();
      var topOfLastEntry = $('li:last-child', _grid).offset().top;
      if(viewportBottom < topOfLastEntry - movieDb.config.RENDER_MORE_HEIGHT) {
        break;
      }
    }
    console.log('posterGrid: %d movies remaining to render', moviesToRender.length);
  }

  function renderOnScroll() {
    var viewportBottom = $(window).scrollTop() + $(window).height();
    var topOfLastEntry = $('li:last-child', _grid).offset().top;
    if(viewportBottom > topOfLastEntry - movieDb.config.RENDER_MORE_HEIGHT) {
      render(true);
    }
  }

  return {
    init: init,
    render: render
  };
})();
