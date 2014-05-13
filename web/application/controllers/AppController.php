<?php

/*
 * Controller fuer die Schnittstellen
 * Siehe Schnittstellen.pdf
 */
class AppController extends Zend_Controller_Action {

    private $OK = 1;
    private $EMAIL_NOT_FOUND = 2;
    private $TOKEN_INVALID = 3;
    private $TOKEN_USED = 4;
    private $DATA_INCOMPLETE = 5;

    public function init() {
//        $contextSwitch = $this->_helper->getHelper('contextSwitch');
        //app/get-patient/format/json',
//        $contextSwitch->addActionContext('get-patient', 'json')
//                   ->initContext();
    }

    public function indexAction() {
        // action body
    }

    /*
     * Liefert einen Patienten
     * @params token,email
     */
    public function getPatientAction() {
        // Post variablen holen
        $token = $this->getRequest()->getParam('token');
        $email = $this->getRequest()->getParam('email');

        if (empty($token) || empty($email)) {
            exit();
        }
        $json = array();

        // Patientendaten
        $patient = new Application_Model_Patient_Patient();
        $patientMapper = new Application_Model_Patient_PatientMapper();
//        $json['patient'] = $patient->getKeyValueArray();
//        
//        
        // Wenn kein Patient mit der uebertragenen E-Mail gefunden wurde
        if (!$patientMapper->findByEmail($email, $patient)) {
            $json['data']['status'] = array('statuscode' => $this->EMAIL_NOT_FOUND, 'message' => 'E-Mail not found!');
        } else {
            // Pruefen ob dem Patienten alle benoetigten Daten zugewiesen wurden.
            if (!$patient->hasValidData()) {
                $json['data']['status'] = array('statuscode' => $this->DATA_INCOMPLETE, 'message' => 'Data incomplete!');
            } else {
                // Token auf Uebereinstimmung pruefen
                if ($token != $patient->getToken()) {
                    $json['data']['status'] = array('statuscode' => $this->TOKEN_INVALID, 'message' => 'Token invalid!');
                } else {
                    // Pruefen ob das Token schon verwendet wurde
                    if ($patient->getToken_used()) {
                        $json['data']['status'] = array('statuscode' => $this->TOKEN_USED, 'message' => 'Token already used!');
                    } else {

                        // Zugehoeriger Notfallkoffer
                        $emergencyCaseMapper = new Application_Model_EmergencyCase_EmergencyCaseMapper();
                        $emergencyCase = new Application_Model_EmergencyCase_EmergencyCase();
                        $emergencyCase = $emergencyCaseMapper->findEmergencyCaseOfPatient($patient->getId());
                        $json['data']['emergency-case'] = $emergencyCase->getKeyValueArray();

                        // Zugehoerige Sprueche
                        $maximMapper = new Application_Model_MaximMapper();
                        $maxims = $patientMapper->getMaximsFromPatient($patient->getId());
                        foreach ($maxims as $maxim) {
                            $json['data']['maxims'][] = $maxim->getKeyValueArray();
                        }

                        // Zugehoerige Ablenkungen
                        $distractions = $patientMapper->getDistractionsFromPatient($patient->getId());
                        foreach ($distractions as $distraction) {
                            $json['data']['distractions'][] = $distraction->getKeyValueArray();
                        }

                        $json['data']['status'] = array('statuscode' => $this->OK, 'message' => 'Ok');

                        $patient->setToken_used(true);
//                        $patientMapper->save($patient);
                    }
                }
            }
        }



        $this->view->aaData = $json;

//        echo json_encode($json, JSON_PRETTY_PRINT);

        echo Zend_Json::encode($json);
        exit();
//        $this->_helper->viewRenderer->setNoRender(true);
    }

    /*
     * Zum testen fuer die App
     */
    public function testAction() {
        $_POST['token'] = '123';
        $_POST['email'] = 'o.mckinney@localhost.com';

        $this->getPatientAction();
    }

    /*
     * Liefert alle Anlaufstellen
     */
    public function getContactpointsAction() {
        // action body
        $contactPointMapper = new Application_Model_ContactPointMapper();
        $contactPoints = $contactPointMapper->fetchAll();
        $json = array();

        foreach ($contactPoints as $cp) {
            $json[] = $cp->getKeyValueArray();
        }
        echo Zend_Json::encode($json);
        exit();
    }

    /*
     * Liefert Timestamp wann die zuletzt eine Anlaufstelle bearbeitet wurde
     */
    public function getContactpointTimestampAction() {
        // action body
        $contactPointMapper = new Application_Model_ContactPointMapper();
        $timestamp = $contactPointMapper->fetchLatestTimestamp();

         echo Zend_Json::encode(array('timestamp' => $timestamp));
         exit();
    }

}

