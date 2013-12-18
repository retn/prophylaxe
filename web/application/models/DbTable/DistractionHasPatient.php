<?php

class Application_Model_DbTable_DistractionHasPatient extends Zend_Db_Table_Abstract {

    protected $_name = 'spl_distraction_has_patient';
    protected $_primary = array('distraction_distractionID', 'patient_patientID');
    protected $_referenceMap = array(
        'Distraction' => array(
            'columns' => 'distraction_distractionID',
            'refTableClass' => 'Application_Model_DbTable_Distraction',
            'refColumns' => 'distractionID'
        ),
        'Patient' => array(
            'columns' => 'patient_patientID',
            'refTableClass' => 'Application_Model_DbTable_Patient',
            'refColumns' => 'patientID'
    ));

}

