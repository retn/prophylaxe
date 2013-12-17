<?php

class Application_Model_PatientMapper {

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
            $this->setDbTable('Application_Model_DbTable_Patient');
        }
        return $this->_dbTable;
    }

    public function save(Application_Model_Patient $patient) {
        $data = array(
            'first_name' => $patient->getFirstname(),
            'last_name' => $patient->getLastname(),
            'user_name' => $patient->getUsername(),
            'birth_date' => $patient->getBirthdate(),
            'email' => $patient->getEmail(),
            'userID_fk' => $patient->getUserID_fk(),
        );

        if (null === ($id = $patient->getId())) {
            unset($data['id']);
            $this->getDbTable()->insert($data);
        } else {
            $this->getDbTable()->update($data, array('patientID = ?' => $id));
        }
    }

    public function find($id, Application_Model_Patient $patient = null) {
        $result = $this->getDbTable()->find($id);
        if ($patient == null)
            return $result->current();

        if (0 == count($result)) {
            return;
        }
        $row = $result->current();
        $patient->setId($row->patientID)
                ->setFirstname($row->first_name)
                ->setLastname($row->last_name)
                ->setUsername($row->user_name)
                ->setBirthdate($row->birth_date)
                ->setEmail($row->email)
                ->setUserID_fk($row->userID_fk);
    }

    public function fetchAll() {
//         $select = $users->select()
//                        ->where('username = ?', $this->username),
//                        ->where('password = ?', md5($this->password));
//        $user = $users->fetchRow($select);
// 
        // Daten des eingeloggten Benutzers lesen, nur Admin darf alle Patienten sehen
        $userStorage = Zend_Auth::getInstance()->getStorage()->read();
        $user = new Application_Model_User((array) $userStorage);

        if (($user->getUser_role() === "admin")) {
            $patients = $this->getDbTable()->fetchAll();
        } else {
            // Nur Patienten filtern die vom eingloggten User angelegt wurden
            $userMapper = new Application_Model_UserMapper();
            $userRow = $userMapper->find($user->getUserID())->current(); // Aktuelle Row mit current()
            $patients = $userRow->findDependentRowset('Application_Model_DbTable_Patient');
        }


//      $where = $this->getDbTable()->getAdapter()->quoteInto('userID_fk = ?', $user->userID);
//      $resultSet = $this->getDbTable()->fetchAll($where);

        $entries = array();
        foreach ($patients as $row) {
            $entry = new Application_Model_Patient();
            $entry->setId($row->patientID)
                    ->setFirstname($row->first_name)
                    ->setLastname($row->last_name)
                    ->setUsername($row->user_name)
                    ->setBirthdate($row->birth_date)
                    ->setEmail($row->email)
                    ->setUserID_fk($row->userID_fk);

            $entries[] = $entry;
        }
        return $entries;
    }

    public function getMaximsFromPatient($patientID) {
        $patientRow = $this->find($patientID);
//        $result = $patientRow->findDependentRowset('Application_Model_DbTable_MaximHasPatient');
        $result = $patientRow->findManyToManyRowset('Application_Model_DbTable_Maxim', 'Application_Model_DbTable_MaximHasPatient');

        $entries = array();
        foreach ($result as $row) {
            $entry = new Application_Model_Maxim();
            $entry->setId($row->maximID)
                    ->setText($row->text);
            $entries[] = $entry;
        }

        return $entries;
    }

}
