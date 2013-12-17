<?php

class Application_Model_Patient {

    protected $_id;
    protected $_first_name;
    protected $_last_name;
    protected $_user_name;
    protected $_birth_date;
    protected $_email;
    protected $_userID_fk;

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

    public function setLastname($lastname) {
        $this->_last_name = (string) $lastname;
        return $this;
    }

    public function getLastname() {
        return $this->_last_name;
    }

    public function setFirstname($firstname) {
        $this->_first_name = (string) $firstname;
        return $this;
    }

    public function getFirstname() {
        return $this->_first_name;
    }

    public function setUsername($username) {
        $this->_user_name = $username;
        return $this;
    }

    public function getUsername() {
        return $this->_user_name;
    }

    public function setBirthdate($birthdate) {
        $this->_birth_date = $birthdate;
        return $this;
    }

    public function getBirthdate() {
        return $this->_birth_date;
    }

    public function setEmail($email) {
        $this->_email = $email;
        return $this;
    }

    public function getEmail() {
        return $this->_email;
    }

    public function setId($id) {
        if (strlen($id) < 1) {
            $this->_id = null;
        } else {
            $this->_id = (int) $id;
        }
        return $this;
    }

    public function getId() {
        return $this->_id;
    }

    public function setUserID_fk($userid_fk) {
        $this->_userID_fk = (int) $userid_fk;
        return $this;
    }

    public function getUserID_fk() {
        return $this->_userID_fk;
    }

    /*
     * Liefert die Daten des Objekts als Array zurueck fuer jDataTable
     */

    public function getPatientTableArray() {
        return array($this->getId(), $this->getFirstname(), $this->getLastname(), $this->getBirthdate(), $this->getEmail(), $this->getUsername());
    }

    /*
     * Liefert die Daten des Objekts als Array zurueck
     */

    public function getKeyValueArray() {
        return array(
            'id' => $this->getId(),
            'firstname' => $this->getFirstname(),
            'lastname' => $this->getLastname(),
            'username' => $this->getUsername(),
            'birthdate' => $this->getBirthdate(),
            'email' => $this->getEmail(),
            'userID_fk' => $this->getUserID_fk());
    }

}

