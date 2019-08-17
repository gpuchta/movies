/* --------------------------------------------
 *
 * -------------------------------------------- */
movieDb.app = (function() {

  const _databaseFolder = $('.database-folder');
  const _tmdbAuthToken = $('.tmdb-auth-token');

  const _buttonSave = $('.button.save');
  const _buttonBackup = $('.button.backup');

  const _inputRestore = $('.input.restore');
  const _buttonChoose = $('.button.choose');
  const _buttonRestore = $('.button.restore');
  const _fileRestore = $('[name=file-content]');
  const _formRestore = _buttonRestore.closest('form');

  const _logList = $('.progress-log ul');


  function init() {
    console.log('init app-settings');

    // save
    movieDb.serverApi.getSettings(function(databaseFolder, tmdbApiKey) {
      _databaseFolder.val(databaseFolder);
      _tmdbAuthToken.val(tmdbApiKey);

      _buttonSave.click(safeSettings);
    });

    // backup
    _buttonBackup.click(function(event) {
      console.log('backup');
      movieDb.serverApi.backupData();
    });

    // restore
    _fileRestore.change(function(event) {
      _inputRestore.val(_fileRestore.val());
    });

    _buttonChoose.click(function(event) {
      console.log(this);
      _fileRestore.click();
    });

    _buttonRestore.click(function(event) {
      _formRestore.trigger('submit');
    });

    _formRestore.submit(function() {
      movieDb.serverApi.restoreData(this,
          createLogger(_logList));
      return false;
    });
  }

  function createLogger(container) {
    var _container = container;

    function clear() {
      _container.empty();
    }

    function log(text) {
      _container.append($('<li>').text(text));
      _container.get(0).scrollTop = _container.get(0).scrollHeight;
    }

    clear();

    return {
      clear: clear,
      log: log
    }
  }

  function safeSettings() {
    movieDb.serverApi.persistSettings(
        _databaseFolder.val(),
        _tmdbAuthToken.val());
  }

  return {
    init: init
  };
})();

/* --------------------------------------------
 *
 * -------------------------------------------- */
$(function() {
 movieDb.config.init();

 for(var component in movieDb) {
   var func = movieDb[component].init;
   if ($.isFunction(func)) {
     func();
   }
 }
});
