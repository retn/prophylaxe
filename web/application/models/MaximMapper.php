<?php

class Application_Model_MaximMapper {

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
            $this->setDbTable('Application_Model_DbTable_Maxim');
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

    public function find($id, Application_Model_Maxim $maxim) {
        $result = $this->getDbTable()->find($id);
        if (0 == count($result)) {
            return;
        }
        $row = $result->current();
        $maxim->setId($row->maximID)
                ->setText($row->text);
    }

    public function fetchAll() {
        $resultSet = $this->getDbTable()->fetchAll();
        $entries = array();
        foreach ($resultSet as $row) {
            $entry = new Application_Model_Maxim();
            $entry->setId($row->maximID)
                    ->setText($row->text);
            $entries[] = $entry;
        }
        return $entries;
    }

    public function delete($id) {

        $maximRowset = $this->getDbTable()->find($id);
        $maxim = $maximRowset->current();

        $anzDeletedRows = $maxim->delete();
        
        return $anzDeletedRows;      
       
//        $this->getDbTable()->delete(array('maximID = ?' => $id));
    }

}
