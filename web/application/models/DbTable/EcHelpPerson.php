<?php

class Application_Model_DbTable_EcHelpPerson extends Zend_Db_Table_Abstract {

    protected $_name = 'spl_ec_help_person';
    protected $_primary = 'ehpID';
    // Automatisch hochzaehlender PK
    protected $_sequence = true;
    protected $_referenceMap = array(
        'EmergencyCase' => array(
            'columns' => 'ecID_fk',
            'refTableClass' => 'Application_Model_DbTable_EmergencyCase',
            'refColumns' => 'ecID'
    ));

}
