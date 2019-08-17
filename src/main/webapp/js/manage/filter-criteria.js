/* --------------------------------------------
 *
 * -------------------------------------------- */
movieDb.filterCriteria = (function() {

  const DATA_TERM = 'data-term';
  var _filterInput;
  var _filterCriteria;
  var _doingKeyup = false;

  function init() {
    console.log('init filterCriteria');
    _filterCriteria = $('.filter-criteria');
    _filterInput = $('.header .filter-input');
    _filterInput.keypress(onEnterKey);
    $('.header .action').click(find);
    _filterCount = $('.header .filter-count');
  }

  function create(text, className, term) {
    return $('<span>')
      .addClass(className)
      .attr(DATA_TERM, term)
      .text(text);
  }

  function onEnterKey(e) {
    if(e.which == 13) {
      find();
    }
  }

  function find() {
    let value = _filterInput.val();
    if(value.trim().length == 0) {
      return;
    }
    updateCriteria();
    movieDb.app.updateSearch(true);
  }

  function updateCriteria() {
    let value = _filterInput.val();
    console.log('filterCriteria: set criteria %s', value);

    let searchElements = /^(.+?)( *[ ,;]{1} *(\d{4}))?$/g.exec(value.trim());
    let title = searchElements[1];
    let year = searchElements[3];

    set(year ? [
      movieDb.shared.toLiteralTerm(title),
      movieDb.shared.toYearTerm(year)
    ] : [
      movieDb.shared.toLiteralTerm(title)
    ]);

    _filterInput.val('');
  }

  function set(criteria) {
    console.log('filterCriteria: update %d criteria', criteria.length);
    _filterCriteria.empty();
    $(criteria).each(function() {
      var filterCriteria = movieDb.shared.toFilterCriteria(this);
      var term = create(filterCriteria.text, filterCriteria.className, filterCriteria.term);
      _filterCriteria.append(term);
    });
  }

  function current() {
    var criteria = [];
    $('span', _filterCriteria).each(function(i, span) {
      criteria.push($(span).attr(DATA_TERM));
    });
    return criteria;
  }

  function updateCount(count) {
    console.log('filterCriteria: update count to %d', count);
    _filterCount.text(count);
  }

  return {
    init: init,
    set: set,
    current: current,
    updateCount: updateCount
  };
})();
