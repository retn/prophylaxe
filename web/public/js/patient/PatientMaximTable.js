$(document).ready(function() {
    var pmt = new PatientMaximTable();

});

function PatientMaximTable() {

    var _row = $('<tr><td id="maxim_id"> </td><td id="maxim_text"> </td><td><button name="btnRemove" type="button" class="btn btn-warning"><span class="glyphicon glyphicon-minus"></span></button></td></tr>');
    var _maximsOfPatient = $('#patient_has_maxims');
    var _btnSubmit = $('#btnSubmit');


    var initialize = function() {

        _maximsOfPatient.find("[name='btnRemove']").bind('click', btnRemoveClick);
        _row.find('button').bind('click', btnRemoveClick);
        _btnSubmit.bind('click', btnSubmitClick);


        $("#patient_maxim_table").dataTable({
            "bProcessing": true,
            'iDisplayLength': 20,
            "aoColumns": [
                {"mData": 0},
                {"mData": 1},
                {// Spalte hinzufuegen +
                    "mData": null,
                    "mRender": function(data) {
                        var button = '<button type="button" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span></button>';
                        return button;
                    },
                    "fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
                        var button = $(nTd).find("button");
                        button.bind('click', function() {
                            buttonAddClick($(this));
                        });


                    }
                }
            ]

        });

    };

    var buttonAddClick = function(clickedElement) {
        var currentRow = clickedElement.parent();
        while (!currentRow.is('tr')) {
            currentRow = currentRow.parent();
        }


        var columns = currentRow.find('td');
        var id = $(columns[0]).text().trim();
        var text = $(columns[1]).text().trim();

        if (!isIn(id)) {
            var row = _row.clone(true);

            row.attr('maximid', id);
            row.find('#maxim_id').text(id);
            row.find('#maxim_text').text(text);
            _maximsOfPatient.append(row);
        }

    };

    var isIn = function(id) {
        rows = _maximsOfPatient.find('tr');
        for (var i = 0; i < rows.length; i++) {
            if ($(rows[i]).attr('maximid') === id) {
                return true;
            }
        }
        return false;
    };

    var btnRemoveClick = function() {

        var currentRow = $(this).parent();
        while (!currentRow.is('tr')) {
            currentRow = currentRow.parent();
        }
        currentRow.remove();
    };

    var btnSubmitClick = function() {

        var maxim_ids = new Array();

        _maximsOfPatient.find('tbody tr').each(function(index,value) {
            maxim_ids.push($(value).attr('maximid'));
        });

        $.ajax({
            type: "post",
            url: '/patient/save-maxim/format/json',
            async: false,
            data: {maxim_ids: maxim_ids},
            success: function(data) {
                window.location.href = data.redirect;

            }
        });

    };

    initialize();
}