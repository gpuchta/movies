/* --------------------------------------------
 *
 * -------------------------------------------- */
movieDb.app = (function() {

  let _databaseFolder = $('.database-folder');
  let _tmdbAuthToken = $('.tmdb-auth-token');

  let _buttonSave = $('.button.save');
  let _buttonBackup = $('.button.backup');

  let _inputRestore = $('.input.restore');
  let _buttonChoose = $('.button.choose');
  let _buttonRestore = $('.button.restore');
  let _fileRestore = $('[name=file-content]');
  let _formRestore = _buttonRestore.closest('form');

  function init() {
    console.log('init app-about');
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
