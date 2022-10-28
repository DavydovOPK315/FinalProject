$('.all_items').slice(0, 3).show();

$('#btnMore').on('click', function () {
    let items = $('.all_items:hidden');

    items.slice(0, 3).slideDown();
    if (items.length === 0) {
        $('#btnMore').fadeOut();
    }
});