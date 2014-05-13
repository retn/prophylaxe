<?php

class Application_Model_EmergencyCase_EcLimitRelapse {

    protected $_elrID;
    protected $_text;
    protected $_ecID_fk;

    public function __construct(array $options = null) {
        if (is_array($options)) {
            $this->setOptions($options);
        }
    }

    public function __set($name, $value) {
        $method = 'set' . $name;
        if (('mapper' == $name) || !method_exists($this, $method)) {
            throw new Exception('Ungültige EcRiskSituation Eigenschaft');
        }
        $this->$method($value);
    }

    public function __get($name) {
        $method = 'get' . $name;
        if (('mapper' == $name) || !method_exists($this, $method)) {
            throw new Exception('Ungültige EcRiskSituation Eigenschaft');
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

    public function getElrID() {
        return $this->_elrID;
    }

    public function setElrID($_elrid) {
        if ($_elrid > 0) {
            $this->_elrID = (int) $_elrid;
        } else {
            $this->_elrID = null;
        }
        return $this;
    }

    public function getText() {
        return $this->_text;
    }

    public function setText($_text) {
        $this->_text = $_text;
        return $this;
    }

    public function getEcID_fk() {
        return $this->_ecID_fk;
    }

    public function setEcID_fk($_ecID_fk) {
        $this->_ecID_fk = $_ecID_fk;
        return $this;
    }

    public function getArray() {
        return array($this->getElrID(), $this->getText(), $this->getEcID_fk());
    }

    public function getKeyValueArray() {
        return array('elrID' => $this->getElrID(), 'text' => $this->getText(), 'ecID_fk' => $this->getEcID_fk());
    }

}
