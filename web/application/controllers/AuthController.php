<?php

/*
 * Regelt den Login/Logout eines Users
 */
class AuthController extends Zend_Controller_Action {

    public function init() {
        /* Initialize action controller here */
    }

    public function indexAction() {
        $this->_redirect('auth/login/');
    }

    /*
     * Wird aufgerufen bei einem Login
     */
    public function loginAction() {
        // Extra Layout fuer Login setzten
        $this->_helper->layout->setLayout('loginpage');
             
        
        $db = $this->_getParam('db');

        $loginForm = new Application_Form_Login();
        if ($this->getRequest()->isPost()) {
            if ($loginForm->isValid($_POST)) {

                $adapter = $this->getAuthAdapter();

                $adapter->setIdentity($loginForm->getValue('username'));
                $adapter->setCredential($loginForm->getValue('password'));

                $auth = Zend_Auth::getInstance();
                $result = $auth->authenticate($adapter);

                if ($result->isValid()) {
                    $storage = $auth->getStorage();
                    // die gesamte Tabellenzeile in der Session speichern,
                    // wobei das Passwort unterdrückt wird
                    $storage->write($adapter->getResultRowObject(null, 'password'));

//                    $this->_helper->FlashMessenger('Successful Login');
                    $this->_redirect('index/index');
                    return;
                } else {
                    $this->view->errorMessage = "Benutzername oder Passwort ist falsch.";
                }
            }
        }

        $this->view->loginForm = $loginForm;
    }

    /*
     * Wird aufgerufen bei Logout
     */
    public function logoutAction() {
        Zend_Auth::getInstance()->clearIdentity();
        $this->view->logout = true;
        $this->_redirect('auth/login');
    }

    private function getAuthAdapter() {
        $authAdapter = new Zend_Auth_Adapter_DbTable(Zend_Db_Table::getDefaultAdapter());
        $authAdapter->setTableName('spl_user');
        $authAdapter->setIdentityColumn('user_name');
        $authAdapter->setCredentialColumn('password');
        $authAdapter->setCredentialTreatment('MD5(?)');
        
        return $authAdapter;
    }

}

