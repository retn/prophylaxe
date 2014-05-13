<?php

//require_once 'Custom/Decorator/RiskSituation.php';

class Application_Form_EmergencyCase_backup extends Zend_Form {

    protected $_counterRiskSituation = 2;
    protected $_btnAddRiskSituation;
    protected $_riskSituationOptions = array(
        'required' => true,
        'value' => 'Risikosituation 1',
        'belongsTo' => 'risk_situation_array',
        'filters' => array('StringTrim'),
        'class' => array('form-control'),
        'validators' => array(
            array('validator' => 'StringLength', 'options' => array(3, 45))
        )
    );
    protected $_btnAddSafetyAction;
    protected $_safetyActionOptions = array(
        'required' => true,
        'value' => 'Schutzhandlung 1',
        'belongsTo' => 'safety_action_array',
        'filters' => array('StringTrim'),
        'class' => array('form-control'),
        'validators' => array(
            array('validator' => 'StringLength', 'options' => array(3, 45))
        )
    );
    protected $_btnAddSafetyThought;
    protected $_safetyThoughtOptions = array(
        'required' => true,
        'value' => 'Schutzgedanke 1',
        'belongsTo' => 'safety_thought_array',
        'filters' => array('StringTrim'),
        'class' => array('form-control'),
        'validators' => array(
            array('validator' => 'StringLength', 'options' => array(3, 45))
        )
    );

    // Fuegt vor der Validierung Felder hinzu die durch den Benutzer dynamisch hinzufuegt wurden


    public function init() {
        // Setzt die Methode for das Anzeigen des Formulars mit POST
        $this->setMethod('post');
        $this->setName("emergencycase_form");

        // Pfad zu eigenen Decoratoren festlegen
        $this->addElementPrefixPath('Custom_Decorator', 'Custom/Decorator/', 'decorator');

        // EmergencyCAse ID
        $field = new Zend_Form_Element_Hidden('ecID');
        $field->setDecorators(array('ViewHelper'));
        $this->addElement($field);

        // Patient ID
        $field = new Zend_Form_Element_Hidden('patientID_fk');
        $field->setDecorators(array('ViewHelper'));
        $this->addElement($field);


        $this->addHelpPerson();

        /*
         * ##### Persoenliche Risikosituation Start #####
         */
        // Persoenliche Risikosituation Textfeld + Remove Button (enthalten im Decorator) 
        $riskSituation = new Zend_Form_Element_Text('pers_risk_situation_1', $this->_riskSituationOptions);
        $decorator = new Custom_Decorator_RiskSituation();
        $riskSituation->setDecorators(array($decorator, 'Errors', array('HtmlTag', array('tag' => 'li'))));
        $this->addElement($riskSituation);

        // Button um weitere Punkte hinzuzufuegen
        $addRiskSituation = new Zend_Form_Element_Button('btnRiskSituationAdd');
        $addRiskSituation->setDecorators(array(
            'ViewHelper',
            array('HtmlTag', array('tag' => 'li')),
        ));
        $addRiskSituation->setLabel('+');
        $addRiskSituation->setAttrib('class', 'btn btn-primary');
        $this->_btnAddRiskSituation = $addRiskSituation;

        // Fieldset fuer Risikosituationen
        $this->addDisplayGroup(array($addRiskSituation, 'pers_risk_situation_1'), 'risk_situations');
        $this->getDisplayGroup('risk_situations')->removeDecorator('DtDdWrapper');
        $this->getDisplayGroup('risk_situations')->setLegend('Persönliche Risikosituation');
        $this->getDisplayGroup('risk_situations')->setDecorators(array(
            'FormElements',
            array('HtmlTag', array('tag' => 'ul', 'id' => 'fieldset-risk_situations_list')),
            'Fieldset'
        ));

        /*
         * ##### Persoenliche Risikosituation Ende #####
         */


        /*
         * ##### Persoenliche Schutzhandlung Start #####
         */

        // Persoenliche Schutzhandlung Textfeld + Remove Button
        $safetyAction = new Zend_Form_Element_Text('pers_safety_action_1', $this->_safetyActionOptions);
        $decorator = new Custom_Decorator_RiskSituation();
        $safetyAction->setDecorators(array($decorator, 'Errors', array('HtmlTag', array('tag' => 'li'))));
        $this->addElement($safetyAction);

        // Button um weitere Punkte hinzuzufuegen
        $addSafetyAction = new Zend_Form_Element_Button('btnSafetyActionAdd');
        $addSafetyAction->setDecorators(array(
            'ViewHelper',
            array('HtmlTag', array('tag' => 'li')),
        ));
        $addSafetyAction->setLabel('+');
        $addSafetyAction->setAttrib('class', 'btn btn-primary');
        $this->_btnAddSafetyAction = $addSafetyAction;

        // // Fieldset fuer Persoenliche Schutzhandlung 
        $this->addDisplayGroup(array($addSafetyAction, 'pers_safety_action_1'), 'safety_actions');
        $this->getDisplayGroup('safety_actions')->removeDecorator('DtDdWrapper');
        $this->getDisplayGroup('safety_actions')->setLegend('Persönliche Schutzhandlungen');
        $this->getDisplayGroup('safety_actions')->setDecorators(array(
            'FormElements',
            array('HtmlTag', array('tag' => 'ul', 'id' => 'fieldset-safety_actions_list')),
            'Fieldset'
        ));

        /*
         * ##### Persoenliche Schutzhandlung Ende #####
         */


        /*
         * ##### Persoenliche Schutzgedanken Start #####
         */

        // Persoenliche Schutzgedanken Textfeld + Remove Button
        $safetyThought = new Zend_Form_Element_Text('pers_safety_thought_1', $this->_safetyThoughtOptions);
        $decorator = new Custom_Decorator_RiskSituation();
        $safetyThought->setDecorators(array($decorator, 'Errors', array('HtmlTag', array('tag' => 'li'))));
        $this->addElement($safetyThought);

        // Button um weitere Punkte hinzuzufuegen
        $addSafetyThought = new Zend_Form_Element_Button('btnSafetyThoughtAdd');
        $addSafetyThought->setDecorators(array(
            'ViewHelper',
            array('HtmlTag', array('tag' => 'li')),
        ));
        $addSafetyThought->setLabel('+');
        $addSafetyThought->setAttrib('class', 'btn btn-primary');
        $this->_btnAddSafetyThought = $addSafetyThought;

        // // Fieldset fuer Persoenliche Schutzgedanken 
        $this->addDisplayGroup(array($addSafetyThought, 'pers_safety_thought_1'), 'safety_thoughts');
        $this->getDisplayGroup('safety_thoughts')->removeDecorator('DtDdWrapper');
        $this->getDisplayGroup('safety_thoughts')->setLegend('Persönliche Schutzgedanken');
        $this->getDisplayGroup('safety_thoughts')->setDecorators(array(
            'FormElements',
            array('HtmlTag', array('tag' => 'ul', 'id' => 'fieldset-safety_thoughts_list')),
            'Fieldset'
        ));

        /*
         * ##### Persoenliche Schutzgedanken Ende #####
         */

        /*
         * ##### Personen die mir Helfen Start #####
         */




        /*
         * ##### Personen die mir Helfen Ende #####
         */



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

    public function addFields($post) {
        // Risikosituationen hinzufuegen
        $risk_situations = ($post['risk_situation_array']);
        $this->getDisplayGroup('risk_situations')->clearElements();
        $this->getDisplayGroup('risk_situations')->addElement($this->_btnAddRiskSituation);

        $this->getDisplayGroup('risk_situations')->setLegend('Persönliche Risikosituation');

        $counter = 1;
        foreach ($risk_situations as $key => $value) {
            $this->addRiskSituation($value, $counter++);
        }

        // Persoenlische Schutzhandlungen hinzufuegen
        $safety_actions = ($post['safety_action_array']);
        $this->getDisplayGroup('safety_actions')->clearElements();
        $this->getDisplayGroup('safety_actions')->addElement($this->_btnAddSafetyAction);
        $this->getDisplayGroup('safety_actions')->setLegend('Persönliche Schutzhandlungen');

        $counter = 1;
        foreach ($safety_actions as $key => $value) {
            $this->addSafetyAction($value, $counter++);
        }

        // Persoenlische Schutzgedanken hinzufuegen
        $safety_thoughts = ($post['safety_thought_array']);
        $this->getDisplayGroup('safety_thoughts')->clearElements();
        $this->getDisplayGroup('safety_thoughts')->addElement($this->_btnAddSafetyThought);
        $this->getDisplayGroup('safety_thoughts')->setLegend('Persönliche Schutzgedanken');

        $counter = 1;
        foreach ($safety_thoughts as $key => $value) {
            $this->addSafetyThought($value, $counter++);
        }


        // Sonstiges hinzufuegen
    }

    public function addSafetyAction($value, $index) {

        $name = 'pers_safety_action_' . $index;
        $decorator = new Custom_Decorator_RiskSituation();
        $element = new Zend_Form_Element_Text($name, $this->_safetyActionOptions);
        $element->setValue($value);
        $element->setDecorators(array($decorator, 'Errors', array('HtmlTag', array('tag' => 'li'))));
        $this->addElement($element);
        $this->getDisplayGroup('safety_actions')->addElement($this->getElement($name));
    }

    public function addSafetyThought($value, $index) {

        $name = 'pers_safety_thought_' . $index;
        $decorator = new Custom_Decorator_RiskSituation();
        $element = new Zend_Form_Element_Text($name, $this->_safetyThoughtOptions);
        $element->setValue($value);
        $element->setDecorators(array($decorator, 'Errors', array('HtmlTag', array('tag' => 'li'))));
        $this->addElement($element);
        $this->getDisplayGroup('safety_thoughts')->addElement($this->getElement($name));
    }

    // Fuegt dynamisch Felder hinzu fuer Risikosituationen
    public function addRiskSituation($value, $index) {
        $name = 'pers_risk_situation_' . $index; // $this->_counterRiskSituation;
//        $this->removeElement($name);

        $element = new Zend_Form_Element_Text($name, $this->_riskSituationOptions);
        $element->setValue($value);
        $decorator = new Custom_Decorator_RiskSituation();
        $element->setDecorators(array($decorator, 'Errors', array('HtmlTag', array('tag' => 'li'))));
        $this->addElement($element);


        $this->getDisplayGroup('risk_situations')->addElement($this->getElement($name));
    }

    public function addHelpPerson($values = null) {
        $this->removeDisplayGroup('help_persons');
        $helpPersonRows = array();

        if($values == null){
            $values = array('emptyField' => '');
        }
        
        $counter = 1;
        foreach ($values as $key => $value) {

//            $this->addSafetyAction($value, $counter++);
            $helpPersonName = new Zend_Form_Element_Text('help_person_name_' . $counter, array(
                'required' => true,
                'value' => $value,
                'label' => 'Name:',
                'belongsTo' => 'help_person_array',
                'filters' => array('StringTrim'),
                'class' => array('form-control'),
                'validators' => array(
                    array('validator' => 'StringLength', 'options' => array(2, 45))
                ),
                'decorators' => array('Label', 'ViewHelper')
            ));

            $helpPersonTel = new Zend_Form_Element_Text('help_person_tel_' . $counter, array(
                'required' => true,
                'value' => '000000',
                'label' => 'Tel-Nr',
                'belongsTo' => 'risk_situation_array',
                'filters' => array('StringTrim'),
                'class' => array('form-control'),
                'validators' => array(
                    array('validator' => 'StringLength', 'options' => array(3, 45))
                ),
                'decorators' => array('Label', 'ViewHelper')
            ));

            $helpPersonBtnRemove = new Zend_Form_Element_Button('helpPersonBtnRemove', array(
                'value' => 'value',
                'label' => 'label',
                'class' => array('btn'),
                'decorators' => array('ViewHelper')
            ));

            $this->addElement($helpPersonTel);
            $this->addElement($helpPersonName);
            $this->addElement($helpPersonBtnRemove);

            $this->addDisplayGroup(array($helpPersonName, $helpPersonTel, $helpPersonBtnRemove),'help_person_' . $counter);
            $this->getDisplayGroup('help_person_' . $counter)->setDecorators(array(
                'FormElements',
                array('HtmlTag', array('tag' => 'li')),
            ));
            array_push($helpPersonRows, $this->getDisplayGroup('help_person_' . $counter));
            
            $counter++;
        }

 
        $this->addDisplayGroup($helpPersonRows, 'help_persons');

        $this->getDisplayGroup('help_persons')->setDecorators(array(
            'FormElements',
            array('HtmlTag', array('tag' => 'ul', 'id' => 'address')),
        ));
    }

}
