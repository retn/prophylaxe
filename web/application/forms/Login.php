<?php

class Application_Form_Login extends Zend_Form {

    public function init() {





        $this->setMethod('post');

        $this->addElement('text', 'username', array(
            'label' => 'Username:',
            'required' => true,
            'filters' => array('StringTrim'),
            'class' => 'form-control',
            'placeholder' => 'Benutzername'
        ));

        $this->addElement('password', 'password', array(
            'label' => 'Password:',
            'required' => true,
            'class' => array('form-control', 'login_form_password'),
            'placeholder' => 'Passwort'
        ));

        $this->addElement('submit', 'submit', array(
            'ignore' => true,
            'label' => 'Login',
            'class' => 'btn btn-lg btn-primary btn-block',
        ));

        /* Labels entfernen */
         
        foreach ($this->getElements() as $element) {
            $element->removeDecorator('Label');
        }
    }

}

