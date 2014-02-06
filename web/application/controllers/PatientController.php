<?php

class PatientController extends Zend_Controller_Action {

    public function init() {
        $contextSwitch = $this->_helper->getHelper('contextSwitch');
        $contextSwitch->addActionContext('getpatients', 'json')
                ->initContext();
        $contextSwitch->addActionContext('save-maxim', 'json')
                ->initContext();
        $contextSwitch->addActionContext('save-distraction', 'json')
                ->initContext();
    }

    public function indexAction() {
//        $patient = new Application_Model_PatientMapper();
//        $this->view->entries = $patient->fetchAll();

      
    }

    public function createAction() {


        $request = $this->getRequest();
        $form = new Application_Form_Patient();

        if ($this->getRequest()->isPost()) {
            if ($form->isValid($request->getPost())) {
                $patient = new Application_Model_Patient($form->getValues());
                $patient->setUserID_fk(Zend_Auth::getInstance()->getStorage()->read()->userID);
                $mapper = new Application_Model_PatientMapper();
                $mapper->save($patient);
                return $this->_helper->redirector('index');
            }
        }

        $this->view->form = $form;
    }

    public function editAction() {
        $patientID = $this->getParam('id');

        $form = new Application_Form_Patient();
        $form->getElement('submit')->setLabel("Daten ändern");

        $request = $this->getRequest();

        // Wenn das Formular abgesedet wurde, neue Daten Speichern
        // Wenn kein post Request vorliegt => Erster Seitenaufruf, Patient wird anhand der uebergebenen ID ins Formular eingetragen.

        if ($this->getRequest()->isPost()) {
            if ($form->isValid($request->getPost())) {
                $patient = new Application_Model_Patient($form->getValues());
                $mapper = new Application_Model_PatientMapper();
                $mapper->save($patient);
                return $this->_helper->redirector('list');
            }
        } else {
            $patient = new Application_Model_Patient();
            $mapper = new Application_Model_PatientMapper();
            $mapper->find($patientID, $patient);


            $form->populate($patient->getKeyValueArray());
        }

        $this->view->form = $form;
    }

    public function getpatientsAction() {
        $patienten = new Application_Model_PatientMapper();
        $daten = $patienten->fetchAll();

        $json = array();
        foreach ($daten as $value) {
            $json[] = $value->getPatientTableArray();
        }

        $this->view->aaData = $json;
    }

    public function listAction() {
        //        $this->view->headScript()->appendFile('/js/PatientTable.js');
        $this->view->jQuery()->addJavascriptFile('/js/datatable/jquery.dataTables.min.js');
        $this->view->jQuery()->addStylesheet('/css/jquery.dataTables.css');

        $this->view->jQuery()->addJavascriptFile('/js/patient/PatientTable.js');
    }

    public function editMaximAction() {
        $this->view->jQuery()->addJavascriptFile('/js/datatable/jquery.dataTables.min.js')
                ->addJavascriptFile('/js/patient/PatientMaximTable.js')
                ->addStylesheet('/css/jquery.dataTables.css');
        // Bisher zugeordnete Sprueche holen
        $patientID = $this->getParam('id');
        $patientMapper = new Application_Model_PatientMapper();
        $maximsFromPatient = $patientMapper->getMaximsFromPatient($patientID);

        // Alle Spruche fuer Anzeige holen
        $mapper = new Application_Model_MaximMapper();
        $maxims = $mapper->fetchAll();

        // Patient ID speichern
//        Zend_Registry::set('patientID',$patientID);
        $ns = new Zend_Session_Namespace('edit-maxim');
        $ns->patient_id = $patientID;

        $this->view->maximsFromPatient = $maximsFromPatient;
        $this->view->maxims = $maxims;
    }

    public function saveMaximAction() {
        $request = $this->getRequest()->getPost('maxim_ids');
        // Gespeicherte Patient ID holen
//        $patientID = Zend_Registry::get('patientID');
        $ns = new Zend_Session_Namespace('edit-maxim');
        $patientID = $ns->patient_id;

//        $maximHasPatientObject = array();
//        foreach ($request as $value) {
//            $maximHasPatientObject[] = new Application_Model_MaximHasPatient(array('maximid' => $value, 'patientid' => $patientID));
//        }

        $maximHasPatientMapper = new Application_Model_MaximHasPatientMapper();
        $maximHasPatientMapper->saveAll($patientID, $request);


        $this->view->redirect = "/patient/list";
    }

    public function editDistractionAction() {
        $this->view->jQuery()->addJavascriptFile('/js/datatable/jquery.dataTables.min.js')
                ->addJavascriptFile('/js/patient/PatientDistractionTable.js')
                ->addStylesheet('/css/jquery.dataTables.css');
        // Bisher zugeordnete Sprueche holen
        $patientID = $this->getParam('id');
        $patientMapper = new Application_Model_PatientMapper();
        $distractionsFromPatient = $patientMapper->getDistractionsFromPatient($patientID);

        // Alle Spruche fuer Anzeige holen
        $mapper = new Application_Model_DistractionMapper();
        $distractions = $mapper->fetchAll();

        // Patient ID speichern
//        Zend_Registry::set('patientID',$patientID);
        $ns = new Zend_Session_Namespace('edit-distraction');
        $ns->patient_id = $patientID;

        $this->view->distractionsFromPatient = $distractionsFromPatient;
        $this->view->distractions = $distractions;
    }

    public function saveDistractionAction() {
        $request = $this->getRequest()->getPost('distraction_ids');
        // Gespeicherte Patient ID holen
        $ns = new Zend_Session_Namespace('edit-distraction');
        $patientID = $ns->patient_id;

        $distractionHasPatientMapper = new Application_Model_DistractionHasPatientMapper();
        $distractionHasPatientMapper->saveAll($patientID, $request);


        $this->view->redirect = "/patient/list";
    }

}

