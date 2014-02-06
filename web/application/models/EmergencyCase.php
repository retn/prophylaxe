<?php

class Application_Model_EmergencyCase {

    protected $_ecID;
    protected $_patientID_fk;
    protected $_addict_drughotline;
    protected $_prop_advice_centre;
    protected $_my_therapist;
    protected $_risk_danger;
    protected $_risk_situation;
    protected $_risk_temptation;
    protected $_temptation_thought;
    protected $_temptation_thought_abstinence;
    protected $_temptation_behaviour;

    public function __construct(array $options = null) {
        if (is_array($options)) {
            $this->setOptions($options);
        }
    }

    public function __set($name, $value) {
        $method = 'set' . $name;
        if (('mapper' == $name) || !method_exists($this, $method)) {
            throw new Exception('Ungültige Patient Eigenschaft');
        }
        $this->$method($value);
    }

    public function __get($name) {
        $method = 'get' . $name;
        if (('mapper' == $name) || !method_exists($this, $method)) {
            throw new Exception('Ungültige Patient Eigenschaft');
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

    public function getEcID() {
        return $this->_ecID;
    }

    public function setEcID($_ecID) {
        if ($_ecID > 0) {
            $this->_ecID = (int) $_ecID;
        } else {
            $this->_ecID = null;
        }
        return $this;
    }

    public function getPatientID_fk() {
        return $this->_patientID_fk;
    }

    public function setPatientID_fk($_patientID_fk) {
        $this->_patientID_fk = $_patientID_fk;
        return $this;
    }

    public function getAddict_drughotline() {
        return $this->_addict_drughotline;
    }

    public function setAddict_drughotline($_addict_drughotline) {
        $this->_addict_drughotline = $_addict_drughotline;
        return $this;
    }

    public function getProp_advice_centre() {
        return $this->_prop_advice_centre;
    }

    public function setProp_advice_centre($_prop_advice_centre) {
        $this->_prop_advice_centre = $_prop_advice_centre;
        return $this;
    }

    public function getMy_therapist() {
        return $this->_my_therapist;
    }

    public function setMy_therapist($_my_therapist) {
        $this->_my_therapist = $_my_therapist;
        return $this;
    }

    public function getRisk_danger() {
        return $this->_risk_danger;
    }

    public function setRisk_danger($_risk_danger) {
        $this->_risk_danger = $_risk_danger;
        return $this;
    }

    public function getRisk_situation() {
        return $this->_risk_situation;
    }

    public function setRisk_situation($_risk_situation) {
        $this->_risk_situation = $_risk_situation;
        return $this;
    }

    public function getRisk_temptation() {
        return $this->_risk_temptation;
    }

    public function setRisk_temptation($_risk_temptation) {
        $this->_risk_temptation = $_risk_temptation;
        return $this;
    }

    public function getTemptation_thought() {
        return $this->_temptation_thought;
    }

    public function setTemptation_thought($_temptation_thought) {
        $this->_temptation_thought = $_temptation_thought;
        return $this;
    }

    public function getTemptation_thought_abstinence() {
        return $this->_temptation_thought_abstinence;
    }

    public function setTemptation_thought_abstinence($_temptation_thought_abstinence) {
        $this->_temptation_thought_abstinence = $_temptation_thought_abstinence;
        return $this;
    }

    public function getTemptation_behaviour() {
        return $this->_temptation_behaviour;
    }

    public function setTemptation_behaviour($_temptation_behaviour) {
        $this->_temptation_behaviour = $_temptation_behaviour;
        return $this;
    }

    public function getArray() {
        return array($this->getEcID(), $this->getPatientID_fk(), $this->getAddict_drughotline(), $this->getProp_advice_centre(), $this->getMy_therapist(), $this->getRisk_danger(),
            $this->getRisk_situation(), $this->getRisk_temptation(), $this->getTemptation_thought(), $this->getTemptation_thought_abstinence(), $this->getTemptation_behaviour());
    }

}

