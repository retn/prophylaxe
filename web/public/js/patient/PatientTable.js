$(document).ready(function() {
    var pt = new PatientTable();

//    $('#btnTest').tooltip({'placement':'right'});
    $('#testi a').tooltip(
            {
                'html': true
            });

});

function PatientTable() {


    var initialize = function() {
        $("#patient_edit_table").dataTable({
            "bProcessing": true,
            "sAjaxSource": '/patient/getpatients/format/json',
            "oLanguage": {
                "sUrl": "/js/german.txt"
            },
//            "bJQueryUI": true,
            "aoColumns": [
                {"mData": 0},
                {"mData": 1},
                {"mData": 2},
                {"mData": 3},
                {"mData": 4},
                {"mData": null,
                    "bSortable": false,
                    "mRender": function(data) {
//                        var button = '<a><button type="button" class="btn btn-primary"><span class="glyphicon glyphicon-edit"></span></button></a>';

                        return data;
                    },
                    "fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
//                        var a = $(nTd).find("a");
                        var tooltip = $('<a class="top" title="" data-placement="top" data-toggle="tooltip" href="#">Fehler anzeigen</a>');
                        var tooltip = $('<button type="button" class="btn btn-danger btnStatus" data-toggle="tooltip" data-placement="top">Fehler anzeigen</button>');

                        var row = $(nTd);
                        var errors = oData[6];
                        var tokenUsed = parseInt(oData[7]);

                        // Daten wurden per App vom Patienten abgeholt
                        if (tokenUsed === 1) {
                            tooltip.toggleClass('btn-danger');
                            tooltip.toggleClass('btn-success');
                            tooltip.text('Daten empfangen');
                            row.append(tooltip);
                        } else if (errors.length > 0) {
//                            row.css('background-color', '#D43F3A');
                            var list = $('<ul>');
                            for (var i = 0; i < errors.length; i++) {

                                var listEntry = $('<li>');
                                listEntry.text(errors[i]);
                                list.append(listEntry);

                            }


                            tooltip.tooltip(
                                    {
                                        'html': true,
                                        'title': list.prop('outerHTML'),
                                        'trigger': 'hover'
                                    });
                            row.append(tooltip);
                        } else {
                            tooltip.toggleClass('btn-danger');
                            tooltip.toggleClass('btn-primary');
                            tooltip.text('Daten vollständig');
                            row.append(tooltip);
                        }
                        //a.attr("href", "/patient/edit/" + oData[0]);
                    }},
                {// Spalte bearbeiten
                    "mData": null,
                    "sClass": "patient_edit_table_column",
                    "bSortable": false,
                    "mRender": function(data) {
                        var button = '<a><button type="button" class="btn btn-primary"><span class="glyphicon glyphicon-edit"></span></button></a>';
                        return button;
                    },
                    "fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
                        var a = $(nTd).find("a");

                        a.attr("href", "/patient/edit/" + oData[0]);
                    }
                },
                {// Spalte Notfallkoffer
                    "mData": null,
                    "sClass": "patient_edit_table_column",
                    "bSortable": false,
                    "mRender": function(data) {
                        var button = '<a><button type="button" class="btn btn-primary"><span class="glyphicon glyphicon-edit"></span></button></a>';
                        return button;
                    },
                    "fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
                        var a = $(nTd).find("a");
                        a.attr("href", "/emergency-case/edit/" + oData[0]);
                    }

                },
                {// Spalte Sprueche
                    "mData": null,
                    "sClass": "patient_edit_table_column",
                    "bSortable": false,
                    "mRender": function(data) {
                        var button = '<a><button type="button" class="btn btn-primary"><span class="glyphicon glyphicon-edit"></span></button></a>';
                        return button;
                    },
                    "fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
                        var a = $(nTd).find("a");
                        a.attr("href", "/patient/edit-maxim/" + oData[0]);
                    }
                },
                {// Spalte Ablenkung
                    "mData": null,
                    "sClass": "patient_edit_table_column",
                    "bSortable": false,
                    "mRender": function(data) {
                        var button = '<a><button type="button" class="btn btn-primary"><span class="glyphicon glyphicon-edit"></span></button></a>';
                        return button;
                    },
                    "fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
                        var a = $(nTd).find("a");
                        a.attr("href", "/patient/edit-distraction/" + oData[0]);
                    }
                }
            ]

        });

    };


    this.editButtonClick = function() {
        alert($(this).data("id"));

    }

    initialize();

}