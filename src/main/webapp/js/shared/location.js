/* --------------------------------------------
 *
 * -------------------------------------------- */
movieDb.location = (function() {

  const BINDER = 'BINDER';
  const PROVIDER = 'PROVIDER';

  function init() {
    console.log('init index');
  }

  function createSelector(detailListItem) {
    let indexEditIcon = $('.index-edit', detailListItem);
    let indexNode = $('.index', detailListItem);

    let select = $('<select>').addClass('index-select').attr('size', 1).change(function(e) {
      let option = $("option:selected", select);
      if(option.attr('type') == 'binder') {
        movieDb.posterDetail.updateBinderIndex(
            detailListItem,
            option.attr('binder'),
            option.attr('page'));
      }
      else {
        movieDb.posterDetail.updateProviderIndex(
            detailListItem,
            option.attr('provider'));
      }

      select.remove();
      indexEditIcon.show();
      indexNode.show();
    })

    select.append($('<option>')
        .attr('type', 'select')
        .text('Select ...'));

    select.append($('<option>')
        .attr('type', 'provider')
        .attr('provider', 'Amazon')
        .text('Amazon'));

    select.append($('<option>')
        .attr('type', 'provider')
        .attr('provider', 'Google')
        .text('Google'));

    for(var b=0; b<5; b++) {
      for(var p=1; p<=130; p++) {
        let binder = String.fromCharCode('A'.charCodeAt(0) + b);
        let page = p;
        let index = binder + page;
        select.append($('<option>')
            .attr('type', 'binder')
            .attr('binder', binder)
            .attr('page', page)
            .text(index));
      }
    }

    return select;
  }

  function binder(binder, page) {
    return {
      type: BINDER,
      binder: binder,
      page: page,
      streaming: false
    };
  }

  function provider(provider) {
    return {
      type: PROVIDER,
      provider: provider,
      streaming: true
    };
  }

  return {
    init: init,
    createSelector: createSelector,
    binder: binder,
    provider: provider
  };
})();
