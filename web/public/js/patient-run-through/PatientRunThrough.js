$(document).ready(function() {
    var prt = new PatientRunThrough();
});

function PatientRunThrough() {

    _next = $('.rightarrow');
    _contentContainer = $('#view-content');
    _maxim = null;

    var initialize = function() {
        _next.bind('click', function() {

            $.ajax({
                type: "POST",
                url: "/patient-run-through/maxim",
                dataType: "html",
//                data: {form: form},
                success: nextPage
            });


        });
    };


    var nextPage = function(data){
        _contentContainer.html(data);
        _maxim = new MaximTable();
    };

    initialize();

}