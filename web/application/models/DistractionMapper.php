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
        $distraction->setId($row->distractionID)
                ->setFirstname($row->text);
    }

    public function fetchAll() {
        $resultSet = $this->getDbTable()->fetchAll();
        $entries = array();
        foreach ($resultSet as $row) {
            $entry = new Application_Model_Distraction();
            $entry->setId($row->distractionID)
                    ->setText($row->text);
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
