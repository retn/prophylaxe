<?php

class Application_Model_EmergencyCase_EcRiskSituation {

    protected $_ersID;
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

    public function getErsID() {
        return $this->_ersID;
    }

    public function setErsID($_ersid) {
        if ($_ersid > 0) {
            $this->_ersID = (int) $_ersid;
        } else {
            $this->_ersID = null;
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
        return array($this->getErsID(), $this->getText(), $this->getEcID_fk());
    }
    
    public function getKeyValueArray() {
        return array('ersID'=>$this->getErsID(),'text'=> $this->getText());
    }

}
