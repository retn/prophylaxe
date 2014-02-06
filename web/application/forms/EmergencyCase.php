<?php

class Application_Form_EmergencyCase extends Zend_Form {

    protected $_counter = 2;

    public function addRiskSituation($value) {
        $name = 'pers_risk_situation_' . $this->_counter;
        $this->_counter++;

//        $this->removeElement($name);

        $decorator = new Custom_Decorator_RiskSituation();
        $this->addElement('text', $name, array(
            'required' => true,
            'value' => $value,
            'belongsTo' => 'risk_situation_array',
            'filters' => array('StringTrim'),
            'class' => array('form-control'),
            'validators' => array(
                array('validator' => 'StringLength', 'options' => array(3, 45))
            ),
            'decorators' => array($decorator,'Errors', 'DtDdWrapper'),
        ));

        $this->getDisplayGroup('risk_situations')->addElement($this->getElement($name));
    }

    public function init() {
        // Setzt die Methode for das Anzeigen des Formulars mit POST
        $this->setMethod('post');
        $this->setName("emergencycase_form");

        // EmergencyCAse ID
        $field = new Zend_Form_Element_Hidden('ecID');
        $field->setDecorators(array('ViewHelper'));
        $this->addElement($field);

        // Patient ID
        $field = new Zend_Form_Element_Hidden('patientID_fk');
        $field->setDecorators(array('ViewHelper'));
        $this->addElement($field);

        // Persoenliche Risikosituation
        $decorator = new Custom_Decorator_RiskSituation();
        $this->addElement('text', 'pers_risk_situation_1', array(
            'required' => true,
            'value' => 'bums',
            'belongsTo' => 'risk_situation_array',
            'filters' => array('StringTrim'),
            'class' => array('form-control'),
            'validators' => array(
                array('validator' => 'StringLength', 'options' => array(3, 45))
            ),
            'decorators' => array($decorator, 'Errors','DtDdWrapper'),
        ));
//        $this->getElement('pers_risk_situation_1')->removeDecorator('Label');
//        $this->addElement('text', 'pers_risk_situation_2', array(
//         
//            'required' => true,
//            'belongsTo' => 'risk_situation_array',
//            'filters' => array('StringTrim'),
//            'class' => array('form-control'),
//            'validators' => array(
//                array('validator' => 'StringLength', 'options' => array(3, 45))
//            )
//        ));

        $this->addElement('button', 'btnAdd', array(
            'label' => '+',
            'class' => array('btn btn-primary'),
        ));


        $this->addDisplayGroup(array('btnAdd', 'pers_risk_situation_1'), 'risk_situations');
//        $this->getDisplayGroup('risk_situations')->removeDecorator('DtDdWrapper');
        $this->getDisplayGroup('risk_situations')->setLegend('Persönliche Risikosituation');
        



        // Drogenhotline
        $this->addElement('text', 'addict_drughotline', array(
            'label' => 'Drogenhotline:',
            'required' => true,
            'filters' => array('StringTrim'),
            'class' => array('form-control'),
            'validators' => array(
                array('validator' => 'StringLength', 'options' => array(3, 45))
            )
        ));

        // Prop Beratungsstelle
        $this->addElement('text', 'prop_advice_centre', array(
            'label' => 'Prop Beratungsstelle:',
            'required' => true,
            'class' => array('form-control'),
            'validators' => array(
                array('validator' => 'StringLength', 'options' => array(3, 20))
            )
        ));

        // Mein Therapeut
        $this->addElement('text', 'my_therapist', array(
            'label' => 'mein/e Therapeut/in:',
            'required' => true,
            'class' => array('form-control'),
            'validators' => array(
                array('validator' => 'StringLength', 'options' => array(1, 255))
            )
        ));

        // Risk danger
        $risk_danger = new Zend_Form_Element_Text('risk_danger');
        $risk_danger->setLabel('Ich bin in Gefahr, wieder zur Flasche zu greifen wenn ich... ');
        $risk_danger->setRequired(true);
        $risk_danger->setAttribs(array('class' => array('form-control')));
//        $risk_danger->addValidators(array('StringLength', array(3, 20)));
        // Risk situation
        $risk_situation = new Zend_Form_Element_Text('risk_situation');
        $risk_situation->setLabel('Diese Situation ist schwierig für mich, weil... ');
        $risk_situation->setRequired(true);
        $risk_situation->setAttribs(array('class' => array('form-control')));
//        $risk_danger->addValidators(array('StringLength', array(3, 20)));
        // Risk temptation
        $risk_temptation = new Zend_Form_Element_Text('risk_temptation');
        $risk_temptation->setLabel('"Versuchung": Der "kleine Teufel" auf meiner Schulter flüstert mir zu: "Trink doch ein Gläscchen", dann..');
        $risk_temptation->setRequired(true);
        $risk_temptation->setAttribs(array('class' => array('form-control')));
//        $risk_danger->addValidator('StringLength', true,array(3, 20));
        $risk_temptation->addValidator('StringLength', false, array(6, 20));

        $this->addDisplayGroup(array($risk_danger, $risk_situation, $risk_temptation), 'risiko');
        $this->getDisplayGroup('risiko')->setLegend('Risiko');

        // Temptation thought
        $temptation_thought = new Zend_Form_Element_Text('temptation_thought');
        $temptation_thought->setLabel('(Bewältigungsgedanken) Der "kleine Engel" auf meiner Schulter sagt mir: "Nein! Wenn du jetzt trinkst, dann...');
        $temptation_thought->setRequired(true);
        $temptation_thought->setAttribs(array('class' => array('form-control')));
//        $temptation_thought->addValidators(array('StringLength',false, array(3, 20)));
        // Temptation thought abstinence
        $temptation_thought_abstinence = new Zend_Form_Element_Text('temptation_thought_abstinence');
        $temptation_thought_abstinence->setLabel('In deiner Abstinenz hast du schon viel für dich erreicht, z.B. ...');
        $temptation_thought_abstinence->setRequired(true);
        $temptation_thought_abstinence->setAttribs(array('class' => array('form-control')));
//        $temptation_thought_abstinence->addValidators(array('StringLength', false,array(3, 20)));
        // temptation behaviour
        $temptation_behaviour = new Zend_Form_Element_Text('temptation_behaviour');
        $temptation_behaviour->setLabel('(Bewältigungsverhalten)<br>Was kann ich tun, um meine Abstinenz zu schützen?');
        $temptation_behaviour->setRequired(true);
        $temptation_behaviour->setAttribs(array('class' => array('form-control')));
//        $temptation_behaviour->addValidators(array('StringLength', false,array(3, 20)));

        $this->addDisplayGroup(array($temptation_thought, $temptation_thought_abstinence, $temptation_behaviour), 'temptation');
        $this->getDisplayGroup('temptation')->setLegend('Versuchung');


//
//        // Telefonnummer
//        $this->addElement('text', 'phone_number', array(
//            'label' => 'Telefonnummer:',
//            'required' => true,
//            'class' => array('form-control'),
//            'validators' => array(
//                array('validator' => 'StringLength', 'options' => array(3, 20))
//            )
//        ));
//
//        // Email
//        $this->addElement('text', 'email', array(
//            'label' => 'Email:',
//            'required' => false,
//            'class' => array('form-control'),
//            'validators' => array(
//                'EmailAddress'
//            )
//        ));
        // Den Submit Button hinzufügen
        $this->addElement('submit', 'submit', array(
            'ignore' => true,
            'label' => 'Notfallkoffer speichern',
        ));

        $this->addElement('reset', 'reset', array(
            'ignore' => true,
            'label' => 'Eingaben löschen',
        ));
    }

}

