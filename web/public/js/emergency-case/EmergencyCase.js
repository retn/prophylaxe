$(document).ready(function() {
    var _Row = '<li><input type="text" class="" value="" name="risk_situations_array[]"><button name="btnRemove" class="emergency_case_delete_button">L&ouml;schen</button></li>';

    var riskSituation = new BindingHandler(_Row, $('#btnRiskSituationAdd'), $('#fieldset-risk_situations_list'), 'btnRemove', 'risk_situations_array[]');
    var safetyAction = new BindingHandler(_Row, $('#btnSafetyActionAdd'), $('#fieldset-safety_actions_list'), 'btnRemove', 'safety_actions_array[]');
    var limitRelapse = new BindingHandler(_Row, $('#btnLimitRelapseAdd'), $('#fieldset-limit_relapses_list'), 'btnRemove', 'limit_relapses_array[]');
    var safetyThought = new BindingHandler(_Row, $('#btnSafetyThoughtAdd'), $('#fieldset-safety_thoughts_list'), 'btnRemove', 'safety_thoughts_array[]');

    var hp = new HelpPerson();

});

function replaceAll(find, replace, str) {
    return str.replace(new RegExp(find, 'g'), replace);
}

/*
 * 
 * @param {type} row = Template das eingefuegt wird bei einem Klick 
 * @param {type} btnAddId = Hinzufuegen-Button
 * @param {type} listID = Liste in dem rows hinzugefuegt werden
 * @param {type} btnRemove = Loeschen-Button
 * @param inputName = Name (POST) in dem die Formular-Werte gespeichert werden 
 */
function BindingHandler(row, btnAddId, listID, btnRemove, inputName) {
    var _row = row;


    var _btnAddId = btnAddId;
    var _listID = listID;
    var _btnRemoveSelektor = "[name=" + "'" + btnRemove + "'" + "]";
    var _btnRemove = _listID.find(_btnRemoveSelektor); //_listID.find("[name='btnRemove']");    
    var _inputName = inputName;
    
//    console.log(_btnRemove);

    _btnAddId.bind('click', function() {
        var row = $(_row);
        row.find('input').attr('name', _inputName);
        row.find(_btnRemoveSelektor).bind('click', function() {
            _btnRemoveClick(this);
        });
        _listID.append(row);
    });

    _btnRemove.bind('click', function() {
        _btnRemoveClick(this);
    });

    var _btnRemoveClick = function(element) {
        var li = $(element).parent();
        li.remove();
    }
}

function HelpPerson() {
    var _counter = 0;
    var _Row = '<li><label for="help_person_%X%-name">Name</label><input name="help_persons_array[help_person_%X%][name]" id="help_person_%X%-name" value="" size="10" type="text"> / <label for="help_person_%X%-phonenumber">Nummer</label><input name="help_persons_array[help_person_%X%][phonenumber]" id="help_person_%X%-phonenumber" value="" size="10" type="text"><button name="btnRemove" class="emergency_case_delete_button">L&ouml;schen</button></li>';
    var _RowName = 'help_person_%X%[phonenumber]';
    var _btnAddId = $('#btnHelpPersonAdd');
    var _listID = $('#fieldset-help_persons_list');
    var _btnRemove = _listID.find("[name='btnRemove']");

    /*
     * Ermittelt den hoechsten Index des Input-Namen-Feldes
     * Dadurch wird verhindert das beim Entfernen/Hinzufuegen Felder den gleichen Index im Namen bekommen
     */
    _listID.find('input[name^="help_person_"]').each(function(index, value) {
        tmp = $(value).attr('name');
        start = tmp.search('.[0-9]+.');
        end = tmp.search('.]');
        tmp = tmp.substring(start + 1, end + 1);
        if (tmp > _counter) {
            _counter = tmp;
        }
    });


    _btnAddId.bind('click', function() {
        _counter++;
        row = $(replaceAll('%X%', _counter, _Row)); // $(_Row.replace('%X%', _counter));
//        row.find('input').attr('name',_RowName.replace('%X%', _counter));
        row.find("[name='btnRemove']").bind('click', function() {
            _btnRemoveClick(this);
        });
        _listID.append(row);

    });

    _btnRemove.bind('click', function() {
        _btnRemoveClick(this);
    });

    var _btnRemoveClick = function(element) {
        var li = $(element).parent();
        li.remove();
    }
}

