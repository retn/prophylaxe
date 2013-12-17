<?php

class Application_Model_MaximHasPatientMapper {

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
            $this->setDbTable('Application_Model_DbTable_MaximHasPatient');
        }
        return $this->_dbTable;
    }

    public function save(Application_Model_Maxim $maxim) {
        $data = array(
            'maximID' => $maxim->getId(),
            'text' => $maxim->getText(),
        );

        if (null === ($id = $maxim->getId())) {
            unset($data['id']);
            $this->getDbTable()->insert($data);
        } else {

//            $productsRowset = $this->getDbTable()->find($id);
//            $maxim = $productsRowset->current();
//            $maxim->setText("fuck you bitch");
//            
//            $maxim->save();

            $this->getDbTable()->update($data, array('maximID = ?' => $id));
        }
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
            $this->getDbTable()->insert(array('patient_patientID' => $patientID, 'maxim_maximID' => $value));
        }
    }


}
