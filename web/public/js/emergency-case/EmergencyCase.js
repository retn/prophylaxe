$(document).ready(function() {

    var _counter = 2;
    var _riskRow = '<dt></td><dd><input  name="" type="text" value=""/><button name="btnRemove" type="button" class="btn btn-warning"><span class="glyphicon glyphicon-minus"></span></button></dd>';
    var _riskRowName = 'risk_situation_array[pers_risk_situation_';

    $('#btnAdd').bind('click', function() {
        riskRow = $(_riskRow);
        riskRow.find('input').attr('name', _riskRowName + _counter + "]");

        $('#fieldset-risk_situations dl').append(riskRow);
        _counter++;

    });
});