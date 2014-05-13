<?php

class Application_Model_DbTable_EcSafetyAction extends Zend_Db_Table_Abstract {

    protected $_name = 'spl_ec_safety_action';
    protected $_primary = 'esaID';
    // Automatisch hochzaehlender PK
    protected $_sequence = true;
    protected $_referenceMap = array(
        'EmergencyCase' => array(
            'columns' => 'ecID_fk',
            'refTableClass' => 'Application_Model_DbTable_EmergencyCase',
            'refColumns' => 'ecID'
    ));

}
