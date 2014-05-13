$(document).ready(function() {
    var pdt = new PatientDistractionTable();

});

function PatientDistractionTable() {

    var _row = $('<tr><td id="distraction_id"> </td><td id="distraction_text"></td><td id="emotion_text"></td><td><button name="btnRemove" type="button" class="btn btn-warning"><span class="glyphicon glyphicon-minus"></span></button></td></tr>');
    var _distractionsOfPatient = $('#patient_has_distractions');
    var _btnSubmit = $('#btnSubmit');
    var datatable_distraction = $("#patient_distraction_table");
    var datatable_distraction_has_patient = $("#patient_has_distractions");


    var initialize = function() {

        _distractionsOfPatient.find("[name='btnRemove']").bind('click', btnRemoveClick);
        _row.find('button').bind('click', btnRemoveClick);
        _btnSubmit.bind('click', btnSubmitClick);

        datatable_distraction_has_patient.dataTable();

        datatable_distraction.dataTable({
            "bProcessing": true,
            'iDisplayLength': 20,
            "aoColumns": [
                {"mData": 0},
                {"mData": 1},
                {"mData": 2},
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
//        var currentRow = clickedElement.parent();
//        while (!currentRow.is('tr')) {
//            currentRow = currentRow.parent();
//        }
//
//
//        var columns = currentRow.find('td');
//        var id = $(columns[0]).text().trim();
//        var text = $(columns[1]).text().trim();
//        var emotion = $(columns[2]).text().trim();
//
//        if (!isIn(id)) {
//            var row = _row.clone(true);
//
//            row.attr('distractionid', id);
//            row.find('#distraction_id').text(id);
//            row.find('#distraction_text').text(text);
//            row.find('#emotion_text').text(emotion);
//            _distractionsOfPatient.append(row);
//        }

        var row = clickedElement.parent();
        while (!row.is('tr')) {
            row = row.parent();
        }

        // Ausgewaehlte Daten der Zeile holen
        var data = datatable_distraction.fnGetData(row.get(0));

        // id auslesen
        var distractionid = data[0];
        // pruefen ob bereits ein Eintrag mit der ID existiert
        if (jQuery("[distractionid='" + distractionid + "']").length > 0) {
            return;
        }

        // Button in Array hinzufuegen
        var btn = '<button name="btnRemove" type="button" distractionid="' + distractionid + '"class="btn btn-warning"><span class="glyphicon glyphicon-minus"></span></button>';
        data.push(btn);

        // Zeile hinzufuegen
        datatable_distraction_has_patient.dataTable().fnAddData(data);
        $("[distractionid='"+distractionid+"']").bind('click', btnRemoveClick);
    };


    var btnRemoveClick = function() {
        var target_row = $(this).closest("tr").get(0); // this line did the trick
        var aPos = datatable_distraction_has_patient.fnGetPosition(target_row);
        datatable_distraction_has_patient.fnDeleteRow(aPos);
    };

    var btnSubmitClick = function() {

        var distraction_ids = new Array();

        _distractionsOfPatient.find("tbody [name='btnRemove']").each(function(index, value) {
            distraction_ids.push($(value).attr('distractionid'));
        });

        $.ajax({
            type: "post",
            url: '/patient/save-distraction/format/json',
            async: false,
            data: {distraction_ids: distraction_ids},
            success: function(data) {
                window.location.href = data.redirect;

            }
        });

    };

    initialize();
}