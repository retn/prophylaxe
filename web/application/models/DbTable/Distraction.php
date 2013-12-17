<?php

class Application_Model_DbTable_Distraction extends Zend_Db_Table_Abstract {

    protected $_name = 'spl_distraction';
    protected $_primary = 'distractionID';
    // Automatisch hochzaehlender PK
    protected $_sequence = true;
    protected $_referenceMap = array(
        'Emotion' => array(
            'columns' => 'emotionID_fk',
            'refTableClass' => 'Application_Model_DbTable_Emotion',
            'refColumns' => 'emotionID'
    ));

}

