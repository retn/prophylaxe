<?php

class Application_Model_EmergencyCase_EcLimitRelapseMapper {

    protected $_dbTable = null;

    public function setDbTable($dbTable) {
        if (is_string($dbTable)) {
            $dbTable = new $dbTable();
        }
        if (!$dbTable instanceof Zend_Db_Table_Abstract) {
            throw new Exception('EcLimitRelapseMapper: Ungültiges Table Data Gateway angegeben');
        }
        $this->_dbTable = $dbTable;
        return $this;
    }

    public function getDbTable() {
        if (null === $this->_dbTable) {
            $this->setDbTable('Application_Model_DbTable_EcLimitRelapse');
        }
        return $this->_dbTable;
    }

    public function save(Application_Model_EmergencyCase_EcLimitRelapse $limitRelapse) {
        $data = array(
            'elrID' => $limitRelapse->getElrID(),
            'text' => $limitRelapse->getText(),
            'ecID_fk' => $limitRelapse->getEcID_fk(),
        );

        if (null === ($id = $limitRelapse->getElrID())) {
            unset($data['elrID']);
            $this->getDbTable()->insert($data);
        } else {
            $this->getDbTable()->update($data, array('elrID = ?' => $id));
        }
    }

    public function find($id, Application_Model_EmergencyCase_EcLimitRelapse $limitRelapse) {
        $result = $this->getDbTable()->find($id);
        if (0 == count($result)) {
            return;
        }
        $row = $result->current();       
        $limitRelapse->setElrID($row->elrID)
                ->setText($row->text)
                ->setEcID_fk($row->ecID_fk);
     
    }

}
