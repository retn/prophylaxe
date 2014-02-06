<?php

class Application_Model_DistractionHasPatientMapper {

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
            $this->setDbTable('Application_Model_DbTable_DistractionHasPatient');
        }
        return $this->_dbTable;
    }

   
    public function saveAll($patientID, $maximids) {

        // Alle Spreuche des Patienten zuerst loeschen
        $where = $this->getDbTable()->getAdapter()->quoteInto('patient_patientID= ?', $patientID);
        $this->getDbTable()->delete($where);

//        foreach ($maximids as $value) {
//            $this->getDbTable()->delete(array('patient_patientID' => $patientID));
//        }

        // Alle gewaehlten Sprueche eintragen
        foreach ($maximids as $value) {
            $this->getDbTable()->insert(array('patient_patientID' => $patientID, 'distraction_distractionID' => $value));
        }
    }


}
