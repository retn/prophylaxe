<?php

class Application_Model_DbTable_EcLimitRelapse extends Zend_Db_Table_Abstract {

    protected $_name = 'spl_ec_limit_relapse';
    protected $_primary = 'elrID';
    // Automatisch hochzaehlender PK
    protected $_sequence = true;
    protected $_referenceMap = array(
        'EmergencyCase' => array(
            'columns' => 'ecID_fk',
            'refTableClass' => 'Application_Model_DbTable_EmergencyCase',
            'refColumns' => 'ecID'
    ));

}
