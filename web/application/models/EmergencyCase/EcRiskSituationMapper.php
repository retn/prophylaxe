<?php

class Application_Model_EmergencyCase_EcRiskSituationMapper {

    protected $_dbTable = null;

    public function setDbTable($dbTable) {
        if (is_string($dbTable)) {
            $dbTable = new $dbTable();
        }
        if (!$dbTable instanceof Zend_Db_Table_Abstract) {
            throw new Exception('Ungültiges Table Data Gateway angegeben');
        }
        $this->_dbTable = $dbTable;
        return $this;
    }

    public function getDbTable() {
        if (null === $this->_dbTable) {
            $this->setDbTable('Application_Model_DbTable_EcRiskSituation');
        }
        return $this->_dbTable;
    }

    public function save(Application_Model_EmergencyCase_EcRiskSituation $riskSituation) {
        $data = array(
            'ersID' => $riskSituation->getErsID(),
            'text' => $riskSituation->getText(),
            'ecID_fk' => $riskSituation->getEcID_fk(),
        );

        if (null === ($id = $riskSituation->getErsID())) {
            unset($data['ersID']);
            $this->getDbTable()->insert($data);
        } else {
            $this->getDbTable()->update($data, array('ersID = ?' => $id));
        }
    }

    public function find($id, Application_Model_EmergencyCase_EcRiskSituation $riskSituation) {
        $result = $this->getDbTable()->find($id);
        if (0 == count($result)) {
            return;
        }
        $row = $result->current();       
        $riskSituation->setErsID($row->ersID)
                ->setText($row->text)
                ->setEcID_fk($row->ecID_fk);
     
    }

}
