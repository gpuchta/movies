/* --------------------------------------------
 *
 * -------------------------------------------- */
movieDb.serverApi = (function() {

  function init() {
    console.log('init serverApi');
  }

  function showSpinner() {
    hideSpinner();
    console.log('show spinner');
    $('body').append($('<div>').addClass('loading').html('Loading&#8230;'));
  }

  function hideSpinner() {
    console.log('hide spinner');
    $('.loading').remove();
  }

  function getAllMovies(callback) {
    $.ajax({
      dataType: 'json',
      url: '/movies/all',
      beforeSend: function() {
        showSpinner();
      },
      complete: function() {
        hideSpinner();
      },
      success: function(movies) {
        callback(movies);
      }
    });
  }

  function searchTmdbMovie(criteria, callback) {
    let title;
    let year;
    let tmdbid;

    for (var i=0; i<criteria.length; i++) {
      if (movieDb.shared.isLiteral(criteria[i])) {
        title = movieDb.shared.categoryValue(criteria[i]);
      }
      else if (movieDb.shared.isYear(criteria[i])) {
        year = movieDb.shared.categoryValue(criteria[i]);
      }
      else if (movieDb.shared.isTmdbid(criteria[i])) {
        tmdbid = movieDb.shared.categoryValue(criteria[i]);
      }
    }

    var searchUrl;
    if (title) {
      searchUrl = '/movies/tmdb/search?title=' + title;
      if(year) {
        searchUrl += '&year=' + year;
      }
    }
    else if (tmdbid) {
      searchUrl = '/movies/tmdb/detail/' + tmdbid;
    }

    let movies = [];
    $.ajax({
      url: searchUrl,
      type: "GET",
      dataType: "json",
      beforeSend: function() {
        showSpinner();
      },
      complete: function() {
        hideSpinner();
      },
      success: function(data) {
        if (!Array.isArray(data)) {
          // tmdbid query returns single movie
          data = [ data ];
        }
        for (var i=0; i<data.length; i++) {
          var movie = data[i];
          movies.push(movie);
        }
        callback(movies);
      }

    });
  }

  function getTmdbMovieDetails(movie, callback) {
    $.ajax({
      dataType: 'json',
      url: '/movies/tmdb/detail/' + movie.tmdb_id,
      beforeSend: function() {
        showSpinner();
      },
      complete: function() {
        hideSpinner();
      },
      success: function(movie) {
        callback(movie);
      }
    });
  }

  function persistMovie(movie, item) {
    var imageUrl = $('img', item).attr('src');
    movie.poster_path = imageUrl.substring(imageUrl.lastIndexOf('/'));
    $.ajax({
      type: 'PUT',
      url: '/movies/store',
      data: JSON.stringify(movie),
      dataType: 'json',
      beforeSend: function() {
        showSpinner();
      },
      complete: function() {
        hideSpinner();
      },
      success: function(data) {
        console.log('done storing: %s', JSON.stringify(movie));
      },
      error: function(err) {
        console.log('failed to store movie: %o', err);
      }
    });
  }

  function deleteMovie(movie, callback) {
    $.ajax({
      type: 'DELETE',
      url: '/movies/delete',
      data: JSON.stringify(movie),
      beforeSend: function() {
        showSpinner();
      },
      complete: function() {
        hideSpinner();
      },
      success: callback,
      error: function(err) {
        console.log('failed to delete movie: %o', err);
      }
    });
  }

  function getSettings(callback) {
    $.ajax({
      dataType: 'json',
      url: '/movies/settings',
      beforeSend: function() {
        showSpinner();
      },
      complete: function() {
        hideSpinner();
      },
      success: function(settings) {
        callback(
            settings.environment.data_folder,
            settings.settings.tmdb_api_key);
      }
    });
  }

  function persistSettings(databaseFolder, tmdbApiKey) {
    let settings = {
        environment: {
          data_folder: databaseFolder
        },
        settings: {
          tmdb_api_key: tmdbApiKey
        }
    };

    $.ajax({
      type: 'PUT',
      url: '/movies/settings',
      data: JSON.stringify(settings),
      dataType: 'json',
      beforeSend: function() {
        showSpinner();
      },
      complete: function() {
        hideSpinner();
      },
      success: function(data) {
        console.log('done storing: %s', JSON.stringify(settings));
      }
    });
  }

  function backupData() {
    let iframe = $('#backup');
    if (!iframe.length) {
      iframe = $('<iframe>').attr('id', 'backup').hide();
      $('body').append(iframe);
    }
    iframe.removeAttr('src');

    console.log('download ...');
    iframe.attr('src', '/movies/backup');
  }

  function restoreData(form, logger) {
    let fileInput = $('input[type=file]', form);

    if (fileInput.val() === "") {
      console.log('nothing to upload');
      return;
    }

    var formData = new FormData(form);

    var lastLineNum = 0;
    $.ajax({
      url: '/movies/restore',
      type: 'POST',
      data: formData,
      beforeSend: function() {
        showSpinner();
      },
      complete: function() {
        hideSpinner();
      },
      xhrFields: {
        onprogress: function(e) {
          var lines = e.currentTarget.response.trim().split('\n');
          var linesToAdd = lines.slice(lastLineNum, lines.length);
          lastLineNum = lines.length;

          for(index in linesToAdd) {
            logger.log(linesToAdd[index]);
          }
        }
      },
      success: function (data) {
        console.log('upload successful');
      },
      cache: false,
      contentType: false,
      processData: false
    });
  }

  return {
    init: init,
    getAllMovies: getAllMovies,
    searchTmdbMovie: searchTmdbMovie,
    getTmdbMovieDetails: getTmdbMovieDetails,
    persistMovie: persistMovie,
    deleteMovie: deleteMovie,
    getSettings: getSettings,
    persistSettings: persistSettings,
    backupData: backupData,
    restoreData: restoreData
  };
})();
