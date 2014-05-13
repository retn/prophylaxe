<?php

/*
 * Controller zum Verwalten von Patienten
 * 
 */
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
    /*
     * Anlegen eines Patienten
     */
    public function createAction() {


        $request = $this->getRequest();
        $form = new Application_Form_Patient();

        if ($this->getRequest()->isPost()) {
            if ($form->isValid($request->getPost())) {
                $patient = new Application_Model_Patient_Patient($form->getValues());
                $patient->setUserID_fk(Zend_Auth::getInstance()->getStorage()->read()->userID);
                $mapper = new Application_Model_Patient_PatientMapper();
                $mapper->save($patient);

                $this->sendConfirmEmail($patient);

                $this->_helper->redirector->gotoUrl('/patient/run-through/' . $patient->getId());
            }
        }

        $this->view->form = $form;
    }

    /*
     * Bearbeiten eines Patienten
     */
    public function editAction() {
        $patientID = $this->getParam('id');

        $form = new Application_Form_Patient();
        $form->getElement('submit')->setLabel("Daten ändern");

        $request = $this->getRequest();

        // Wenn das Formular abgesedet wurde, neue Daten Speichern
        // Wenn kein post Request vorliegt => Erster Seitenaufruf, Patient wird anhand der uebergebenen ID ins Formular eingetragen.

        if ($this->getRequest()->isPost()) {
            if ($form->isValid($request->getPost())) {
                $patient = new Application_Model_Patient_Patient($form->getValues());
                $mapper = new Application_Model_Patient_PatientMapper();
                $mapper->save($patient);
                return $this->_helper->redirector('list');
            }
        } else {
            $patient = new Application_Model_Patient_Patient();
            $mapper = new Application_Model_Patient_PatientMapper();
            $mapper->find($patientID, $patient);


            $form->populate($patient->getKeyValueArray());
        }

        $this->view->form = $form;
    }

    /*
     * Liefert alle Patienten fuer jDataTable
     */
    public function getpatientsAction() {
        $patienten = new Application_Model_Patient_PatientMapper();
        $daten = $patienten->fetchAll();

        $json = array();
        foreach ($daten as $value) {
            $json[] = $value->getPatientTableArray();
        }

        $this->view->aaData = $json;
    }

    /*
     * Liste alle Patienten auf
     */
    public function listAction() {
        //        $this->view->headScript()->appendFile('/js/PatientTable.js');
        $this->view->jQuery()->addJavascriptFile('/js/datatable/jquery.dataTables.min.js');
        $this->view->jQuery()->addStylesheet('/css/jquery.dataTables.css');

        $this->view->jQuery()->addJavascriptFile('/js/patient/PatientTable.js');
    }

    /*
     * Zuordnen der Sprueche zu Patienten
     */
    public function editMaximAction() {
        $this->view->jQuery()->addJavascriptFile('/js/datatable/jquery.dataTables.min.js')
                ->addJavascriptFile('/js/patient/PatientMaximTable.js')
                ->addStylesheet('/css/jquery.dataTables.css');
        // Bisher zugeordnete Sprueche holen
        $patientID = $this->getParam('id');
        $this->runthrough = $this->getParam('runthrough');

        $patientMapper = new Application_Model_Patient_PatientMapper();
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

    /*
     * Speichern der zugeordneten Sprueche
     */
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

    /*
     * Zuordnen der Ablenkungen zu einem Patient
     */
    public function editDistractionAction() {
        $this->view->jQuery()->addJavascriptFile('/js/datatable/jquery.dataTables.min.js')
                ->addJavascriptFile('/js/patient/PatientDistractionTable.js')
                ->addStylesheet('/css/jquery.dataTables.css');
        // Bisher zugeordnete Sprueche holen
        $patientID = $this->getParam('id');
//        $this->runthrough = $this->getParam('runthrough');
//        $this->runthrough = 'FOO';
        $patientMapper = new Application_Model_Patient_PatientMapper();
        $distractionsFromPatient = $patientMapper->getDistractionsFromPatient($patientID);

        // Alle Sprueche fuer Anzeige holen
        $mapper = new Application_Model_DistractionMapper();
        $distractions = $mapper->fetchAll();

        // Patient ID speichern
//        Zend_Registry::set('patientID',$patientID);
        $ns = new Zend_Session_Namespace('edit-distraction');
        $ns->patient_id = $patientID;
        $ns->runthrough = $this->getParam('runthrough');



        $this->view->distractionsFromPatient = $distractionsFromPatient;
        $this->view->distractions = $distractions;
    }

    /*
     * Speichern der zugeordneten Ablenkungen
     */
    public function saveDistractionAction() {
        $request = $this->getRequest()->getPost('distraction_ids');
        // Gespeicherte Patient ID holen
        $ns = new Zend_Session_Namespace('edit-distraction');
        $patientID = $ns->patient_id;
        $runthrough = $ns->runthrough;

        $distractionHasPatientMapper = new Application_Model_DistractionHasPatientMapper();
        $distractionHasPatientMapper->saveAll($patientID, $request);


        if ($runthrough) {
            $this->view->redirect = '/patient/edit-maxim/' . $patientID . '/1';
        } else {
            $this->view->redirect = "/patient/list";
        }

        Zend_Session::namespaceUnset('edit-distraction');
    }

    
    public function runThroughAction() {
        $patientID = $this->getParam('id');
        $this->view->emergencyLink = '/emergency-case/create/' . $patientID;
        $this->view->startseite = '/index';
    }

    /*
     * Senden der E-Mail an Patienten mit dem Token
     */
    private function sendConfirmEmail(Application_Model_Patient_Patient $patient) {
        $config = Zend_Controller_Front::getInstance()->getParam('bootstrap');
        $mailsettings = $config->getOption('mailsettings');

        $html = new Zend_View();
        $html->setScriptPath($mailsettings['templatePath']);



        // assign valeues
        $html->assign('name', $patient->getFirstname());
        $html->assign('token', $patient->getToken());

        // create mail object
        $mail = new Zend_Mail('utf-8');



        $mailServerUrl = $mailsettings['mailserver'];

        $from = $mailsettings['fromEmail'];

        $transport = new Zend_Mail_Transport_Smtp($mailServerUrl);

        // render view
        $bodyText = $html->render('confirmEmail.phtml');

//        $mail = new Zend_Mail();
        $mail->setBodyHtml($bodyText);
        $mail->setFrom($from);
        $mail->addTo($patient->getEmail());
        $mail->setSubject('Suchtprophylaxe Bestätigung');
//        $mail->send($transport);
        $mail->send();
    }

}
