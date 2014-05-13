<?php

class Application_Model_MaximHasPatient {

    protected $_maxim_maximID;
    protected $_patient_patientID;

    public function __construct(array $options = null) {
        if (is_array($options)) {
            $this->setOptions($options);
        }
    }

    public function __set($name, $value) {
        $method = 'set' . $name;
        if (('mapper' == $name) || !method_exists($this, $method)) {
            throw new Exception('Ungültige MaximHasPatient Eigenschaft');
        }
        $this->$method($value);
    }

    public function __get($name) {
        $method = 'get' . $name;
        if (('mapper' == $name) || !method_exists($this, $method)) {
            throw new Exception('Ungültige MaximHasPatient Eigenschaft');
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

    public function getMaximid() {
        return $this->_maxim_maximID;
    }

    public function setMaximid($maxim_maximID) {
        $this->_maxim_maximID = $maxim_maximID;
        return $this;
    }

    public function getPatientid() {
        return $this->_patient_patientID;
    }

    public function setPatientid($patient_patientID) {
        $this->_patient_patientID = $patient_patientID;
        return $this;
    }

}

