<?php

class EmergencyCaseController extends Zend_Controller_Action {

    public function init() {
        /* Initialize action controller here */
    }

    public function indexAction() {
        // action body
    }

    public function createAction() {
        $this->view->jQuery()->addJavascriptFile('/js/emergency-case/EmergencyCase.js');
        
        $patientD = $this->getParam('id');

        $form = new Application_Form_EmergencyCase();
        
        if ($this->getRequest()->isPost()) {
            // Formular dynamische Felder hinzufügen
            $post = $this->getRequest()->getPost();
            $risk_situations = ($post['risk_situation_array']);
            $i = 0;
            foreach ($risk_situations as $key => $value) {
                if($i > 0)
                    $form->addRiskSituation($value);
                $i++;
            }
//            for ($i = 1; $i < count($risk_situations)-1; $i++) {
//                $form->addRiskSituation($risk_situations[$i]);
//            }
            
            if ($form->isValid($post)) {
                $emergencyCase = new Application_Model_EmergencyCase($form->getValues());
                $emergencyCase->setPatientID_fk($patientD);

                $mapper = new Application_Model_EmergencyCaseMapper();
                $mapper->save($emergencyCase);
                return $this->_helper->redirector('index');
            }
        }
        $this->view->form = $form;

    }

    public function editAction() {
        // action body $patientID = $this->getParam('id');
    }

}

