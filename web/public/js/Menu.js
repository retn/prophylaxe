$(document).ready(function() {
    var menu = new Menu();

});

function Menu() {
    var _ul;
    
//    $(document).mouseup(function(e) {
//        // If the dropdown is visible
//        // and the thing we've clicked is not a descendent of the dropdown
//        if ($('.dropdown').is(":visible") && $(e.target).parents('.dropdown').length == 0) {
////            $('.dropdown').hide();
//            if (_ul !== undefined) {
//                toggle();
//            }
//        }
//    });

    $('.dropdown').bind('click', function() {
        _ul = $(this).find('ul');
        toggle();
    });



    var toggle = function($element) {
        _ul.toggleClass('hover_ul dropdown');

        _ul.find('li').each(function() {
            $(this).toggleClass('hover_li');
        });
    };

}


