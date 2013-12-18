<?php

class Application_Model_DbTable_Patient extends Zend_Db_Table_Abstract {

    protected $_name = 'spl_patient';
    protected $_primary = 'patientID';
    // Automatisch hochzaehlender PK
    protected $_sequence = true;
//    protected $_dependentTables = array('MaximHasPatient');

    protected $_referenceMap = array(
        'User' => array(
            'columns' => 'userID_fk',
            'refTableClass' => 'Application_Model_DbTable_User',
            'refColumns' => 'userID'
        ));
    
}

