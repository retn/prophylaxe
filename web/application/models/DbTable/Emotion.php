<?php

class Application_Model_DbTable_Emotion extends Zend_Db_Table_Abstract {

    protected $_name = 'spl_emotion';
    protected $_primary = 'emotionID';
    // Automatisch hochzaehlender PK
    protected $_sequence = true;

}

