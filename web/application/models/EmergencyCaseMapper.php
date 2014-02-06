<?php

class Application_Model_EmergencyCaseMapper {

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
            $this->setDbTable('Application_Model_DbTable_EmergencyCase');
        }
        return $this->_dbTable;
    }

    public function save(Application_Model_EmergencyCase $emergencycase) {
        $data = array(
            'ecID' => $emergencycase->getEcID(),
            'patientID_fk' => $emergencycase->getPatientID_fk(),
            'addict_drughotline' => $emergencycase->getAddict_drughotline(),
            'prop_advice_centre' => $emergencycase->getProp_advice_centre(),
            'my_therapist' => $emergencycase->getMy_therapist(),
            'risk_danger' => $emergencycase->getRisk_danger(),
            'risk_situation' => $emergencycase->getRisk_situation(),
            'risk_temptation' => $emergencycase->getRisk_temptation(),
            'temptation_thought' => $emergencycase->getTemptation_thought(),
            'temptation_thought_abstinence' => $emergencycase->getTemptation_thought_abstinence(),
            'temptation_behaviour' => $emergencycase->getTemptation_behaviour()            
        );

        if (null === ($id = $emergencycase->getEcID())) {
            unset($data['ecID']);
            $this->getDbTable()->insert($data);
        } else {
            $this->getDbTable()->update($data, array('ecID = ?' => $id));
        }
    }

    public function find($id, Application_Model_EmergencyCase $emergencycase) {
        $result = $this->getDbTable()->find($id);
        if (0 == count($result)) {
            return;
        }
        $row = $result->current();
        //$emotion = $row->findDependentRowset('Application_Model_DbTable_Emotion');
        $emergencycase->setEcID($row->ecID)
                ->setPatientID_fk($row->patientID_fk)
                ->setAddict_drughotline($row->addict_drughotline)
                ->setProp_advice_centre($row->prop_advice_centre)
                ->setMy_therapist($row->my_therapist)
                ->setRisk_danger($row->risk_danger)
                ->setRisk_situation($row->risk_situation)
                ->setRisk_temptation($row->risk_temptation)
                ->setTemptation_thought($row->temptation_thought)
                ->setTemptation_thought_abstinence($row->temptation_thought_abstinence)
                ->setTemptation_behaviour($row->temptation_behaviour);
    }

    public function fetchAll() {
//        $resultSet = $this->getDbTable()->fetchAll();
        $entries = array();
        
        $select = $this->getDbTable()->select(Zend_Db_Table::SELECT_WITH_FROM_PART)
                ->setIntegrityCheck(false);        
        $select->join('spl_emotion', 'spl_emotion.emotionID = spl_distraction.emotionID_fk','emotion');
        $rows = $this->getDbTable()->fetchAll($select);        

        foreach ($rows as $row) {
//            $emotionMapper = new Application_Model_EmotionMapper();
//            $emotion = new Application_Model_Emotion();
//            $emotionMapper->find($row->emotionID_fk, $emotion);

            $entry = new Application_Model_Distraction();
            $entry->setId($row->distractionID)
                    ->setText($row->text)
                    ->setEmotion($row->emotionID_fk)
                    ->setEmotionText($row->emotion);
            $entries[] = $entry;
        }
        return $entries;
    }

    public function delete($id) {
        $emergencyCaseRowset = $this->getDbTable()->find($id);
        $emergencyCase = $emergencyCaseRowset->current();
        $anzDeletedRows = $emergencyCase->delete();
        return $anzDeletedRows;
    }

}
