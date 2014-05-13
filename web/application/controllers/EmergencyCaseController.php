<?php

/*
 * Controller zum verwalten vom Notfallkoffer
 */
class EmergencyCaseController extends Zend_Controller_Action
{

    public function init()
    {
        /* Initialize action controller here */
    }

    public function indexAction()
    {
        // action body
    }

    /*
     * Anlegen eines Notfallkoffers
     */
    public function createAction()
    {
        $this->view->jQuery()->addJavascriptFile('/js/emergency-case/EmergencyCase.js');

        $patientID = $this->getParam('id');
        $runthrough = $this->getParam('runthrough');
//        $controller = $this->getParam('redirect_controller');
//        $action = $this->getParam('redirect_action');
//        $redirectUrl = '/' . $controller . '/' . $action . '/' . $patientID;

        $form = new Application_Form_EmergencyCase();


        if ($this->getRequest()->isPost()) {
            // Formular dynamische Felder hinzufügen
            $post = $this->getRequest()->getPost();

            $form->addFields($post);


            if ($form->isValid($post)) {
                $emergencyCase = new Application_Model_EmergencyCase_EmergencyCase($form->getValues());
                $emergencyCase->setPatientID_fk($patientID);

                $mapper = new Application_Model_EmergencyCase_EmergencyCaseMapper();
                $emergencyCaseID = $mapper->save($emergencyCase);

                if ($runthrough) {
                    $this->_helper->redirector->gotoUrl('/patient/edit-distraction/' . $patientID . '/1');
                } else {
                    $this->_helper->redirector('index');
                }

//                return $this->_helper->redirector('index');
            }
        }
        $this->view->form = $form;
    }

    /*
     * Bearbeiten einer Anlaufstelle
     */
    public function editAction()
    {
        $this->view->jQuery()->addJavascriptFile('/js/emergency-case/EmergencyCase.js');
        $patientID = $this->getParam('id');



        $form = new Application_Form_EmergencyCase();
        $form->getElement('submit')->setLabel("Daten ändern");

        $request = $this->getRequest();

        // Wenn das Formular abgesedet wurde, neue Daten Speichern
        // Wenn kein post Request vorliegt => Erster Seitenaufruf, Patient wird anhand der uebergebenen ID ins Formular eingetragen.

        if ($this->getRequest()->isPost()) {
            $post = $this->getRequest()->getPost();

            $form->addFields($post);

            if ($form->isValid($post)) {
                $emergencyCase = new Application_Model_EmergencyCase_EmergencyCase($form->getValues());
                $emergencyCase->setPatientID_fk($patientID);

                $mapper = new Application_Model_EmergencyCase_EmergencyCaseMapper();
                $emergencyCaseID = $mapper->save($emergencyCase);
                return $this->_helper->redirector('index');
            }
        } else {
//            $emergencyCase = new Application_Model_EmergencyCase();
            $mapper = new Application_Model_EmergencyCase_EmergencyCaseMapper();
            $emergencyCase = $mapper->findEmergencyCaseOfPatient($patientID);

            $form->setDefaults($emergencyCase->getKeyValueArray());
        }

        $this->_helper->viewRenderer('create');
        $this->view->form = $form;
    }

   


}


