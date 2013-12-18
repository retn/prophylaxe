<?php

class Application_Form_Maxim extends Zend_Form {

    public function init() {
        $this->setMethod('post');

        $this->addElement('hidden', 'id', array(
        ));


        $this->addElement('textarea', 'text', array(
            'label' => 'Spruch:',
            'required' => true,
            'class' => array(),
            'placeholder' => 'Spruch eingeben',
            'cols' => '70',
            'rows' => '5',
            'validators' => array(
                array('NotEmpty', true, array('messages' => array('isEmpty' => 'Das Feld <b>Spruch</b> darf nicht leer sein.'))),
            ),
        ));

//        $this->setRequired(true)
//         ->addValidators(array(array('NotEmpty', true, 
//         array('messages' => array(
//                'isEmpty' => 'Please enter a password.',
//            ))),
//            'Alnum',
//            array('StringLength', false, array(4, 20)),
//         ))
//         ->addFilter('StringToLower');





        /* Labels entfernen */

//        foreach ($this->getElements() as $element) {
//            $element->removeDecorator('Label');
//        }
    }

}
