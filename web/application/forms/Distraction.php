<?php

class Application_Form_Distraction extends Zend_Form {

    public function init() {
        $this->setMethod('post');

        $this->addElement('hidden', 'id', array(
        ));


        $this->addElement('textarea', 'text', array(
            'label' => 'Möglichkeit:',
            'required' => true,
            'class' => array(),
            'placeholder' => 'Möglichkeit eingeben',
            'cols' => '70',
            'rows' => '5',
            'validators' => array(
                array('NotEmpty', true, array('messages' => array('isEmpty' => 'Das Feld <b>Möglichkeit</b> darf nicht leer sein.'))),
            ),
        ));
    }

}

