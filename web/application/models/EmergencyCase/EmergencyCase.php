<?php

class Application_Model_EmergencyCase_EmergencyCase {

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
    protected $_limit_relapses_array = array();
    protected $_risk_situations_array = array();
    protected $_help_persons_array = array();
    protected $_safety_thoughts_array = array();
    protected $_safety_actions_array = array();

    public function __construct(array $options = null) {
        if (is_array($options)) {
            $this->setOptions($options);
        }
    }

    public function __set($name, $value) {
        $method = 'set' . $name;
        if (('mapper' == $name) || !method_exists($this, $method)) {
            throw new Exception('Ungültige EmergencyCase Eigenschaft');
        }
        $this->$method($value);
    }

    public function __get($name) {
        $method = 'get' . $name;
        if (('mapper' == $name) || !method_exists($this, $method)) {
            throw new Exception('Ungültige EmergencyCase Eigenschaft');
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

    public function getLimit_relapses_array() {
        return $this->_limit_relapses_array;
    }

    public function setLimit_relapses_array($_limit_relapses_array) {
        if (!is_array($_limit_relapses_array)) {
            throw new Exception('Invalid risk_situation_array value!');
        }
        $this->_limit_relapses_array = array();
        foreach ($_limit_relapses_array as $value) {
            if ($value instanceof Application_Model_EmergencyCase_EcLimitRelapse) {
                array_push($this->_limit_relapses_array, $value);
            } else {
                $lr = new Application_Model_EmergencyCase_EcLimitRelapse();
                $lr->setText($value);
                array_push($this->_limit_relapses_array, $lr);
            }
        }
    }

    public function getRisk_situations_array() {
        return $this->_risk_situations_array;
    }

    public function setRisk_situations_array($_risk_situations_array) {

        if (!is_array($_risk_situations_array)) {
            throw new Exception('Invalid risk_situation_array value!');
        }
        $this->_risk_situations_array = array();
        foreach ($_risk_situations_array as $value) {
            if ($value instanceof Application_Model_EmergencyCase_EcRiskSituation) {
                array_push($this->_risk_situations_array, $value);
            } else {
                $rs = new Application_Model_EmergencyCase_EcRiskSituation();
                $rs->setText($value);
                array_push($this->_risk_situations_array, $rs);
            }
        }
    }

    public function getHelp_persons_array() {
        return $this->_help_persons_array;
    }

    public function setHelp_persons_array($_help_persons_array) {
        if (!is_array($_help_persons_array)) {
            throw new Exception('Invalid _help_persons_array value!');
        }
        $this->_help_persons_array = array();
        foreach ($_help_persons_array as $value) {
            if ($value instanceof Application_Model_EmergencyCase_EcHelpPerson) {
                array_push($this->_help_persons_array, $value);
            } else {
                $hp = new Application_Model_EmergencyCase_EcHelpPerson();
                // getValue der Klasse Custom_Form_Element_HelpPerson liefter namen+telefonnummer in einem String, seperator ist ein Semikolon
                list ($name, $phone_number) = split(';', $value);
                $hp->setName($name);
                $hp->setPhone_number($phone_number);
                array_push($this->_help_persons_array, $hp);
            }
        }
    }

    public function getSafety_thoughts_array() {
        return $this->_safety_thoughts_array;
    }

    public function getSafety_actions_array() {
        return $this->_safety_actions_array;
    }

    public function setSafety_thoughts_array($_safety_thoughts_array) {

        if (!is_array($_safety_thoughts_array)) {
            throw new Exception('Invalid risk_situation_array value!');
        }
        $this->_safety_thoughts_array = array();
        foreach ($_safety_thoughts_array as $value) {
            if ($value instanceof Application_Model_EmergencyCase_EcSafetyThought) {
                array_push($this->_safety_thoughts_array, $value);
            } else {
                $st = new Application_Model_EmergencyCase_EcSafetyThought();
                $st->setText($value);
                array_push($this->_safety_thoughts_array, $st);
            }
        }
    }

    public function setSafety_actions_array($_safety_actions_array) {
        if (!is_array($_safety_actions_array)) {
            throw new Exception('Invalid risk_situation_array value!');
        }
        $this->_safety_actions_array = array();
        foreach ($_safety_actions_array as $value) {
            if ($value instanceof Application_Model_EmergencyCase_EcSafetyAction) {
                array_push($this->_safety_actions_array, $value);
            } else {
                $sr = new Application_Model_EmergencyCase_EcSafetyAction();
                $sr->setText($value);
                array_push($this->_safety_actions_array, $sr);
            }
        }
    }

    public function getArray() {
        return array($this->getEcID(), $this->getPatientID_fk(), $this->getAddict_drughotline(), $this->getProp_advice_centre(), $this->getMy_therapist(), $this->getRisk_danger(),
            $this->getRisk_situation(), $this->getRisk_temptation(), $this->getTemptation_thought(), $this->getTemptation_thought_abstinence(), $this->getTemptation_behaviour());
    }

    public function getKeyValueArray() {
        $tmp = array(
            'ecID' => $this->getEcID(),
            'patientID_fk' => $this->getPatientID_fk(),
            'addict_drughotline' => $this->getAddict_drughotline(),
            'prop_advice_centre' => $this->getProp_advice_centre(),
            'my_therapist' => $this->getMy_therapist(),
            'risk_danger' => $this->getRisk_danger(),
            'risk_situation' => $this->getRisk_situation(),
            'risk_temptation' => $this->getRisk_temptation(),
            'temptation_thought' => $this->getTemptation_thought(),
            'temptation_thought_abstinence' => $this->getTemptation_thought_abstinence(),
            'temptation_behaviour' => $this->getTemptation_behaviour(),
        );
        foreach ($this->getRisk_situations_array() as $value) {
            $tmp['risk_situations_array'][] = $value->getKeyValueArray();
        }
        foreach ($this->getLimit_relapses_array() as $value) {
            $tmp['limit_relapses_array'][] = $value->getKeyValueArray();
        }
        foreach ($this->getSafety_thoughts_array() as $value) {
            $tmp['safety_thoughts_array'][] = $value->getKeyValueArray();
        }
        foreach ($this->getSafety_actions_array() as $value) {
            $tmp['safety_actions_array'][] = $value->getKeyValueArray();
        }
        foreach ($this->getHelp_persons_array() as $value) {
            $tmp['help_persons_array'][] = $value->getKeyValueArray();
        }

//            $tmp['help_persons_array'] => $this->getHelp_persons_array(),
//            $tmp['limit_relapses_array'] => $this->getLimit_relapses_array(),
//            $tmp['safety_thoughts_array'] => $this->getSafety_thoughts_array(),
//            $tmp['safety_actions_array'] => $this->getSafety_actions_array()


        return $tmp;
    }

}
