$('.all_items').slice(0,3).show();

$('#btnMore').on('click', function() {
  $('.all_items:hidden').slice(0,3).slideDown();
  if($('.all_items:hidden').length === 0) {
    $('#btnMore').fadeOut();
  }
});