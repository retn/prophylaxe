<?php

class Application_Model_EmergencyCase_EcHelpPerson {

    protected $_ehpID;
    protected $_name;
    protected $_phone_number;
    protected $_ecID_fk;

    public function __construct(array $options = null) {
        if (is_array($options)) {
            $this->setOptions($options);
        }
    }

    public function __set($name, $value) {
        $method = 'set' . $name;
        if (('mapper' == $name) || !method_exists($this, $method)) {
            throw new Exception('Ungültige EcHelpPerson Eigenschaft');
        }
        $this->$method($value);
    }

    public function __get($name) {
        $method = 'get' . $name;
        if (('mapper' == $name) || !method_exists($this, $method)) {
            throw new Exception('Ungültige EcHelpPerson Eigenschaft');
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

    public function getEhpID() {
        return $this->_ehpID;
    }

    public function setEhpID($_ehpid) {
        if ($_ehpid > 0) {
            $this->_ehpID = (int) $_ehpid;
        } else {
            $this->_ehpID = null;
        }
        return $this;
    }

    public function getName() {
        return $this->_name;
    }

    public function setName($_name) {
        $this->_name = $_name;
        return $this;
    }

    public function getEcID_fk() {
        return $this->_ecID_fk;
    }

    public function setEcID_fk($_ecID_fk) {
        $this->_ecID_fk = $_ecID_fk;
        return $this;
    }

    public function getPhone_number() {
        return $this->_phone_number;
    }

    public function setPhone_number($_phone_number) {
        $this->_phone_number = $_phone_number;
    }

    public function getKeyValueArray() {
        return array('name' => $this->getName(), 'phone_number' => $this->getPhone_number());
    }

}
