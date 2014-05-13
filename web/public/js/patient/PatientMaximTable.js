/*
 * Verwaltet die beiden Datatables 
 * Funktionalität für das Zuordnen von Spruechen zum gewaehlten Patienten
 * 
 */
$(document).ready(function() {
    var pmt = new PatientMaximTable();

});

function PatientMaximTable() {

    var _row = $('<tr><td id="maxim_id"> </td><td id="maxim_text"> </td><td><button name="btnRemove" type="button" class="btn btn-warning"><span class="glyphicon glyphicon-minus"></span></button></td></tr>');
    var _maximsOfPatient = $('#patient_has_maxims');
    var _btnSubmit = $('#btnSubmit');
    var datatable_maxim = $("#patient_maxim_table");
    var datatable_maxim_has_patient = $("#patient_has_maxims");

    var initialize = function() {

        _maximsOfPatient.find("[name='btnRemove']").bind('click', btnRemoveClick);
        _row.find('button').bind('click', btnRemoveClick);
        _btnSubmit.bind('click', btnSubmitClick);

        datatable_maxim_has_patient.dataTable();

        datatable_maxim.dataTable({
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
                        button.bind('click', function(iRow) {
                            buttonAddClick($(this));

                        });


                    }
                }
            ]

        });

    };

    var buttonAddClick = function(clickedElement) {
        var row = clickedElement.parent();
        while (!row.is('tr')) {
            row = row.parent();
        }

        // Ausgewaehlte Daten der Zeile holen
        var data = datatable_maxim.fnGetData(row.get(0));

        // id auslesen
        var maximid = data[0];
        // pruefen ob bereits ein Eintrag mit der ID existiert
        if (jQuery("[maximid='" + maximid + "']").length > 0) {
            return;
        }

        // Button in Array hinzufuegen
        var btn = '<button name="btnRemove" type="button" maximid="' + maximid + '"class="btn btn-warning"><span class="glyphicon glyphicon-minus"></span></button>';
        data.push(btn);

        // Zeile hinzufuegen
        datatable_maxim_has_patient.dataTable().fnAddData(data);
//        $('#' + maximid).bind('click', btnRemoveClick);
        $("[maximid='"+maximid+"']").bind('click', btnRemoveClick);
//        index = datatable_maxim_has_patient.dataTable().fnGetPosition()
//        index++; // Wegen HEader Zeile der Tabelle
//        console.log("new line at " + index);


        // maximid in der neu hinzugefuegten Zeile setzen
//        var rows = datatable_maxim_has_patient.find('tr');
//        $(rows[index]).attr('maximid', maximid);

    };


    var btnRemoveClick = function() {
        var target_row = $(this).closest("tr").get(0); // this line did the trick
        var aPos = datatable_maxim_has_patient.fnGetPosition(target_row);
        datatable_maxim_has_patient.fnDeleteRow(aPos);
    };

    var btnSubmitClick = function() {

        var maxim_ids = new Array();

        _maximsOfPatient.find("tbody [name='btnRemove']").each(function(index, value) {
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