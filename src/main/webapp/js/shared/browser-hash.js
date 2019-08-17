/* --------------------------------------------
 *
 * -------------------------------------------- */
movieDb.browserHash = (function() {

  var _filterCriteria;

  function init() {
    console.log('init browserHash');
    _filterCriteria = $('.filter-criteria');
    window.addEventListener("popstate", updateSelection);
  }

  function filterEmptyString(n) {
    return n.trim().length == 0 ? undefined : n;
  }

  function updateSelection(event) {
    console.log('browserHash: update selected based on hash entries: %o', event);
    var criteria = [];
    if(event.state) {
      event.state.criteria.split(/\+/).filter(filterEmptyString).map(function(value) {
        criteria.push(decodeURIComponent(value));
      });
    }
    movieDb.filterCriteria.set(criteria);
    movieDb.app.updateSearch(false);
  }

  function update(criteria) {
    let encodedTerms = criteria.reduce(function(prevVal, elem) {
      return prevVal + (prevVal.length == 0 ? '' : '+') + encodeURIComponent(elem);
    }, '');

    if(encodedTerms !== window.location.hash) {
      history.pushState({ criteria: encodedTerms }, "", '#' + encodedTerms);
    }
  }

  function toCriteria() {
    var hashEntries = window.location.hash.substr(1).split(/\+/).filter(filterEmptyString);

    if (hashEntries.length > 0) {
      return hashEntries.map(function(value) {
        return decodeURIComponent(value);
      });
    }
    return [];
  }

  return {
    init: init,
    update: update,
    toCriteria: toCriteria
  };
})();
