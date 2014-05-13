<?php

/*
 * Formular fuer Anlaufstellen
 */
class Application_Form_ContactPoint extends Zend_Form {

    public function init() {
        // Setzt die Methode for das Anzeigen des Formulars mit POST
        $this->setMethod('post');
        $this->setName("contactpoint_form");

        $field = new Zend_Form_Element_Hidden('cpID');
        $field->setDecorators(array('ViewHelper'));
        $this->addElement($field);


        // Name
        $this->addElement('text', 'name', array(
            'label' => 'Name:',
            'required' => true,
            'filters' => array('StringTrim'),
            'class' => array(''),
            'validators' => array(
                array('validator' => 'StringLength', 'options' => array(3, 45))
            )
        ));

        // Strasse
        $this->addElement('text', 'street', array(
            'label' => 'Strasse:',
            'required' => true,
            'class' => array(''),
            'validators' => array(
                array('validator' => 'StringLength', 'options' => array(0, 20))
            )
        ));

        //PLZ
        $this->addElement('text', 'plz', array(
            'label' => 'PLZ:',
            'required' => true,
            'class' => array(''),
            'validators' => array(
                array('validator' => 'StringLength', 'options' => array(5, 20))
            )
        ));

        // Stadt
        $this->addElement('text', 'town', array(
            'label' => 'Stadt:',
            'required' => true,
            'class' => array(''),
            'validators' => array(
                array('validator' => 'StringLength', 'options' => array(3, 20))
            )
        ));

        // Telefonnummer
        $this->addElement('text', 'phone_number', array(
            'label' => 'Telefonnummer:',
            'required' => true,
            'class' => array(''),
            'validators' => array(
                array('validator' => 'StringLength', 'options' => array(3, 20))
            )
        ));

        // Email
        $this->addElement('text', 'email', array(
            'label' => 'Email:',
            'required' => false,
            'class' => array(''),
            'validators' => array(
                'EmailAddress'
            )
        ));

        // Den Submit Button hinzufügen
        $this->addElement('submit', 'submit', array(
            'ignore' => true,
            'label' => 'Anlaufstelle anlegen',
        ));

        $this->addElement('reset', 'reset', array(
            'ignore' => true,
            'label' => 'Eingaben löschen',
        ));
    }

}

