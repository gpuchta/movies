/* --------------------------------------------
 *
 * -------------------------------------------- */
movieDb.shared = (function() {

  var baseUrls = {};

  const PREFIX_LITERAL  = 'l';
  const PREFIX_GENRE    = 'g';
  const PREFIX_ACTOR    = 'a';
  const PREFIX_DIRECTOR = 'd';
  const PREFIX_YEAR     = 'y';
  const PREFIX_INDEX    = 'i';
  const PREFIX_TMDBID   = 't';

  var prefixToTermMapping = {};
  prefixToTermMapping[PREFIX_LITERAL]  = 'literal';
  prefixToTermMapping[PREFIX_GENRE]    = 'genre';
  prefixToTermMapping[PREFIX_ACTOR]    = 'actor';
  prefixToTermMapping[PREFIX_DIRECTOR] = 'director';
  prefixToTermMapping[PREFIX_YEAR]     = 'year';
  prefixToTermMapping[PREFIX_INDEX]    = 'index';
  prefixToTermMapping[PREFIX_TMDBID]   = 'tmdbid';


  function toLiteralTerm(text) { return 'l:' + text; }
  function toGenreTerm(text) { return 'g:' + text; }
  function toActorTerm(text) { return 'a:' + text; }
  function toDirectorTerm(text) { return 'd:' + text; }
  function toYearTerm(text) { return 'y:' + text; }
  function toIndexTerm(text) { return 'i:' + text; }
  function toTmdbIdTerm(text) { return 't:' + text; }

  function split(value, separator, limit) {
    let tailend = value.split(separator).slice(limit - 1);
    return value.split(separator).slice(0,limit - 1)
      .concat(tailend.length > 0 ? tailend.join(separator) : []);
  }

  function toTitleCase(str) {
    return str.replace(/(?:^|\s)\w/g, function(match) {
        return match.toUpperCase();
    });
  }

  function toManageUrl(tmdbId) {
    return 'manage.html#' + toTmdbIdTerm(tmdbId);
  }

  function toTheMovieDbUrl(tmdbId) {
    return movieDb.config.movieDbUrl
      .replace(/\{tmdb_id\}/, tmdbId);
  }

  function toTheMovieDbSearchUrl(title, year) {
    return movieDb.config.movieDbSearchUrl
      .replace(/\{title\}/, encodeURIComponent(title))
      .replace(/\{year\}/, year);
  }

  function categoryPrefix(value) {
    let prefix = split(value, ':', 2)[0];
    return prefix.startsWith('-') ? prefix.substring(1) : prefix;
  }

  function categoryValue(value) {
    return split(value, ':', 2)[1];
  }

  function toFilterCriteria(value) {
    if(value.length === 0) {
      return null;
    }

    let fragments = split(value, ':', 2);
    var prefix = fragments[0];
    var className = movieDb.shared.prefixToTerm(prefix);
    if(prefix.startsWith('-')) {
      className += ' negate';
    }

    return {
      className: className,
      text: fragments[1],
      term: value
    };
  }

  function infoDialog(title, message) {
    $('<div>')
    .appendTo('body')
    .html('<div><h6>' + message + '</h6></div>')
    .dialog({
      modal: true,
      title: title,
      zIndex: 10000,
      autoOpen: true,
      width: 'auto',
      resizable: false
    });
  }

  function confirmDialog(title, message, yesCallback) {
    $('<div>')
      .appendTo('body')
      .html('<div><h6>' + message + '</h6></div>')
      .dialog({
        modal: true,
        title: title,
        zIndex: 10000,
        autoOpen: true,
        width: 'auto',
        resizable: false,
        buttons: {
          Yes: function () {
            $(this).dialog("close");
            yesCallback();
          },
          No: function () {
            $(this).dialog("close");
          }
        },
        close: function (event, ui) {
          $(this).remove();
        }
      });
  };

  function init() {
    console.log('init shared');

    var tmdbBaseUrls = [];
    $.getJSON('/movies/tmdb/mediabaseurl', function(data) {
      for (var prop in data) {
        if(!data.hasOwnProperty(prop)) {
          continue;
        }
        tmdbBaseUrls[prop] = data[prop];
      }
    });

    movieDb.shared = {

      split: split,
      toTitleCase: toTitleCase,
      infoDialog: infoDialog,
      confirmDialog: confirmDialog,

      toLiteralTerm: toLiteralTerm,
      toGenreTerm: toGenreTerm,
      toActorTerm: toActorTerm,
      toDirectorTerm: toDirectorTerm,
      toYearTerm: toYearTerm,
      toIndexTerm: toIndexTerm,
      toTmdbIdTerm: toTmdbIdTerm,

      toFilterCriteria: toFilterCriteria,

      termFunctionMapping: {
        'literal': toLiteralTerm,
        'genre': toGenreTerm,
        'actor': toActorTerm,
        'director': toDirectorTerm,
        'year': toYearTerm,
        'index': toIndexTerm,
        'tmdbid': toTmdbIdTerm
      },

      prefixToTerm: function(prefix) {
        if(prefix.startsWith('-')) {
          prefix = prefix.substring(1);
        }
        var type = prefixToTermMapping[prefix];
        if(type === undefined) {
          type = prefixToTermMapping[''];
        }
        return type;
      },

      categoryPrefix: categoryPrefix,
      categoryValue: categoryValue,

      isLiteral: function(criteria) {
        return categoryPrefix(criteria) === PREFIX_LITERAL;
      },
      isGenre: function(criteria) {
        return categoryPrefix(criteria) === PREFIX_GENRE;
      },
      isActor: function(criteria) {
        return categoryPrefix(criteria) === PREFIX_ACTOR;
      },
      isDirector: function(criteria) {
        return categoryPrefix(criteria) === PREFIX_DIRECTOR;
      },
      isYear: function(criteria) {
        return categoryPrefix(criteria) === PREFIX_YEAR;
      },
      isIndex: function(criteria) {
        return categoryPrefix(criteria) === PREFIX_INDEX;
      },
      isTmdbid: function(criteria) {
        return categoryPrefix(criteria) === PREFIX_TMDBID;
      },

      toManageUrl: toManageUrl,
      toTheMovieDbUrl: toTheMovieDbUrl,
      toTheMovieDbSearchUrl: toTheMovieDbSearchUrl,

      toPosterUrl: function(size, image) {
        return tmdbBaseUrls['poster' + size] + image;
      },

      toBackdropUrl: function(size, image) {
        return tmdbBaseUrls['backdrop' + size] + image;
      },

      BACKDROP_1280     : "1280",
      BACKDROP_780      : "780",
      BACKDROP_300      : "300",
      BACKDROP_ORIGINAL : "Original",
      POSTER_780        : "780",
      POSTER_500        : "500",
      POSTER_342        : "342",
      POSTER_185        : "185",
      POSTER_154        : "154",
      POSTER_92         : "92",
      POSTER_ORIGINAL   : "Original"
    };
  }

  return {
    init: init
  };
})();
