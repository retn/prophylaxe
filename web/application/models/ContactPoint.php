<?php

class Application_Model_ContactPoint {

    protected $_cpID;
    protected $_name;
    protected $_street;
    protected $_plz;
    protected $_town;
    protected $_phone_number;
    protected $_email;

    public function __construct(array $options = null) {
        if (is_array($options)) {
            $this->setOptions($options);
        }
    }

    public function __set($name, $value) {
        $method = 'set' . $name;
        if (('mapper' == $name) || !method_exists($this, $method)) {
            throw new Exception('Ungültige ContactPoint Eigenschaft');
        }
        $this->$method($value);
    }

    public function __get($name) {
        $method = 'get' . $name;
        if (('mapper' == $name) || !method_exists($this, $method)) {
            throw new Exception('Ungültige ContactPoint Eigenschaft');
        }
        return $this->$method();
    }

    public function setOptions(array $options) {
        $methods = get_class_methods($this);
        foreach ($options as $key => $value) {
            $method = 'set' . ucfirst($key);
            if (in_array($method, $methods)) {
                $this->$method($value);
            }
        }
        return $this;
    }

    public function setCpID($id) {
        if ($id > 0) {
            $this->_cpID = (int) $id;
        } else {
            $this->_cpID = null;
        }
        return $this;
    }

    public function getCpID() {
        return $this->_cpID;
    }

    public function getName() {
        return $this->_name;
    }

    public function setName($name) {
        $this->_name = (string) $name;
        return $this;
    }

    public function getStreet() {
        return $this->_street;
    }

    public function setStreet($_street) {
        $this->_street = (string) $_street;
        return $this;
    }

    public function getPlz() {
        return $this->_plz;
    }

    public function setPlz($_plz) {
        $this->_plz = (string) $_plz;
        return $this;
    }

    public function getTown() {
        return $this->_town;
    }

    public function setTown($_town) {
        $this->_town = (string) $_town;
        return $this;
    }

    public function getPhone_number() {
        return $this->_phone_number;
    }

    public function setPhone_number($_phone_number) {
        $this->_phone_number = (string) $_phone_number;
        return $this;
    }

    public function getEmail() {
        return $this->_email;
    }

    public function setEmail($_email) {
        $this->_email = (string) $_email;
        return $this;
    }

    public function getArray() {
        return array($this->getCpID(), $this->getName(), $this->getStreet(), $this->getPlz(), $this->getTown(), $this->getPhone_number(), $this->getEmail());
    }

    public function getKeyValueArray() {
        return array(
            'cpID' => $this->getCpID(),
            'name' => $this->getName(),
            'street' => $this->getStreet(),
            'plz' => $this->getPlz(),
            'town' => $this->getTown(),
            'phone_number' => $this->getPhone_number(),
            'email' => $this->getEmail(),
        );
    }

}

