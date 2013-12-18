$(document).ready(function() {
    var mt = new MaximTable();
});
function MaximTable() {

    var _editButton = '<button type="button" class="btn btn-primary">Bearbeiten</button>';
    var _dataTable = $('#maxim_table');
    var _dialog_edit = $('#maxim_dialog_edit');
    var _dialog_confirm = $('#maxim_dialog_confirm');
    var _dialog_errors = '#maxim_form_errors';
    var _addButton = $('#maxim_create');


    var initialize = function() {
        initTable();
        initDialogs();
        _addButton.bind('click', addButtonClick);

    };

    var addButtonClick = function() {
        _dialog_edit.dialog("open");
    };

    var initTable = function() {
        _dataTable.dataTable({
            "bProcessing": true,
            "sAjaxSource": '/maxim/getmaxims/format/json',
            "aoColumns": [
                {
                    "mData": 0
                },
                {
                    "mData": 1,
                    "sWidth": "60%"},
                {
                    "mData": null, // Spalte Bearbeiten
                    'bSortable': false,
                    "sClass": "maxim_table_edit_column",
                    "mRender": function(data) {
                        return _editButton;
                    },
                    "fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
                        var button = $(nTd).find("button");
                        button.bind('click', function() {
                            _dialog_edit.find('#id').val(oData[0]);
                            _dialog_edit.find('#text').text(oData[1]);
                            _dialog_edit.dialog("open");
//                            alert('show');
                        });
                    }

                },
                {
                    "mData": null, // Spalte Loeschen
                    'bSortable': false,
                    "sClass": "maxim_table_delete_column",
                    "mRender": function(data) {
                        var button = '<a><button type="button" class="btn btn-primary">Löschen</button></a>';
                        return button;
                    },
                    "fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
                        var button = $(nTd).find("button");
                        button.bind('click', function() {
                            _dialog_confirm.find('#id_delete').val(oData[0]);
//                            _dialog_confirm.find('#text').text(oData[1]);
                            _dialog_confirm.dialog("open");
                        });

                    }

                }
            ]

        });
    };

    var initDialogs = function() {
        /*
         * Dialog zum bearbeiten/erstellen von Spruechen
         */
        _dialog_edit.dialog({
            autoOpen: false,
            modal: true,
            minWidth: 700,
            minHeight: 300,
            buttons: [
                {
                    text: "Ok", click: function() {
                        var form = _dialog_edit.find("form").serializeArray();

                        // Daten pruefen per Ajax-Request
                        $.ajax({
                            type: "POST",
                            url: "/maxim/save/format/json/",
                            data: {form: form},
                            success: saveMaxim
                        });

                    }
                },
                {
                    text: "Cancel", click: function() {
                        $(this).dialog("close");
                    }
                }
            ]

        });

        /*
         * Bestaetigungsdialog ob ein Spruch wirklich geloescht werden soll
         */
        _dialog_confirm.dialog({
            autoOpen: false,
            modal: true,
            minWidth: 300,
            minHeight: 100,
            buttons: [{
                    text: "Ok", click: function() {
                        var id = _dialog_confirm.find("#id_delete").val();

                        // Spruch loeschen per Ajax-Request
                        $.ajax({
                            type: "POST",
                            url: "/maxim/delete/format/json",
                            data: {id: id},
                            success: deleteMaxim
                        });

                    }}, {
                    text: "Cancel", click: function() {
                        $(this).dialog("close");
                    }}
            ]
        });


    };

    var getMessagesHtml = function(errors) {

        var o = $('<ul class = "errors">');
        $.each(errors, function(key, value) {
            $.each(errors[key], function(key, value) {
                var li = $('<li>' + value + '</li>');
                o.append(li);
            });

        });
        return o;
    };

    var saveMaxim = function(return_data) {

        if (return_data.valid !== true) {
            $list = getMessagesHtml(return_data.messages);
            $(_dialog_errors).children().remove();
            $(_dialog_errors).append($list);


        } else {
            _dialog_edit.dialog('close');
            _dataTable.dataTable().fnReloadAjax();
        }
    };

    var deleteMaxim = function(return_data) {
        _dialog_confirm.dialog('close');
        _dataTable.dataTable().fnReloadAjax();

    };

    initialize();
}
    