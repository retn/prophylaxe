<?php

class Application_Model_DbTable_MaximHasPatient extends Zend_Db_Table_Abstract {

    protected $_name = 'spl_maxim_has_patient';
    protected $_primary = array('maxim_maximID', 'patient_patientID');
//    protected $_dependentTables = array('Maxim', 'Patient');
    protected $_referenceMap = array(
        'Maxim' => array(
            'columns' => 'maxim_maximID',
            'refTableClass' => 'Application_Model_DbTable_Maxim',
            'refColumns' => 'maximID'
        ),
        'Patient' => array(
            'columns' => 'patient_patientID',
            'refTableClass' => 'Application_Model_DbTable_Patient',
            'refColumns' => 'patientID'
    ));

}

