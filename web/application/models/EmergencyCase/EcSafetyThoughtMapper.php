<?php

class Application_Model_EmergencyCase_EcSafetyThoughtMapper {

    protected $_dbTable = null;

    public function setDbTable($dbTable) {
        if (is_string($dbTable)) {
            $dbTable = new $dbTable();
        }
        if (!$dbTable instanceof Zend_Db_Table_Abstract) {
            throw new Exception('EcSafetyThoughtMapper: Ungültiges Table Data Gateway angegeben');
        }
        $this->_dbTable = $dbTable;
        return $this;
    }

    public function getDbTable() {
        if (null === $this->_dbTable) {
            $this->setDbTable('Application_Model_DbTable_EcSafetyThought');
        }
        return $this->_dbTable;
    }

    public function save(Application_Model_EmergencyCase_EcSafetyThought $safetyThought) {
        $data = array(
            'estID' => $safetyThought->getEstID(),
            'text' => $safetyThought->getText(),
            'ecID_fk' => $safetyThought->getEcID_fk(),
        );

        if (null === ($id = $safetyThought->getEstID())) {
            unset($data['estID']);
            $this->getDbTable()->insert($data);
        } else {
            $this->getDbTable()->update($data, array('estID = ?' => $id));
        }
    }

    public function find($id, Application_Model_EmergencyCase_EcSafetyThought $safetyThought) {
        $result = $this->getDbTable()->find($id);
        if (0 == count($result)) {
            return;
        }
        $row = $result->current();       
        $safetyThought->setEstID($row->estID)
                ->setText($row->text)
                ->setEcID_fk($row->ecID_fk);
    }

}
