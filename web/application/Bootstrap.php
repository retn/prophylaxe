<?php

class Bootstrap extends Zend_Application_Bootstrap_Bootstrap {

    private $_acl = null;

    protected function _initDoctype() {
//        Zend_Loader::loadClass('Application_Plugin_Auth_AclList', '/');
        $this->bootstrap('view');
        $view = $this->getResource('view');
        $view->doctype('HTML5');

        $view->addHelperPath('ZendX/JQuery/View/Helper/', 'ZendX_JQuery_View_Helper');
//        $view->jQuery()->addStylesheet('/js/jquery/css/ui-lightness/jquery-ui-1.10.3.custom.css');
        $view->jQuery()->addStylesheet('/js/jquery/css/custom-theme/jquery-ui-1.10.4.custom.css');
        $view->jQuery()->setLocalPath('/js/jquery/js/jquery-1.9.1.js');
        $view->jQuery()->setUiLocalPath('/js/jquery/js/jquery-ui-1.10.3.custom.min.js');



        $view->jQuery()->enable();
    }

    protected function _initAutoloader() {
        $autoloader = Zend_Loader_Autoloader::getInstance();
        $autoloader->registerNamespace('Custom_');
    }

    protected function _initPlugins() {

        if (Zend_Auth::getInstance()->hasIdentity()) {
            Zend_Registry::set('user_role', Zend_Auth::getInstance()->getStorage()->read()->user_role);
        } else {
            Zend_Registry::set('user_role', 'guest');
        }
        $this->_acl = new Custom_Auth_AclList();

        $frontController = Zend_Controller_Front::getInstance();
        $frontController->registerPlugin(new Custom_Auth_AuthPlugin($this->_acl));
    }

    protected function _initViewHelpers() {

        $this->bootstrap('view');
        $view = $this->getResource('view');

        $menuContainerConfig = new Zend_Config_Xml(APPLICATION_PATH . '/configs/menu.xml', 'nav');
        $menuContainer = new Zend_Navigation($menuContainerConfig);
        $view->navigation($menuContainer)->setAcl($this->_acl)
                ->setRole(Zend_Registry::get('user_role'));


        $footerConfig = new Zend_Config_Xml(APPLICATION_PATH . '/configs/menu.xml', 'footer');
        $footer = new Zend_Navigation($footerConfig);
        $view->footer = $footer;
    }

    public function _initRoute() {


        $frontController = Zend_Controller_Front::getInstance();

        // define new route class 
        // this route with define the route for 
        // http://www.example.com/patient/edit/10
        // the id of the product found under variable name id
        // to retrive it $this->getRequest->getParam('id')  
        // uebergibt die zu bearbeitende Patienten ID
        $route = new Zend_Controller_Router_Route(
                'patient/edit/:id/', array(
            'controller' => 'patient',
            'action' => 'edit',
            'id' => 'id'));

        // add this route to the front controller 
        $frontController->getRouter()->addRoute('editPatient', $route);

        $patientRunThrough = new Zend_Controller_Router_Route(
                'patient/run-through/:id/', array(
            'controller' => 'patient',
            'action' => 'run-through'));
        $frontController->getRouter()->addRoute('patientRunThrough', $patientRunThrough);

        $editPatientMaxim = new Zend_Controller_Router_Route(
                'patient/edit-maxim/:id/:runthrough', array(
            'runthrough' => '0',
            'controller' => 'patient',
            'action' => 'edit-maxim'));

        // add this route to the front controller 
        $frontController->getRouter()->addRoute('editPatientMaxim', $editPatientMaxim);

        $editPatientDistraction = new Zend_Controller_Router_Route(
                'patient/edit-distraction/:id/:runthrough', array(
            'runthrough' => '0',
            'controller' => 'patient',
            'action' => 'edit-distraction'));

        // add this route to the front controller 
        $frontController->getRouter()->addRoute('editPatientDistraction', $editPatientDistraction);

        $editContactPoint = new Zend_Controller_Router_Route(
                'contact-point/edit/:id', array(
            'controller' => 'contact-point',
            'action' => 'edit',
            'id' => 'id'));

        // add this route to the front controller 
        $frontController->getRouter()->addRoute('editContactpoint', $editContactPoint);

        $createEmegencyCase = new Zend_Controller_Router_Route(
                'emergency-case/create/:id/:runthrough', array(
            'runthrough' => '0',
            'controller' => 'emergency-case',
            'action' => 'create'));

        // add this route to the front controller 
        $frontController->getRouter()->addRoute('createEmegencyCase', $createEmegencyCase);

        $editEmergencyCase = new Zend_Controller_Router_Route(
                'emergency-case/edit/:id', array(
            'controller' => 'emergency-case',
            'action' => 'edit',
            'id' => 'id'));

        // add this route to the front controller 
        $frontController->getRouter()->addRoute('editEmergencyCase', $editEmergencyCase);
    }

}
