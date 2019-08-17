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
    _filterCriteria.click(toggleStateHandler);
    _filterCriteria.dblclick(deleteHandler);
    _filterCriteria.contextmenu(deleteHandler);
    _filterInput = $('.header .filter-input');
    _filterInput.keyup(onKeyUp);
    $('.header .action').click(clear);
    $(document).keypress(onEnterKey);
    _filterCount = $('.header .filter-count');
  }

  function toggleStateHandler(e) {
    let target = $(e.target);
    if (target.is('span')) {
      const term = target.attr(DATA_TERM);
      if(!term.startsWith('-')) {
        target.attr(DATA_TERM, '-' + term);
        target.addClass('negate');
      } else {
//        target.attr(DATA_TERM, term.substring(1));
//        target.removeClass('negate');
        target.remove();
      }
      movieDb.app.updateSearch(true);
    }
  }

  function deleteHandler(e) {
    let target = $(e.target);
    if (target.is('span')) {
      console.log('filterCriteria: remove criteria %s', target.text());
      target.remove();
      movieDb.app.updateSearch(true);
    }
    return false;
  }

  function onKeyUp() {
    if(!_doingKeyup){
      _doingKeyup=true;
      movieDb.app.updateSearch();
      _doingKeyup=false;
    }
  }

  function onEnterKey(e) {
    if(e.which == 13) {
      var value = _filterInput.val().trim();

      if(movieDb.searchIndex.isGenre(value)) {
        value = movieDb.shared.toGenreTerm(movieDb.shared.toTitleCase(value));
      } else {
        if(value.startsWith('"')) {
          value = value.substring(1);
        }
        if(value.endsWith('"')) {
          value = value.substring(0, value.length - 1);
        }
        value = movieDb.shared.toLiteralTerm(value);
      }

      var filterCriteria = movieDb.shared.toFilterCriteria(value);

      var termNode = create(filterCriteria.text, filterCriteria.className, filterCriteria.term);
      _filterCriteria.append(termNode);
      _filterInput.val('');
      movieDb.app.updateSearch(true);
    }
  }

  function create(text, className, term) {
    return $('<span>')
      .addClass(className)
      .attr(DATA_TERM, term)
      .text(text);
  }

  function add(target) {
    var text = target.text();
    var className = target.attr('class');
    var term = movieDb.shared.termFunctionMapping[className](text);
    var filterCriteria = movieDb.shared.toFilterCriteria(term);
    var term = create(filterCriteria.text, filterCriteria.className, filterCriteria.term);
    console.log('filterCriteria: add criteria %s', text);
    _filterCriteria.append(term);
    movieDb.browserHash.update(current());
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

    var input = _filterInput.val().trim();
    if(input.length > 0) {
      if(input.startsWith('"')) {
        input = input.substring(1);
      }
      if(input.endsWith('"')) {
        input = input.substring(0, input.length - 1);
      }
      criteria.push(movieDb.shared.toLiteralTerm(input));
    }
    return criteria;
  }

  function clear() {
    let value = _filterInput.val();
    if(value.trim().length > 0) {
      _filterInput.val('');
    }
    else {
      _filterCriteria.empty();
    }
    movieDb.app.updateSearch(true);
  }

  function updateCount(count) {
    console.log('filterCriteria: update count to %d', count);
    _filterCount.text(count);
  }

  return {
    init: init,
    set: set,
    add: add,
    current: current,
    updateCount: updateCount
  };
})();
