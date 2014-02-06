$(document).ready(function() {
    var cpt = new ContactPointTable();

});

function ContactPointTable() {

    var _dataTable = $("#contactpoint_table");
    var initialize = function() {
        _dataTable.dataTable({
            "bProcessing": true,
            "aoColumns": [
                {"mData": 0},
                {"mData": 1},
                {"mData": 2},
                {"mData": 3},
                {"mData": 4},
                {"mData": 5},
                {"mData": 6},
                {// Spalte loeschen
                    "mData": null,
                    "sClass": "contactpoint_edit_table_column",
                    "mRender": function(data) {
                        var button = '<a><button type="button" class="btn btn-primary"><span class="glyphicon glyphicon-trash"></span></button></a>';
                        return button;
                    },
                    "fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
                        var button = $(nTd).find("button");
                        button.bind('click', function() {
                            deleteButtonClick(oData[0]);
                        });

                        
                    }
                },
                {// Spalte bearbeiten
                    "mData": null,
                    "sClass": "contactpoint_edit_table_column",
                    "mRender": function(data) {
                        var button = '<a><button type="button" class="btn btn-primary"><span class="glyphicon glyphicon-edit"></span></button></a>';
                        return button;
                    },
                    "fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
                        var a = $(nTd).find("a");
                        a.attr("href", "/contact-point/edit/" + oData[0]);
                    }

                }
            ]

        });

    };


    var deleteButtonClick = function(id) {
        $.ajax({
            type: "post",
            url: '/contact-point/delete/format/json',
            async: false,
            data: {id: id},
            success: function(data) {
                _dataTable.dataTable().fnReloadAjax('/contact-point/getcontactpoints/format/json');
            }
        });

    };

    initialize();

}