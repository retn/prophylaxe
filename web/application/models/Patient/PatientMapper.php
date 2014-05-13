<?php

class Application_Model_Patient_PatientMapper {

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

    public function save(Application_Model_Patient_Patient &$patient) {
        // Security Token erzeugen
        $random_hash = substr(md5(uniqid(rand(), true)), 0, 10);
        $data = array(
            'first_name' => $patient->getFirstname(),
            'last_name' => $patient->getLastname(),
            'user_name' => $patient->getUsername(),
            'birth_date' => $patient->getBirthdate(),
            'email' => $patient->getEmail(),
            'userID_fk' => $patient->getUserID_fk(),
            'token' => $patient->getToken(),
            'token_used' => $patient->getToken_used()
        );

        if (null === ($id = $patient->getId())) {
            unset($data['id']);
            $data['token'] = $random_hash;
            $patient->setToken($data['token']);
            
            $id  =$this->getDbTable()->insert($data);            
            $patient->setId($id);
            
        } else {
            $this->getDbTable()->update($data, array('patientID = ?' => $id));
        }
    }

    public function find($id, Application_Model_Patient_Patient $patient = null) {
        $result = $this->getDbTable()->find($id);
        if ($patient == null)
            return $result->current();

        if (0 == count($result)) {
            return;
        }
        $patientRow = $result->current();
        $patient->setId($patientRow->patientID)
                ->setFirstname($patientRow->first_name)
                ->setLastname($patientRow->last_name)
                ->setUsername($patientRow->user_name)
                ->setBirthdate($patientRow->birth_date)
                ->setEmail($patientRow->email)
                ->setUserID_fk($patientRow->userID_fk)
                ->setToken($patientRow->token)
                ->setToken_used($patientRow->token_used);

        $this->statusCheck($patientRow, $patient);
    }

    public function findByEmail($email, Application_Model_Patient_Patient $patient = null) {
        $select = $this->getDbTable()->select();
        $select->where('email = ?', $email);

        $row = $this->getDbTable()->fetchAll($select);

        // Wenn kein Datensatz gefunden wurde abbrechen
        if (!($row->count() > 0)) {
            return false;
        }

        $row = $row->current();

        $patient->setId($row->patientID)
                ->setFirstname($row->first_name)
                ->setLastname($row->last_name)
                ->setUsername($row->user_name)
                ->setBirthdate($row->birth_date)
                ->setEmail($row->email)
                ->setUserID_fk($row->userID_fk)
                ->setToken($row->token)
                ->setToken_used($row->token_used);

        $this->statusCheck($row, $patient);

        return true;
    }

    /*
     * Prueft ob fuer einen Patient bereits ein EmergencyCase, Maxims oder Distractions zugeordnet wurden
     */

    private function statusCheck(Zend_Db_Table_Row $patientRow, Application_Model_Patient_Patient $patient) {
//          $patientRow = $this->find($patientID);
        $result = $patientRow->findDependentRowset('Application_Model_DbTable_EmergencyCase');
        if ($result->count() > 0)
            $patient->setHasEmergencyCase(true);

        $result = $patientRow->findDependentRowset('Application_Model_DbTable_MaximHasPatient');
        if ($result->count() > 0)
            $patient->setHasMaxim(true);

        $result = $patientRow->findDependentRowset('Application_Model_DbTable_DistractionHasPatient');
        if ($result->count() > 0)
            $patient->setHasDistraction(true);
//        $result = $patientRow->findManyToManyRowset('Application_Model_DbTable_Maxim', 'Application_Model_DbTable_MaximHasPatient');
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
        foreach ($patients as $patientRow) {
            $entry = new Application_Model_Patient_Patient();
            $entry->setId($patientRow->patientID)
                    ->setFirstname($patientRow->first_name)
                    ->setLastname($patientRow->last_name)
                    ->setUsername($patientRow->user_name)
                    ->setBirthdate($patientRow->birth_date)
                    ->setEmail($patientRow->email)
                    ->setUserID_fk($patientRow->userID_fk)
                    ->setToken($patientRow->token)
                    ->setToken_used($patientRow->token_used);

            $this->statusCheck($patientRow, $entry);
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

    public function getDistractionsFromPatient($patientID) {
        $patientRow = $this->find($patientID);
//        $result = $patientRow->findDependentRowset('Application_Model_DbTable_MaximHasPatient');
        $result = $patientRow->findManyToManyRowset('Application_Model_DbTable_Distraction', 'Application_Model_DbTable_DistractionHasPatient');

        $entries = array();
        foreach ($result as $row) {
            // Emotion-String ermitteln
            $emotionMapper = new Application_Model_EmotionMapper();
            $emotion = new Application_Model_Emotion();
            $emotionMapper->find($row->emotionID_fk, $emotion);

            $entry = new Application_Model_Distraction();
            $entry->setId($row->distractionID)
                    ->setText($row->text)
                    ->setEmotion($row->emotionID_fk)
                    ->setEmotionText($emotion->getEmotion());
            $entries[] = $entry;
        }

        return $entries;
    }

}
