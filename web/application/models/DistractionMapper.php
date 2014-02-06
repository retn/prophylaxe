<?php

class Application_Model_DistractionMapper {

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
            $this->setDbTable('Application_Model_DbTable_Distraction');
        }
        return $this->_dbTable;
    }

    public function save(Application_Model_Distraction $distraction) {
        $data = array(
            'distractionID' => $distraction->getId(),
            'text' => $distraction->getText(),
            'emotionID_fk' => $distraction->getEmotion(),
        );

        if (null === ($id = $distraction->getId())) {
            unset($data['id']);
            $this->getDbTable()->insert($data);
        } else {
            $this->getDbTable()->update($data, array('distractionID = ?' => $id));
        }
    }

    public function find($id, Application_Model_Distraction $distraction) {
        $result = $this->getDbTable()->find($id);
        if (0 == count($result)) {
            return;
        }
        $row = $result->current();
        $emotion = $row->findDependentRowset('Application_Model_DbTable_Emotion');
        $distraction->setId($row->distractionID)
                ->setText($row->text)
                ->setEmotion($row->emotionID_fk)
                ->setEmotionText($emotion->emotion);
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
        $distractionRowset = $this->getDbTable()->find($id);
        $distraction = $distractionRowset->current();

        $anzDeletedRows = $distraction->delete();

        return $anzDeletedRows;
    }

}
