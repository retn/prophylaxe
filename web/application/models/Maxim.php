<?php

class Application_Model_Maxim {

    protected $_maximID;
    protected $_text;

    public function __construct(array $options = null) {
        if (is_array($options)) {
            $this->setOptions($options);
        }
    }

    public function __set($name, $value) {
        $method = 'set' . $name;
        if (('mapper' == $name) || !method_exists($this, $method)) {
            throw new Exception('Ungültige Maxim Eigenschaft');
        }
        $this->$method($value);
    }

    public function __get($name) {
        $method = 'get' . $name;
        if (('mapper' == $name) || !method_exists($this, $method)) {
            throw new Exception('Ungültige Maxim Eigenschaft');
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

    public function setText($text) {
        $this->_text = (string) $text;
        return $this;
    }

    public function getText() {
        return $this->_text;
    }

    public function setId($id) {
        if ($id > 0) {
            $this->_maximID = (int) $id;
        } else {
            $this->_maximID = null;
        }
        return $this;
    }

    public function getId() {
        return $this->_maximID;
    }

    public function getArray() {
        return array($this->getId(), $this->getText());
    }

    public function getKeyValueArray() {
        return array('maximID' => $this->getId(), 'text' => $this->getText());
    }

}
