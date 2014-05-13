$(document).ready(function() {
    var dt = new DistractionTable();
});
function DistractionTable() {

    var _editButton = '<button type="button" class="btn btn-primary">Bearbeiten</button>';
    var _dataTable = $('#distraction_table');
    var _dialog_edit = $('#distraction_dialog_edit');
    var _dialog_confirm = $('#distraction_dialog_confirm');
    var _dialog_errors = '#distraction_form_errors';
    var _addButton = $('#distraction_create');
    var _textEdit = "Möglichkeit bearbeiten";
    var _textCreate = "Möglichkeit anlegen";


    var initialize = function() {
        initTable();
        initDialogs();
        _addButton.bind('click', addButtonClick);

    };

    var addButtonClick = function() {
        _dialog_edit.find('#id').val('');
        _dialog_edit.find('h3').text(_textCreate);
        _dialog_edit.find('#text').text('');
        _dialog_edit.dialog("open");
    };

    var initTable = function() {
        _dataTable.dataTable({
            "bProcessing": true,
            "sAjaxSource": '/distraction/get-distractions/format/json',
            "aoColumns": [
                {
                    "mData": 0
                },
                {
                    "mData": 1,
                    "sWidth": "60%"
                },
                {
                    "mData": 2
                },
                {
                    "mData": null, // Spalte Bearbeiten
                    'bSortable': false,
                    "sClass": "distraction_table_edit_column",
                    "mRender": function(data) {
                        return _editButton;
                    },
                    "fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
                        var button = $(nTd).find("button");
                        button.bind('click', function() {
                            _dialog_edit.find('#id').val(oData[0]);
                            _dialog_edit.find('#text').text(oData[1]);
                            _dialog_edit.find('#emotion').val(oData[3]);
                              _dialog_edit.find('h3').text(_textEdit);
                            _dialog_edit.dialog("open");
//                            alert('show');
                        });
                    }

                },
                {
                    "mData": null, // Spalte Loeschen
                    'bSortable': false,
                    "sClass": "distraction_table_edit_column",
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
                            url: "/distraction/save/format/json/",
                            data: {form: form},
                            success: saveDistraction
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
                            url: "/distraction/delete/format/json",
                            data: {id: id},
                            success: deleteDistraction
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

    var saveDistraction = function(return_data) {

        if (return_data.valid !== true) {
            $list = getMessagesHtml(return_data.messages);
            $(_dialog_errors).children().remove();
            $(_dialog_errors).append($list);


        } else {
            _dialog_edit.dialog('close');
            _dataTable.dataTable().fnReloadAjax();
        }
    };

    var deleteDistraction = function(return_data) {
        _dialog_confirm.dialog('close');
        _dataTable.dataTable().fnReloadAjax();

    };

    initialize();
}
    