<?php

class MaximController extends Zend_Controller_Action {

    public function init() {
        // action getmaxims soll json zurueckliefern
        $contextSwitch = $this->_helper->getHelper('contextSwitch');
        $contextSwitch->addActionContext('getmaxims', 'json')
                ->initContext();
        $contextSwitch->addActionContext('save', 'json')
                ->initContext();
        $contextSwitch->addActionContext('delete', 'json')
                ->initContext();
    }

    public function indexAction() {
        $this->view->jQuery()->addJavascriptFile('/js/datatable/jquery.dataTables.min.js')
                ->addJavascriptFile('/js/datatable/dataTable.fnReloadAjax.js')
                ->addJavascriptFile('/js/maxim/MaximTable.js')
                ->addStylesheet('/css/jquery.dataTables.css');



        $form = new Application_Form_Maxim();
//        
        $this->view->form = $form;

//        $maxim = new Application_Model_Maxim(array('id' => "2", "text" => "fck"));
//        $mapper = new Application_Model_MaximMapper();
//        $mapper->save($maxim);
//        $this->view->maxims = $mapper->fetchAll();
    }


    public function getmaximsAction() {
        $mapper = new Application_Model_MaximMapper();

        $daten = $mapper->fetchAll();



        $json = array();
        foreach ($daten as $value) {
            $json[] = $value->getArray();
        }

        $this->view->aaData = $json;
    }

    public function saveAction() {
        $form = new Application_Form_Maxim();
        //unset($_POST['captcha']);
        $post_data = $request = $this->getRequest()->getPost();

        $form_data = array();
        foreach ($post_data['form'] as $value) {
            $form_data[$value['name']] = $value['value'];
        }

        $valid = $form->isValidPartial($form_data);
//        $this->_helper->json($form->getMessages());

        $messages = $form->getMessages();



        $this->view->valid = $valid;
        $this->view->messages = $messages;
        if ($valid) {
            $maxim = new Application_Model_Maxim($form->getValues());
            $mapper = new Application_Model_MaximMapper();
            $mapper->save($maxim);
        }
    }

    public function deleteAction() {
        $post_data = $request = $this->getRequest()->getPost();

        $mapper = new Application_Model_MaximMapper();
        $anzDeletedRows = $mapper->delete($post_data['id']);

        $this->view->success = ($anzDeletedRows > 0);
    }

}

