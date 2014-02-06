<?php

class Application_Model_EmotionMapper {

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
            $this->setDbTable('Application_Model_DbTable_Emotion');
        }
        return $this->_dbTable;
    }

    public function save(Application_Model_Emotion $emotion) {
        $data = array(
            'emotionID' => $emotion->getId(),
            'emotion' => $emotion->getEmotion(),
        );

        if (null === ($id = $emotion->getId())) {
            unset($data['id']);
            $this->getDbTable()->insert($data);
        } else {
            $this->getDbTable()->update($data, array('emotionID = ?' => $id));
        }
    }

    public function find($id, Application_Model_Emotion $emotion) {
        $result = $this->getDbTable()->find($id);
        if (0 == count($result)) {
            return;
        }
        $row = $result->current();
        $emotion->setId($row->emotionID)
                ->setEmotion($row->emotion);
    }

    public function fetchAll() {
        $resultSet = $this->getDbTable()->fetchAll();
        $entries = array();
        foreach ($resultSet as $row) {
            $entry = new Application_Model_Emotion();
            $entry->setId($row->emotionID)
                    ->setEmotion($row->emotion);
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
