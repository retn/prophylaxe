<?php

class Application_Model_DbTable_EmergencyCase extends Zend_Db_Table_Abstract {

    protected $_name = 'spl_emergency_case';
    protected $_primary = 'ecID';
    // Automatisch hochzaehlender PK
    protected $_sequence = true;
    protected $_referenceMap = array(
        'Patient' => array(
            'columns' => 'patientID_fk',
            'refTableClass' => 'Application_Model_DbTable_Patient',
            'refColumns' => 'patientID'
    ));

}

