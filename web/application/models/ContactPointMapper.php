<?php

class Application_Model_ContactPointMapper {

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
            $this->setDbTable('Application_Model_DbTable_ContactPoint');
        }
        return $this->_dbTable;
    }

    public function save(Application_Model_ContactPoint $contactpoint) {
        $data = array(
            'cpID' => $contactpoint->getCpID(),
            'name' => $contactpoint->getName(),
            'street' => $contactpoint->getStreet(),
            'plz' => $contactpoint->getPlz(),
            'town' => $contactpoint->getTown(),
            'phone_number' => $contactpoint->getPhone_number(),
            'email' => $contactpoint->getEmail(),
        );

        if (null === ($id = $contactpoint->getCpID())) {
            unset($data['cpID']);
            $this->getDbTable()->insert($data);
        } else {
            $this->getDbTable()->update($data, array('cpID = ?' => $id));
        }
    }

    public function find($id, Application_Model_ContactPoint $contactpoint) {
        $result = $this->getDbTable()->find($id);
        if (0 == count($result)) {
            return;
        }
        $row = $result->current();

        $contactpoint->setCpID($row->cpID)
                ->setName($row->name)
                ->setStreet($row->street)
                ->setPlz($row->plz)
                ->setTown($row->town)
                ->setPhone_number($row->phone_number)
                ->setEmail($row->email);
    }

    public function fetchAll() {
        $entries = array();
        $rows = $this->getDbTable()->fetchAll();

        foreach ($rows as $row) {
            $entry = new Application_Model_ContactPoint();
            $entry->setCpID($row->cpID)
                    ->setName($row->name)
                    ->setStreet($row->street)
                    ->setPlz($row->plz)
                    ->setTown($row->town)
                    ->setPhone_number($row->phone_number)
                    ->setEmail($row->email);
            $entries[] = $entry;
        }
        return $entries;
    }

    public function delete($id) {
        $contactpointRowset = $this->getDbTable()->find($id);
        $contactpoint = $contactpointRowset->current();

        $anzDeletedRows = $contactpoint->delete();

        return $anzDeletedRows;
    }

}

