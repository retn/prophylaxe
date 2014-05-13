<?php

class Application_Model_EmergencyCase_EcSafetyActionMapper {

    protected $_dbTable = null;

    public function setDbTable($dbTable) {
        if (is_string($dbTable)) {
            $dbTable = new $dbTable();
        }
        if (!$dbTable instanceof Zend_Db_Table_Abstract) {
            throw new Exception('EcSafetyActionMapper: Ungültiges Table Data Gateway angegeben');
        }
        $this->_dbTable = $dbTable;
        return $this;
    }

    public function getDbTable() {
        if (null === $this->_dbTable) {
            $this->setDbTable('Application_Model_DbTable_EcSafetyAction');
        }
        return $this->_dbTable;
    }

    public function save(Application_Model_EmergencyCase_EcSafetyAction $safetyAction) {
        $data = array(
            'esaID' => $safetyAction->getEsaID(),
            'text' => $safetyAction->getText(),
            'ecID_fk' => $safetyAction->getEcID_fk(),
        );

        if (null === ($id = $safetyAction->getEsaID())) {
            unset($data['esaID']);
            $this->getDbTable()->insert($data);
        } else {
            $this->getDbTable()->update($data, array('esaID = ?' => $id));
        }
    }

    public function find($id, Application_Model_EmergencyCase_EcSafetyAction $safetyAction) {
        $result = $this->getDbTable()->find($id);
        if (0 == count($result)) {
            return;
        }
        $row = $result->current();
        $safetyAction->setEsaID($row->esaID)
                ->setText($row->text)
                ->setEcID_fk($row->ecID_fk);
    }

}
