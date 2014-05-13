<?php

class Application_Model_EmergencyCase_EcHelpPersonMapper {

    protected $_dbTable = null;

    public function setDbTable($dbTable) {
        if (is_string($dbTable)) {
            $dbTable = new $dbTable();
        }
        if (!$dbTable instanceof Zend_Db_Table_Abstract) {
            throw new Exception('Application_Model_EcHelpPersonMapper: Ungültiges Table Data Gateway angegeben');
        }
        $this->_dbTable = $dbTable;
        return $this;
    }

    public function getDbTable() {
        if (null === $this->_dbTable) {
            $this->setDbTable('Application_Model_DbTable_EcHelpPerson');
        }
        return $this->_dbTable;
    }

    public function save(Application_Model_EmergencyCase_EcHelpPerson $helpPerson) {
        $data = array(
            'ehpID' => $helpPerson->getEhpID(),
            'name' => $helpPerson->getName(),
            'phone_number' => $helpPerson->getPhone_number(),
            'ecID_fk' => $helpPerson->getEcID_fk(),
        );

        if (null === ($id = $helpPerson->getEhpID())) {
            unset($data['ehpID']);
            $this->getDbTable()->insert($data);
        } else {
            $this->getDbTable()->update($data, array('ehpID = ?' => $id));
        }
    }

    public function find($id, Application_Model_EmergencyCase_EcHelpPerson $helpPerson) {
        $result = $this->getDbTable()->find($id);
        if (0 == count($result)) {
            return;
        }
        $row = $result->current();       
        
        $helpPerson->setEhpID($row->ehpID)
                ->setName($row->name)
                ->setPhone_number($row->phone_number)
                ->setEcID_fk($row->ecID_fk);
     
    }

    public function fetchAll() {
//        $resultSet = $this->getDbTable()->fetchAll();
//        $entries = array();
//        
//        $select = $this->getDbTable()->select(Zend_Db_Table::SELECT_WITH_FROM_PART)
//                ->setIntegrityCheck(false);        
//        $select->join('spl_emotion', 'spl_emotion.emotionID = spl_distraction.emotionID_fk','emotion');
//        $rows = $this->getDbTable()->fetchAll($select);        
//
//        foreach ($rows as $row) {
////            $emotionMapper = new Application_Model_EmotionMapper();
////            $emotion = new Application_Model_Emotion();
////            $emotionMapper->find($row->emotionID_fk, $emotion);
//
//            $entry = new Application_Model_Distraction();
//            $entry->setId($row->distractionID)
//                    ->setText($row->text)
//                    ->setEmotion($row->emotionID_fk)
//                    ->setEmotionText($row->emotion);
//            $entries[] = $entry;
//        }
//        return $entries;
    }

    public function delete($id) {
        $helPersonRowset = $this->getDbTable()->find($id);
        $helpPerson = $helPersonRowset->current();

        $anzDeletedRows = $helpPerson->delete();

        return $anzDeletedRows;
    }

}

