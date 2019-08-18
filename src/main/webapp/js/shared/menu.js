/* --------------------------------------------
 *
 * -------------------------------------------- */
movieDb.menu = (function() {

  function show() {
     $("#side-nav").show();
  }

  function hide() {
    $("#side-nav").hide();
  }

  function init() {
    console.log('init menu');

    var sideNav = $('<div>').attr('id', 'side-nav').addClass('side-nav')
      .append($('<a>').attr('href', 'javascript:void(0)')
          .addClass('close-btn')
          .click(hide)
          .html('&times;'))
      .append($('<a>').attr('href', 'index.html').text('Browse'))
      .append($('<a>').attr('href', 'manage.html').text('Edit'))
      .append($('<a>').attr('href', 'settings.html').text('Settings'))
      .append($('<a>').attr('href', 'about.html').text('About'));

    $('body').append(sideNav).click(function() {
      console.log('clicked outside menu box');
      hide();
    });

    sideNav.click(function(event) {
      event.stopPropagation();
      console.log('clicked inside menu box');
    });

    $('.settings-icon').click(function(event) {
      event.stopPropagation();
      console.log('clicked menu icon');
      show();
    });
  }

  return {
    init: init
  };
})();
