<?php

class Custom_Auth_AclList extends Zend_Acl {

    function __construct() {


        // Controller script names. You have to add all of them if credential check
        // is global to your application.
        $controllers = array('app','auth', 'error', 'index', 'patient', 'contact-point','maxim','distraction','emergency-case','patient-run-through');

        foreach ($controllers as $controller) {
            $this->add(new Zend_Acl_Resource($controller));
        }

        $this->addRole('guest');
        $this->addRole('therapist','guest');
        // admin inherits  all of the rules of therapist
        $this->addRole('admin', 'therapist');


        // allow(benutzer, controller, action methoden)
        // add privileges to roles and resource combinations
        $this->allow('guest', array('error','app'));
        $this->allow('guest', 'auth');
        // Kein Zugriff auf Logout
        $this->deny('guest', 'auth', 'logout');
        
        // Therapist soll zugriff auf Logout haben
        $this->allow('therapist', 'auth', 'logout');
        // Zugriff auf index und patient controller
        $this->allow('therapist', array('index','patient','maxim','distraction','emergency-case','patient-run-through'));

        // admin hat zusaetzlich zugriff auf anlaufstellen
        $this->allow('admin', 'contact-point');
//        $this->deny('admin', 'contactpoint',array('edit','create'));
            
    }

}