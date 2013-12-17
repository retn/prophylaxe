<?php

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
            'class' => array('form-control'),
            'validators' => array(
                array('validator' => 'StringLength', 'options' => array(3, 45))
            )
        ));

        // Das Kommentar Element hinzufügen
        $this->addElement('text', 'lastname', array(
            'label' => 'Nachname:',
            'required' => true,
            'class' => array('form-control'),
            'validators' => array(
                array('validator' => 'StringLength', 'options' => array(0, 20))
            )
        ));

        $this->addElement('text', 'username', array(
            'label' => 'Loginname:',
            'required' => true,
            'class' => array('form-control'),
            'validators' => array(
                array('validator' => 'StringLength', 'options' => array(0, 20))
            )
        ));

        $elem = new ZendX_JQuery_Form_Element_DatePicker(
                "birthdate", array("label" => "Geburtsdatum:",
            'required' => true,
            'class' => array('form-control'),
            'validators' => array(
                array('validator' => 'Date', 'options' => array('format' => 'yyyy-mm-dd'))
            ))
        );
        $elem->setJQueryParam('dateFormat', 'yy-mm-dd');
        $this->addElement($elem);

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
            'class' => array('form-control'),
            'validators' => array(
                'EmailAddress',
            )
        ));


        // Den Submit Button hinzufügen
        $this->addElement('submit', 'submit', array(
            'ignore' => true,
            'label' => 'Patient anlegen',
        ));

        $this->addElement('reset', 'reset', array(
            'ignore' => true,
            'label' => 'Daten löschen',
        ));


        // Und letztendlich etwas CSRF Protektion hinzufügen
//        $this->addElement('hash', 'csrf', array(
//            'ignore' => true,
//        ));
    }

}

