<?php

class Application_Model_DbTable_Maxim extends Zend_Db_Table_Abstract {

    protected $_name = 'spl_maxim';
    protected $_primary = 'maximID';
    // Automatisch hochzaehlender PK
    protected $_sequence = true;
//    protected $_dependentTables = array('MaximHasPatient');

    
    
}

