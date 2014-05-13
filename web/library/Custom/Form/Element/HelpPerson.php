<?php

/*
 * http://www.zendcasts.com/preparing-custom-elements-for-zend-validators/2010/03/
 */

class Custom_Form_Element_HelpPerson extends Zend_Form_Element_Xhtml {

    protected $_dateFormat = '%year%-%month%-%day%';
    protected $_namePerson = null;
    protected $_phoneNumber = null;
    protected $_seperator = ';';
    

    public function __construct($spec, $options = null) {
        $this->addPrefixPath(
                'Custom_Form_Decorator', 'Custom/Form/Decorator', 'decorator'
        );
        parent::__construct($spec, $options);
    }

    public function loadDefaultDecorators() {
        if ($this->loadDefaultDecoratorsIsDisabled()) {
            return;
        }

        $decorators = $this->getDecorators();
        if (empty($decorators)) {
            $this->addDecorator('HelpPerson')
                    ->addDecorator('Errors')
                    ->addDecorator('Description', array(
                        'tag' => 'p',
                        'class' => 'description')
            );
//                    ->addDecorator('HtmlTag', array(
//                        'tag' => 'dd',
//                        'id' => $this->getNamePerson() . '-element')
//                    );
//                    ->addDecorator('Label', array('tag' => 'dt'));
        }
    }

    public function setNamePerson($value) {
        $this->_namePerson = $value;
        return $this;
    }

    public function getNamePerson() {
        return $this->_namePerson;
    }

    public function setPhoneNumber($value) {
        $this->_phoneNumber = $value;
        return $this;
    }

    public function getPhoneNumber() {
        return $this->_phoneNumber;
    }

//    public function isValid($value, $context = null) {
//        
//        return parent::isValid($value, $context);
//    }

    public function setValue($value) {

        if (is_array($value)) {
            $this->setNamePerson($value['name']);
            $this->setPhoneNumber($value['phonenumber']);
        } else {
            throw new Exception('Invalid date value provided');
        }

        return $this;
    }

    public function getValue() {
        if (empty($this->_namePerson) || empty($this->_phoneNumber)) {

            return false;
        }
        return $this->getNamePerson() . $this->_seperator . $this->getPhoneNumber();

//        return str_replace(
//            array('%year%', '%month%', '%day%'),
//            array($this->getYear(), $this->getPhoneNumber(), $this->getName()),
//            $this->_dateFormat
//        );
    }

}
