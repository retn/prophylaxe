<?php

class Application_Model_User {

    protected $_id;
    protected $_user_role;
    protected $_user_name;
    protected $_email;

    public function __construct(array $options = null) {
        if (is_array($options)) {
            $this->setOptions($options);
        }
    }

    public function __set($name, $value) {
        $method = 'set' . $name;
        if (('mapper' == $name) || !method_exists($this, $method)) {
            throw new Exception('Ungültige User Set Eigenschaft');
        }
        $this->$method($value);
    }

    public function __get($name) {
        $method = 'get' . $name;
        if (('mapper' == $name) || !method_exists($this, $method)) {
            throw new Exception('Ungültige User Get Eigenschaft');
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

    public function setUserID($id) {
        $this->_id = (int) $id;
        return $this;
    }

    public function getUserID() {
        return $this->_id;
    }

    public function setUser_role($userrole) {
        $this->_user_role = (string) $userrole;
        return $userrole;
    }

    public function getUser_role() {
        return $this->_user_role;
    }

    public function setUser_name($username) {
        $this->_user_name = (string) $username;
        return $username;
    }

    public function getUser_name() {
        return $this->_user_name;
    }

    public function setEmail($email) {
        $this->_email = $email;
        return $this;
    }

    public function getEmail() {
        return $this->_email;
    }

}

