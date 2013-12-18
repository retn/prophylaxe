<?php

class Application_Model_UserMapper {

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
            $this->setDbTable('Application_Model_DbTable_User');
        }
        return $this->_dbTable;
    }

    public function save(Application_Model_Patient $patient) {
//        $data = array(
//            'first_name' => $patient->getFirstname(),
//            'last_name' => $patient->getLastname(),
//            'user_name' => $patient->getUsername(),
//            'birth_date' => $patient->getBirthdate(),
//            'email' => $patient->getEmail(),
//        );
//
//        if (null === ($id = $patient->getId())) {
//            unset($data['id']);
//            $this->getDbTable()->insert($data);
//        } else {
//            $this->getDbTable()->update($data, array('patientID = ?' => $id));
//        }
    }

    public function find($id, Application_Model_User $user = null) {
        $result = $this->getDbTable()->find($id);
        
        if ($user === null) {
            return $result;
        }
        if (0 == count($result)) {
            return;
        }
        $row = $result->current();
        $user->setUserID($row->userID)
                ->setUser_name($row->user_name)
                ->setUser_role($row->user_role)
                ->setEmail($row->email);
    }

    public function fetchAll() {

//        $resultSet = $this->getDbTable()->fetchAll();
//        $entries = array();
//        // where Zend_Auth::getInstance()->getStorage()->read()->userID = userId_fk
//        foreach ($resultSet as $row) {
//            $entry = new Application_Model_Patient();
//            $entry->setId($row->patientID)
//                    ->setFirstname($row->first_name)
//                    ->setLastname($row->last_name)
//                    ->setUsername($row->user_name)
//                    ->setBirthdate($row->birth_date)
//                    ->setEmail($row->email);
//            $entries[] = $entry;
//        }
//        return $entries;
    }

}

