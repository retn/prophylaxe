$(document).ready(function() {
    var _Row = '<li><input type="text" class="" value="" name="risk_situations_array[]"><button name="btnRemove" class="emergency_case_delete_button">L&ouml;schen</button></li>';
    var rs = new RiskSituation(_Row);
    var sa = new SafetyAction();
    var st = new SafetyThought();
    var hp = new HelpPerson();
    var lr = new LimitRelapse();
});

function replaceAll(find, replace, str) {
    return str.replace(new RegExp(find, 'g'), replace);
}

function RiskSituation(row, btnAddId, listID, btnRemove) {
    var _row = row;
    
    var _btnAddId = $('#btnRiskSituationAdd');
    var _listID = $('#fieldset-risk_situations_list');
    var _btnRemove = _listID.find("[name='btnRemove']");    

    _btnAddId.bind('click', function() {
        var row = $(_row);        
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

function SafetyAction() {
    var _Row = '<li><input name="safety_actions_array[]" type="text" value=""/><button name="btnRemove" type="button" class="btn btn-warning"><span class="glyphicon glyphicon-minus"></span></button></li>';
    var _btnAddId = $('#btnSafetyActionAdd');
    var _listID = $('#fieldset-safety_actions_list');
    var _btnRemove = _listID.find("[name='btnRemove']");


    _btnAddId.bind('click', function() {
        _btnRemove.bind('click', function() {
            _btnRemoveClick(this);
        });
        _listID.append($(_Row));
    });

    _btnRemove.bind('click', function() {
        _btnRemoveClick(this);
    });

    var _btnRemoveClick = function(element) {
        var li = $(element).parent();
        li.remove();
    }
}

function LimitRelapse() {
    var _Row = '<li><input name="limit_relapses_array[]" type="text" value=""/><button name="btnRemove" type="button" class="btn btn-warning"><span class="glyphicon glyphicon-minus"></span></button></li>';
    var _btnAddId = $('#btnLimitRelapseAdd');
    var _listID = $('#fieldset-limit_relapses_list');
    var _btnRemove = _listID.find("[name='btnRemove']");

    _btnAddId.bind('click', function() {
        _btnRemove.bind('click', function() {
            _btnRemoveClick(this);
        });
        _listID.append($(_Row));
    });

    _btnRemove.bind('click', function() {
        _btnRemoveClick(this);
    });

    var _btnRemoveClick = function(element) {
        var li = $(element).parent();
        li.remove();
    }
}

function SafetyThought() {
    var _Row = '<li><input type="text" value="" name="safety_thoughts_array[]"><button class="btn btn-warning" type="button" name="btnRemove"><span class="glyphicon glyphicon-minus"></span></button></li>';
    var _btnAddId = $('#btnSafetyThoughtAdd');
    var _listID = $('#fieldset-safety_thoughts_list');
    var _btnRemove = _listID.find("[name='btnRemove']");

    _btnAddId.bind('click', function() {

        _btnRemove.bind('click', function() {
            _btnRemoveClick(this);
        });
        _listID.append($(_Row));
    });

    _btnRemove.bind('click', function() {
        _btnRemoveClick(this);
    });

    var _btnRemoveClick = function(element) {
        var li = $(element).parent();
        li.remove();
    }

//   


}

function HelpPerson() {
    var _counter = 0;
    var _Row = '<li><label for="help_person_%X%-name">Name</label><input name="help_persons_array[help_person_%X%][name]" id="help_person_%X%-name" value="foo" size="10" type="text"> / <label for="help_person_%X%-phonenumber">Nummer</label><input name="help_persons_array[help_person_%X%][phonenumber]" id="help_person_%X%-phonenumber" value="" size="10" type="text"><button class="btn btn-warning" type="button" name="btnRemove"><span class="glyphicon glyphicon-minus"></span></button></li>';
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

