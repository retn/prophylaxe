<?php

/*
 * Formular zum anlegen/bearbeiten eines Patienten
 */

class Application_Form_Patient extends Zend_Form {

    public function init() {
        // Setzt die Methode for das Anzeigen des Formulars mit POST
        $this->setMethod('post');
        $this->setName("patient_form");

        $field = new Zend_Form_Element_Hidden('id');
        $field->setDecorators(array('ViewHelper'));
        $this->addElement($field);

//        $this->addElement('hidden', 'id', array(
//            'disableLoadDefaultDecorators' => true
//        ));
        $field = new Zend_Form_Element_Hidden('userID_fk');
        $field->setDecorators(array('ViewHelper'));
        $this->addElement($field);
//        $this->addElement('hidden', 'userID_fk', array(
//        ));
        // Ein Email Element hinzufügen
        $this->addElement('text', 'firstname', array(
            'label' => 'Vorname:',
            'required' => true,
            'filters' => array('StringTrim'),
            'class' => array('form-spl'),
            'validators' => array(
                array('validator' => 'StringLength', 'options' => array(3, 45))
            )
        ));
        $this->getElement('firstname')->setDecorators(array('ViewHelper', 'Errors', 'Label', array('HtmlTag', array('tag' => '<div>'))));

        // Das Kommentar Element hinzufügen
        $this->addElement('text', 'lastname', array(
            'label' => 'Nachname:',
            'required' => true,
            'class' => array('form-spl'),
            'validators' => array(
                array('validator' => 'StringLength', 'options' => array(0, 20))
            )
        ));
        $this->getElement('lastname')->setDecorators(array('ViewHelper', 'Errors', 'Label', array('HtmlTag', array('tag' => '<div>'))));

        $this->addElement('text', 'username', array(
            'label' => 'Loginname:',
            'required' => true,
            'class' => array('form-spl'),
            'validators' => array(
                array('validator' => 'StringLength', 'options' => array(0, 20))
            )
        ));
        $this->getElement('username')->setDecorators(array('ViewHelper', 'Label', array('HtmlTag', array('tag' => 'div')), 'Errors'));

        $elem = new ZendX_JQuery_Form_Element_DatePicker(
                "birthdate", array("label" => "Geburtsdatum:",
            'required' => true,
            'class' => array('form-spl'),
            'validators' => array(
                array('validator' => 'Date', 'options' => array('format' => 'yyyy-mm-dd'))
            ))
        );
        $elem->setJQueryParam('dateFormat', 'yy-mm-dd');
        $this->addElement($elem);

        $this->getElement('birthdate')->setDecorators($this::$formJQueryElements);

//        $this->getElement('birthdate')->setDecorator('HtmlTag', array('tag' => '<div>'));
//        $this->addElement('text', 'birthdate', array(
//            'label' => 'Geburtsdatum:',
//            'required' => true,
//            'validators' => array(
//                array('validator' => 'Date', 'options' => array('format' => 'yyyy-mm-dd'))
//            )
//        ));

        $this->addElement('text', 'email', array(
            'label' => 'Email:',
            'required' => true,
            'class' => array('form-spl'),
            'validators' => array(
                'EmailAddress',
            )
        ));
        $this->getElement('email')->setDecorators(array('ViewHelper', 'Label', 'Errors', array('HtmlTag', array('tag' => 'div'))));

        $this->addElement('text', 'token', array(
            'label' => 'Token:',
            'required' => false,
            'readonly' => 'readonly',
            'class' => array('form-spl')
           
        ));
        $this->getElement('token')->setDecorators(array('ViewHelper', 'Label', array('HtmlTag', array('tag' => 'div')), 'Errors'));

        // Den Submit Button hinzufügen
        $this->addElement('submit', 'submit', array(
            'ignore' => true,
            'label' => 'Patient anlegen',
        ));
//        $this->getElement('submit')->removeDecorator('HtmlTag');
//        $this->getElement('submit')->addDecorator('HtmlTag', array('tag' => 'li'));
        $this->getElement('submit')->setDecorators(array('ViewHelper', 'Errors', array('HtmlTag', array('tag' => 'div'))));

        $this->addElement('reset', 'reset', array(
            'ignore' => true,
            'label' => 'Daten löschen',
        ));
        $this->getElement('reset')->setDecorators(array('ViewHelper', 'Errors', array('HtmlTag', array('tag' => '<div>'))));

//        $this->setDecorators(array(
//            'FormElements',
//            array('HtmlTag', array('tag' => 'div')),
//            'Form'
//        ));
        // Und letztendlich etwas CSRF Protektion hinzufügen
//        $this->addElement('hash', 'csrf', array(
//            'ignore' => true,
//        ));
    }

    public static $formJQueryElements = array(
        array('UiWidgetElement', array('tag' => '')), // it necessary to include for jquery elements
        array('Errors'),
        array('Description', array('tag' => 'span')),
        array('HtmlTag', array('tag' => 'td')),
//        array('Label', array('tag' => 'td', 'class' => 'element')),
        array(array('row' => 'HtmlTag'), array('tag' => 'div')),
    );

}
