$(document).ready(function() {
    var pt = new PatientTable();

});

function PatientTable() {


    var initialize = function() {
        $("#patient_edit_table").dataTable({
            "bProcessing": true,
            "sAjaxSource": '/patient/getpatients/format/json',
            "aoColumns": [
                {"mData": 0},
                {"mData": 1},
                {"mData": 2},
                {"mData": 3},
                {"mData": 4},
                {"mData": 5},
                {// Spalte bearbeiten
                    "mData": null,
                    "sClass": "patient_edit_table_column",
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
                    "mRender": function(data) {
                        var button = '<a><button type="button" class="btn btn-primary"><span class="glyphicon glyphicon-edit"></span></button></a>';
                        return button;
                    },
                    "fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
                        var a = $(nTd).find("a");
                        a.attr("href", "/patient/edit-maxim/" + oData[0]);
                    }

                },
                {// Spalte Sprueche
                    "mData": null,
                    "sClass": "patient_edit_table_column",
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