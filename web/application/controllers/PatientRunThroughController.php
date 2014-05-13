<?php

class PatientRunThroughController extends Zend_Controller_Action
{

    public function init()
    {
        /* Initialize action controller here */
    }

    public function indexAction()
    {
        // action body
    }

    public function emergencyCaseAction()
    {
        $this->view->jQuery()->addJavascriptFile('/js/patient-run-through/PatientRunThrough.js');

        $this->view->jQuery()->addJavascriptFile('/js/datatable/jquery.dataTables.min.js')
                ->addJavascriptFile('/js/datatable/dataTable.fnReloadAjax.js')
                ->addJavascriptFile('/js/maxim/MaximTable.js')
                ->addStylesheet('/css/jquery.dataTables.css');
    }

    public function maximAction()
    {

        $this->_helper->layout->disableLayout();
        // action body
    }

    public function runThroughAction()
    {
        // action body
    }


}


