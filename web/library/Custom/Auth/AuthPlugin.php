<?php

/*
 * Checkt ob der Benutzer berechtigt ist auf die Seite zuzugreifen.
 * 
 */

class Custom_Auth_AuthPlugin extends Zend_Controller_Plugin_Abstract {

    private $_acl;
    function __construct(Zend_acl $acl) {
        $this->_acl = $acl;
    }

    public function preDispatch(Zend_Controller_Request_Abstract $request) {

        $loginController = 'auth';
        $loginAction = 'login';
        
        // Angeforderte Resource
        $resource = $request->getControllerName();
        $action = $request->getActionName();

        // Benutzerrolle holen
        // Pruefen ob Benutzerrolle zugriff hat, wenn nicht auf Login weiterleiten
        if (!$this->_acl->isAllowed(Zend_Registry::get('user_role'), $resource, $action)) {
           
            $request->setControllerName($loginController);
            $request->setActionName($loginAction);
        }
    }

}