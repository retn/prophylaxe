<?php

class Application_Model_EmergencyCase_EmergencyCaseMapper {

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

    public function save(Application_Model_EmergencyCase_EmergencyCase $emergencycase) {
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
            $id = $this->getDbTable()->insert($data);
            $emergencycase->setEcID($id);
//            $this->saveRiskSituations($id, $emergencycase->getRisk_situation_array());
        } else {
            $this->getDbTable()->update($data, array('ecID = ?' => $id));
            $row = $this->getDbTable()->find($id);

            // Vom Notfallkoffer abhaengige Daten in der Datenbank loeschen und neu einfuegen
            // Risikosituationen loeschen
            $dependent_risks = $row->current()->findDependentRowset('Application_Model_DbTable_EcRiskSituation');
            foreach ($dependent_risks as $value) {
                $value->delete();
            }
            // Hilfe Personen loeschen
            $dependent_helpPersons = $row->current()->findDependentRowset('Application_Model_DbTable_EcHelpPerson');
            foreach ($dependent_helpPersons as $value) {
                $value->delete();
            }

            // Rueckfall Begrenzen loeschen
            $dependent_limitRelapses = $row->current()->findDependentRowset('Application_Model_DbTable_EcLimitRelapse');
            foreach ($dependent_limitRelapses as $value) {
                $value->delete();
            }
            
            // Safety Thoughts loeschen
            $dependent_safetyThought = $row->current()->findDependentRowset('Application_Model_DbTable_EcSafetyThought');
            foreach ($dependent_safetyThought as $value) {
                $value->delete();
            }            
            
            // Safety Actions loeschen
            $dependent_safetyActions = $row->current()->findDependentRowset('Application_Model_DbTable_EcSafetyAction');
            foreach ($dependent_safetyActions as $value) {
                $value->delete();
            }
        }
        // Datensaetze speichern
        $this->saveDependentRows($emergencycase);
//        return $id;
    }

    public function findEmergencyCaseOfPatient($patientID) {
        $patientMapper = new Application_Model_Patient_PatientMapper();
        $patientRow = $patientMapper->find($patientID);
        // Zugehoerigen Notfallkoffer holen
        $rowEC = $patientRow->findDependentRowset('Application_Model_DbTable_EmergencyCase');

        if(!($rowEC->count() > 0))
            return null;
        // Zugehoerige Rueckfaelle holen
        $limitRelapses = $rowEC->current()->findDependentRowset('Application_Model_DbTable_EcLimitRelapse');
        $limitRelapsesArray = array();

        
        foreach ($limitRelapses as $row) {
            $lr = new Application_Model_EmergencyCase_EcLimitRelapse();
            $lr->setEcID_fk($row->ecID_fk)
                    ->setText($row->text)
                    ->setElrID($row->elrID);
            $limitRelapsesArray[] = $lr;
        }

        // Zugehoerige Risikosituationen holen
        $risks = $rowEC->current()->findDependentRowset('Application_Model_DbTable_EcRiskSituation');
        $riskArray = array();

        foreach ($risks as $row) {
            $risk = new Application_Model_EmergencyCase_EcRiskSituation();
            $risk->setEcID_fk($row->ecID_fk)
                    ->setText($row->text)
                    ->setErsID($row->ersID);
            $riskArray[] = $risk;
        }
        
        // Zugehoerige Schutzgedanken holen
        $safetyThouhts = $rowEC->current()->findDependentRowset('Application_Model_DbTable_EcSafetyThought');
        $safetyThouhtsArray = array();

        foreach ($safetyThouhts as $row) {
            $safetyThought = new Application_Model_EmergencyCase_EcSafetyThought();
            $safetyThought->setEcID_fk($row->ecID_fk)
                    ->setText($row->text)
                    ->setEstID($row->estID);
            $safetyThouhtsArray[] = $safetyThought;
        }

        // Zugehoerige Schutzhandlungen holen
        $safetyActions = $rowEC->current()->findDependentRowset('Application_Model_DbTable_EcSafetyAction');
        $safetyActionArray = array();

        foreach ($safetyActions as $row) {
            $safetyAction = new Application_Model_EmergencyCase_EcSafetyAction();
            $safetyAction->setEcID_fk($row->ecID_fk)
                    ->setText($row->text)
                    ->setEsaID($row->esaID);
            $safetyActionArray[] = $safetyAction;
        }
        
        // Zugehoerige HilfePersonen holen
        $helpPersons = $rowEC->current()->findDependentRowset('Application_Model_DbTable_EcHelpPerson');
        $helpPersonsArray = array();

        foreach ($helpPersons as $row) {
            $hp = new Application_Model_EmergencyCase_EcHelpPerson();
            $hp->setEhpID($row->ehpID)
                    ->setEcID_fk($row->ecID_fk)
                    ->setName($row->name)
                    ->setPhone_number($row->phone_number);

            $helpPersonsArray[] = $hp;
        }


        $rowEC = $rowEC->current();
        $emergencyCase = new Application_Model_EmergencyCase_EmergencyCase();
        $emergencyCase->setEcID($rowEC->ecID)
                ->setPatientID_fk($rowEC->patientID_fk)
                ->setAddict_drughotline($rowEC->addict_drughotline)
                ->setProp_advice_centre($rowEC->prop_advice_centre)
                ->setMy_therapist($rowEC->my_therapist)
                ->setRisk_danger($rowEC->risk_danger)
                ->setRisk_situation($rowEC->risk_situation)
                ->setRisk_temptation($rowEC->risk_temptation)
                ->setTemptation_thought($rowEC->temptation_thought)
                ->setTemptation_thought_abstinence($rowEC->temptation_thought_abstinence)
                ->setTemptation_behaviour($rowEC->temptation_behaviour);
        $emergencyCase->setHelp_persons_array($helpPersonsArray);
        $emergencyCase->setRisk_situations_array($riskArray);
        $emergencyCase->setLimit_relapses_array($limitRelapsesArray);
        $emergencyCase->setSafety_actions_array($safetyActionArray);
        $emergencyCase->setSafety_thoughts_array($safetyThouhtsArray);
        

        return $emergencyCase;
    }

    public function find($ecID, Application_Model_EmergencyCase_EmergencyCase $emergencycase = null) {
        $result = $this->getDbTable()->find($ecID);
        if ($emergencycase == null)
            return $result->current();

        if (0 == count($result)) {
            return;
        }
        $row = $result->current();

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
        $select->join('spl_emotion', 'spl_emotion.emotionID = spl_distraction.emotionID_fk', 'emotion');
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

    /*
     * speichert Datensaetze in den Tabellen
     * spl_ec_limit_relapse
     * spl_ec_risk_situation
     * spl_ec_safety_action
     * spl_ec_safety_thought
     * spl_ec_help_person
     */

    public function saveDependentRows(Application_Model_EmergencyCase_EmergencyCase $emergencycase) {
        // Alle zugehorigen Daten speichern

        $limitRelapses = $emergencycase->getLimit_relapses_array();
        if (count($limitRelapses) > 0) {
            foreach ($limitRelapses as $lr) {
                $lr->setEcID_fk($emergencycase->getEcID());
                $limitRelapseMapper = new Application_Model_EmergencyCase_EcLimitRelapseMapper();
                $limitRelapseMapper->save($lr);
            }
        }

        $riskSituations = $emergencycase->getRisk_situations_array();
        if (count($riskSituations) > 0) {
            foreach ($riskSituations as $lr) {
                $lr->setEcID_fk($emergencycase->getEcID());
                $limitRelapseMapper = new Application_Model_EmergencyCase_EcRiskSituationMapper();
                $limitRelapseMapper->save($lr);
            }
        }
        
        $safetyThoughts = $emergencycase->getSafety_thoughts_array();
        if (count($safetyThoughts) > 0) {
            foreach ($safetyThoughts as $st) {
                $st->setEcID_fk($emergencycase->getEcID());
                $safetyThoughtMapper = new Application_Model_EmergencyCase_EcSafetyThoughtMapper();
                $safetyThoughtMapper->save($st);
            }
        }
        
        $safetyActions = $emergencycase->getSafety_actions_array();
        if (count($safetyActions) > 0) {
            foreach ($safetyActions as $sa) {
                $sa->setEcID_fk($emergencycase->getEcID());
                $safetyActionMapper = new Application_Model_EmergencyCase_EcSafetyActionMapper();
                $safetyActionMapper->save($sa);
            }
        }

        $helpPersons = $emergencycase->getHelp_persons_array();
        if (count($helpPersons) > 0) {
            foreach ($helpPersons as $lr) {
                $lr->setEcID_fk($emergencycase->getEcID());
                $helpPersonMapper = new Application_Model_EmergencyCase_EcHelpPersonMapper();
                $helpPersonMapper->save($lr);
            }
        }
    }

}
