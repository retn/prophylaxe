<?php

//require_once 'Custom/Decorator/RiskSituation.php';

class Application_Form_EmergencyCase extends Zend_Form {

    protected $_counterRiskSituation = 2;
    protected $_btnAddRiskSituation;
    protected $_riskSituationArrayName = 'risk_situations_array';
    protected $_riskSituationName = 'risk_situation_';
    protected $_riskSituationOptions = array(
        'required' => true,
        'value' => 'Risikosituation 1',
        'belongsTo' => 'risk_situations_array',
        'filters' => array('StringTrim'),
        'class' => array(''),
        'validators' => array(
            array('validator' => 'StringLength', 'options' => array(3, 45))
        )
    );
    protected $_btnAddSafetyAction;
    protected $_safetyActionArrayName = 'safety_actions_array';
    protected $_safetyActionName = 'safety_action_';
    protected $_safetyActionOptions = array(
        'required' => true,
        'value' => 'Schutzhandlung 1',
        'belongsTo' => 'safety_actions_array',
        'filters' => array('StringTrim'),
        'class' => array(''),
        'validators' => array(
            array('validator' => 'StringLength', 'options' => array(3, 45))
        )
    );
    protected $_btnAddSafetyThought;
    protected $_safetyThoughtArrayName = 'safety_thoughts_array';
    protected $_safetyThoughtName = 'safety_thought_';
    protected $_safetyThoughtOptions = array(
        'required' => true,
        'value' => 'Schutzgedanke 1',
        'belongsTo' => 'safety_thoughts_array',
        'filters' => array('StringTrim'),
        'class' => array(''),
        'validators' => array(
            array('validator' => 'StringLength', 'options' => array(3, 45))
        )
    );
    protected $_btnAddLimitRelapse;
    protected $_limitRelapseArrayName = 'limit_relapses_array';
    protected $_limitRelapseName = 'limit_relapse_';
    protected $_limitRelapseOptions = array(
        'required' => true,
        'value' => 'Rueckfall maßnahme 1',
        'belongsTo' => 'limit_relapses_array',
        'filters' => array('StringTrim'),
        'class' => array(''),
        'validators' => array(
            array('validator' => 'StringLength', 'options' => array(3, 45))
        )
    );
    protected $_helpPersonArrayName = 'help_persons_array';
    protected $_helpPersonName = 'help_person_';
    protected $_riskSituations = array();
    protected $_helpPersons = array();
    protected $_safetyThoughts = array();
    protected $_safetyActions = array();
    protected $_limitRelapses = array();

    public function getRiskSituations() {
        return $this->_riskSituations;
    }

    public function getHelpPersons() {
        return $this->_helpPersons;
    }

    public function getSafetyThoughts() {
        return $this->_safetyThoughts;
    }

    public function getSafetyActions() {
        return $this->_safetyActions;
    }

    public function getLimitRelapses() {
        return $this->_limitRelapses;
    }

    public function getRemoveButton() {
        return '<button name="btnRemove" class="emergency_case_delete_button">L&ouml;schen</button>';
    }

    public function getRiskSituationAddButton() {
        return '<button id="btnRiskSituationAdd" type="button" class="emergency_case_delete_button">Hinzuf&uuml;gen</button>'; //'<button class="btn btn-primary" type="button" id="btnRiskSituationAdd"><span class="glyphicon glyphicon-plus"></span></button>';
    }

    public function getHelpPersonAddButton() {
        return '<button id="btnHelpPersonAdd" type="button" class="emergency_case_delete_button">Hinzuf&uuml;gen</button>';
//'<button class="btn btn-primary" type="button" id="btnHelpPersonAdd"><span class="glyphicon glyphicon-plus"></span></button>';
    }

    public function getSafetyThoughtAddButton() {
        return '<button id="btnSafetyThoughtAdd" type="button" class="emergency_case_delete_button">Hinzuf&uuml;gen</button>';
        //'<button class="btn btn-primary" type="button" id="btnSafetyThoughtAdd"><span class="glyphicon glyphicon-plus"></span></button>';
    }

    public function getSafetyActionAddButton() {
        return '<button id="btnSafetyActionAdd" type="button" class="emergency_case_delete_button">Hinzuf&uuml;gen</button>';
        //'<button class="btn btn-primary" type="button" id="btnSafetyActionAdd"><span class="glyphicon glyphicon-plus"></span></button>';
    }

    public function getLimitRelapseAddButton() {
        return '<button id="btnLimitRelapseAdd" type="button" class="emergency_case_delete_button">Hinzuf&uuml;gen</button>';
        //'<button class="btn btn-primary" type="button" id="btnLimitRelapseAdd"><span class="glyphicon glyphicon-plus"></span></button>';
    }

    // Fuegt vor der Validierung Felder hinzu die durch den Benutzer dynamisch hinzufuegt wurden


    public function init() {
        // Setzt die Methode for das Anzeigen des Formulars mit POST
        $this->setMethod('post');
        $this->setName("emergencycase_form");

        // Pfad zu eigenen Decoratoren festlegen
//        $this->addElementPrefixPath('Custom_Decorator', 'Custom/Decorator/', 'decorator');
        // EmergencyCAse ID
        $field = new Zend_Form_Element_Hidden('ecID');
        $field->setDecorators(array('ViewHelper'));
        $this->addElement($field);

        // Patient ID
        $field = new Zend_Form_Element_Hidden('patientID_fk');
        $field->setDecorators(array('ViewHelper'));
        $this->addElement($field);



        $this->setSubFormDecorators(array(
            'FormElements',
            'Fieldset'
        ));


        /*
         * ##### Persoenliche Risikosituation Start #####
         */
        // Persoenliche Risikosituation Textfeld + Remove Button (enthalten im Decorator) 

        $this->addRiskSituation();
        $this->addSafetyThought();
        $this->addSafetyAction();
        $this->addLimitRelapses();
        $this->addHelpPerson();

//    
        // Drogenhotline
        $this->addElement('text', 'addict_drughotline', array(
            'label' => 'Drogenhotline:',
            'required' => true,
            'filters' => array('StringTrim'),
            'class' => array(''),
            'validators' => array(
                array('validator' => 'StringLength', 'options' => array(3, 45))
            )
        ));

        // Prop Beratungsstelle
        $this->addElement('text', 'prop_advice_centre', array(
            'label' => 'Prop Beratungsstelle:',
            'required' => true,
            'class' => array(''),
            'validators' => array(
                array('validator' => 'StringLength', 'options' => array(3, 20))
            )
        ));

        // Mein Therapeut
        $this->addElement('text', 'my_therapist', array(
            'label' => 'mein/e Therapeut/in:',
            'required' => true,
            'class' => array(''),
            'validators' => array(
                array('validator' => 'StringLength', 'options' => array(1, 255))
            )
        ));

        // Risk danger
        $risk_danger = new Zend_Form_Element_Text('risk_danger');
        $risk_danger->setLabel('Ich bin in Gefahr, wieder zur Flasche zu greifen wenn ich... ');
        $risk_danger->setRequired(true);
        $risk_danger->setAttribs(array('class' => array('')));
//        $risk_danger->addValidators(array('StringLength', array(3, 20)));
        // Risk situation
        $risk_situation = new Zend_Form_Element_Text('risk_situation');
        $risk_situation->setLabel('Diese Situation ist schwierig für mich, weil... ');
        $risk_situation->setRequired(true);
        $risk_situation->setAttribs(array('class' => array('')));
//        $risk_danger->addValidators(array('StringLength', array(3, 20)));
        // Risk temptation
        $risk_temptation = new Zend_Form_Element_Text('risk_temptation');
        $risk_temptation->setLabel('"Versuchung": Der "kleine Teufel" auf meiner Schulter flüstert mir zu: "Trink doch ein Gläschen", dann..');
        $risk_temptation->setRequired(true);
        $risk_temptation->setAttribs(array('class' => array('')));
//        $risk_danger->addValidator('StringLength', true,array(3, 20));
        $risk_temptation->addValidator('StringLength', false, array(6, 20));

        $this->addDisplayGroup(array($risk_danger, $risk_situation, $risk_temptation), 'risiko');
        $this->getDisplayGroup('risiko')->setLegend('Risiko');

        // Temptation thought
        $temptation_thought = new Zend_Form_Element_Text('temptation_thought');
        $temptation_thought->setLabel('(Bewältigungsgedanken) Der "kleine Engel" auf meiner Schulter sagt mir: "Nein! Wenn du jetzt trinkst, dann...');
        $temptation_thought->setRequired(true);
        $temptation_thought->setAttribs(array('class' => array('')));
//        $temptation_thought->addValidators(array('StringLength',false, array(3, 20)));
        // Temptation thought abstinence
        $temptation_thought_abstinence = new Zend_Form_Element_Text('temptation_thought_abstinence');
        $temptation_thought_abstinence->setLabel('In deiner Abstinenz hast du schon viel für dich erreicht, z.B. ...');
        $temptation_thought_abstinence->setRequired(true);
        $temptation_thought_abstinence->setAttribs(array('class' => array('')));
//        $temptation_thought_abstinence->addValidators(array('StringLength', false,array(3, 20)));
        // temptation behaviour
        $temptation_behaviour = new Zend_Form_Element_Text('temptation_behaviour');
        $temptation_behaviour->setLabel('(Bewältigungsverhalten)<br>Was kann ich tun, um meine Abstinenz zu schützen?');
        $temptation_behaviour->setRequired(true);
        $temptation_behaviour->setAttribs(array('class' => array('')));
//        $temptation_behaviour->addValidators(array('StringLength', false,array(3, 20)));

        $this->addDisplayGroup(array($temptation_thought, $temptation_thought_abstinence, $temptation_behaviour), 'temptation');
        $this->getDisplayGroup('temptation')->setLegend('Versuchung');


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

    /*
     * Funktion die nach dem Absenden des Forumlars aufgerufen wird 
     * um Dynamisch hinzugefügt Felder im Frontend in die Validierung aufzunehmen
     */

    public function addFields($post) {
        // Risikosituationen hinzufuegen
//        $risk_situations = ($post['risk_situation_array']);
        if (isset($post[$this->_riskSituationArrayName]))
            $this->addRiskSituation($post[$this->_riskSituationArrayName]);

        if (isset($post[$this->_safetyThoughtArrayName]))
            $this->addSafetyThought($post[$this->_safetyThoughtArrayName]);

        if (isset($post[$this->_safetyActionArrayName]))
            $this->addSafetyAction($post[$this->_safetyActionArrayName]);

        if (isset($post[$this->_helpPersonArrayName]))
            $this->addHelpPerson($post[$this->_helpPersonArrayName]);

        if (isset($post[$this->_limitRelapseArrayName]))
            $this->addLimitRelapses($post[$this->_limitRelapseArrayName]);
    }

    // Fuegt dynamisch Felder hinzu fuer Schutzhandlungen
    public function addSafetyAction($values = array('pers_safety_action_1' => '')) {
        if ($values == null)
            return;

        $counter = 0;
        $this->_safetyActions = array();

        foreach ($values as $key => $value) {
            $name = $this->_safetyActionName . $counter;
            $this->removeElement($name);
            $decorator = new Custom_Form_Decorator_RiskSituationMeep();
            $element = new Zend_Form_Element_Text($name, $this->_safetyActionOptions);
            $element->setValue($value);
            $element->setBelongsTo($this->_safetyActionArrayName);
            $element->setDecorators(array($decorator, 'Errors'));
            $this->addElement($element);
            array_push($this->_safetyActions, $name);
            $counter++;
        }
    }

    // Fuegt dynamisch Felder hinzu fuer Schutzgedanken
    public function addSafetyThought($values = array('0' => '')) {
        if ($values == null)
            return;
        $counter = 0;
        $this->_safetyThoughts = array();

        foreach ($values as $key => $value) {
            $name = $this->_safetyThoughtName . $counter;
            $this->removeElement($name);
            $decorator = new Custom_Form_Decorator_RiskSituationMeep();
            $element = new Zend_Form_Element_Text($name, $this->_safetyThoughtOptions);
            $element->setBelongsTo($this->_safetyThoughtArrayName);
            $element->setValue($value);
            $element->setDecorators(array($decorator, 'Errors'));
            $this->addElement($element);
            array_push($this->_safetyThoughts, $name);
            $counter++;
        }
    }

    // Fuegt dynamisch Felder hinzu fuer Risikosituationen
    public function addRiskSituation($values = array('0' => '')) {

        /*
         * Ergebnis im Frontend
         * <input type="text" value="" name="risk_situation_array[risk_situation_0]" id="risk_situation_array-risk_situation_0">
         */
        if ($values == null)
            return;
        $counter = 0;
        $this->_riskSituations = array();

        foreach ($values as $key => $value) {
            $name = $this->_riskSituationName . $counter;
            $this->removeElement($name);
            $decorator = new Custom_Form_Decorator_RiskSituationMeep();
            $element = new Zend_Form_Element_Text($name, $this->_riskSituationOptions);
            $element->setBelongsTo($this->_riskSituationArrayName);
            $element->setValue($value);
            $element->setDecorators(array($decorator, 'Errors'));
            $this->addElement($element);
            array_push($this->_riskSituations, $name);
            $counter++;
        }
    }

    public function addLimitRelapses($values = array('0' => '')) {
        if ($values == null)
            return;
        $counter = 0;
        $this->_limitRelapses = array();

        foreach ($values as $key => $value) {
            $name = $this->_limitRelapseName . $counter;
            $this->removeElement($name);
            $decorator = new Custom_Form_Decorator_RiskSituationMeep();
            $element = new Zend_Form_Element_Text($name, $this->_limitRelapseOptions);
            $element->setBelongsTo($this->_limitRelapseArrayName);
            $element->setValue($value);
            $element->setDecorators(array($decorator, 'Errors'));
            $this->addElement($element);
            array_push($this->_limitRelapses, $name);
            $counter++;
        }
    }

    public function addHelpPerson($values = array('help_person_1' => array('name' => '', 'phonenumber' => ''))) {

        $counter = 0;
        $this->_helpPersons = array();
        foreach ($values as $key => $value) {
            $name = $this->_helpPersonName . $counter;
            $element = new Custom_Form_Element_HelpPerson($name);
            $element->setLabel('')
                    ->setView(new Zend_View());
            $element->setValue($value);
            $element->setRequired(true);
//        $d->setDecorators(array('Errors', array('HtmlTag', array('tag' => 'li'))));
            $validator = new Zend_Validate_StringLength(array('min' => 3, 'max' => 45));
            $element->addValidator($validator);
            $element->setBelongsTo($this->_helpPersonArrayName);
            $this->addElement($element);
            array_push($this->_helpPersons, $name);
            $counter++;
        }
    }

    public function isValid($data) {

        /* Namen fuer Validierung bearbeiten. 
         *  Namen werden im Frontend mit einem index (0,1,2,3...) erzeugt, auf PHP Seiter aber mit einem namen + index        
         * z.B. aus $data['risk_situation_array'][0] wird $data['risk_situation_array'][risk_situation_x]
         */
        $this->isValidHelper($this->_riskSituationArrayName, $this->_riskSituationName, $data);
        $this->isValidHelper($this->_safetyThoughtArrayName, $this->_safetyThoughtName, $data);
        $this->isValidHelper($this->_safetyActionArrayName, $this->_safetyActionName, $data);
        $this->isValidHelper($this->_limitRelapseArrayName, $this->_limitRelapseName, $data);



        return parent::isValid($data);
    }

    function isValidHelper($arrayName, $keyName, &$data) {
        $counter = 0;
        $newArray = array();
        foreach ($data[$arrayName] as $value) {
            $newArray[$keyName . $counter] = $value;
            $counter++;
        }
        $data[$arrayName] = $newArray;
    }

    public function setDefaults(array $defaults) {

//        if (isset($defaults[$this->_riskSituationArrayName])) {
//            $tmp_array = array();
//            foreach ($defaults[$this->_riskSituationArrayName] as $value) {
//                $tmp_array[] = $value->getText();
//            }
//        }
        $tmp = $this->setDefaultsHelper($this->_riskSituationArrayName, $defaults);
//        $tmp = $defaults[$this->_riskSituationArrayName];
        if (count($tmp) > 0) {
            $this->addRiskSituation($tmp);
        } else {
            $this->addRiskSituation();
        }

        $tmp = $this->setDefaultsHelper($this->_limitRelapseArrayName, $defaults);
//        $tmp = $defaults[$this->_limitRelapseArrayName];
        if (count($tmp) > 0) {
            $this->addLimitRelapses($tmp);
        } else {
            $this->addLimitRelapses();
        }
        $tmp = $this->setDefaultsHelper($this->_safetyActionArrayName, $defaults);
//        $tmp = $defaults[$this->_safetyActionArrayName];
        if (count($tmp) > 0) {
            $this->addSafetyAction($tmp);
        } else {
            $this->addSafetyAction();
        }

        $tmp = $this->setDefaultsHelper($this->_safetyThoughtArrayName, $defaults);
//        $tmp = $defaults[$this->_safetyThoughtArrayName];
        if (count($tmp) > 0) {
            $this->addSafetyThought($tmp);
        } else {
            $this->addSafetyThought();
        }


        $tmp = $defaults[$this->_helpPersonArrayName];
        if (isset($defaults[$this->_helpPersonArrayName])) {
            $tmp_array = array();
            foreach ($defaults[$this->_helpPersonArrayName] as $value) {
                $tmp_array[] = array('name' => $value['name'], 'phonenumber' => $value['phone_number']);
            }
        }
        if (count($tmp_array) > 0) {
            $this->addHelpPerson($tmp_array);
        } else {
            $this->addHelpPerson();
        }

        return parent::setDefaults($defaults);
    }

    private function setDefaultsHelper($arrayName, &$defaults) {
        if (isset($defaults[$arrayName])) {
            $tmp_array = array();
            foreach ($defaults[$arrayName] as $value) {
                $tmp_array[] = $value['text'];
            }
        }
        return $tmp_array;
    }

}
