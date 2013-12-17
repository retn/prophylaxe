<?php

class DistractionController extends Zend_Controller_Action {

    public function init() {
        $contextSwitch = $this->_helper->getHelper('contextSwitch');
        $contextSwitch->addActionContext('get-distractions', 'json')
                ->initContext();
        $contextSwitch->addActionContext('save', 'json')
                ->initContext();
        $contextSwitch->addActionContext('delete', 'json')
                ->initContext();
    }

    public function indexAction() {
        $this->view->jQuery()->addJavascriptFile('/js/datatable/jquery.dataTables.min.js')
                ->addJavascriptFile('/js/datatable/dataTable.fnReloadAjax.js')
                ->addJavascriptFile('/js/distraction/DistractionTable.js')
                ->addStylesheet('/css/jquery.dataTables.css');



        $form = new Application_Form_Distraction();
//        
        $this->view->form = $form;
    }

    public function getDistractionsAction() {
        $mapper = new Application_Model_DistractionMapper();
        $daten = $mapper->fetchAll();

        $json = array();
        foreach ($daten as $value) {
            $json[] = $value->getArray();
        }

        $this->view->aaData = $json;
    }

    public function saveAction() {
        $form = new Application_Form_Distraction();
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
            $distraction = new Application_Model_Distraction($form->getValues());
            $mapper = new Application_Model_DistractionMapper();
            $mapper->save($distraction);
        }
    }

    public function deleteAction() {
        $post_data = $request = $this->getRequest()->getPost();

        $mapper = new Application_Model_DistractionMapper();
        $anzDeletedRows = $mapper->delete($post_data['id']);

        $this->view->success = ($anzDeletedRows > 0);
    }

}

