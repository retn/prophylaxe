<?php

/*
 * Controller zum Verwalten der Anlaufstellen
 */
class ContactPointController extends Zend_Controller_Action {

    public function init() {
        $contextSwitch = $this->_helper->getHelper('contextSwitch');
        $contextSwitch->addActionContext('delete', 'json')
                ->initContext();
        $contextSwitch->addActionContext('getcontactpoints', 'json')
                ->initContext();
    }

    public function indexAction() {
        $this->view->jQuery()->addJavascriptFile('/js/datatable/jquery.dataTables.min.js')
                ->addStylesheet('/css/jquery.dataTables.css')
                ->addJavascriptFile('/js/contactpoint/ContactPointTable.js')
                ->addJavascriptFile('/js/datatable/dataTable.fnReloadAjax.js');

        $mapper = new Application_Model_ContactPointMapper();
        $contactPoints = $mapper->fetchAll();

        $this->view->contactPoints = $contactPoints;
    }

    /*
     * Erstellen einer Anlaufstelle
     */
    public function createAction() {
        $form = new Application_Form_ContactPoint();
        $request = $this->getRequest();

        if ($this->getRequest()->isPost()) {
            if ($form->isValid($request->getPost())) {
                $contactpoint = new Application_Model_ContactPoint($form->getValues());
                $mapper = new Application_Model_ContactPointMapper();
                $mapper->save($contactpoint);
                return $this->_helper->redirector('index');
            }
        }
        $this->view->form = $form;
    }

    /*
     * Bearbeiten einer Anlaufstelle
     */
    public function editAction() {
        // Gleiches Template verwenden wie bei create
        $this->_helper->viewRenderer('create');
        
        $contactpointID = $this->getParam('id');

        $form = new Application_Form_ContactPoint();
        $form->getElement('submit')->setLabel("Daten ändern");

        $request = $this->getRequest();

        // Wenn das Formular abgesedet wurde, neue Daten Speichern
        // Wenn kein post Request vorliegt => Erster Seitenaufruf, Patient wird anhand der uebergebenen ID ins Formular eingetragen.

        if ($this->getRequest()->isPost()) {
            if ($form->isValid($request->getPost())) {
                $contactpoint = new Application_Model_ContactPoint($form->getValues());
                $mapper = new Application_Model_ContactPointMapper();
                $mapper->save($contactpoint);
                return $this->_helper->redirector('index');
            }
        } else {
            $contactpoint = new Application_Model_ContactPoint();
            $mapper = new Application_Model_ContactPointMapper();
            $mapper->find($contactpointID, $contactpoint);
            $form->populate($contactpoint->getKeyValueArray());
        }

        $this->view->form = $form;
    }

    /*
     * Loeschen einer Anlaufstelle
     */
    public function deleteAction() {
        $post_data = $this->getRequest()->getPost();

        $mapper = new Application_Model_ContactPointMapper();
        $anzDeletedRows = $mapper->delete($post_data['id']);

        $this->view->success = ($anzDeletedRows > 0);
    }

    /*
     * Liefert alle Anlaufstellen
     */
    public function getcontactpointsAction() {
//        $mapper = new Application_Model_ContactPointMapper();
//        $contactPoints = $mapper->fetchAll();
//
//        $this->view->contactPoints = $contactPoints;
        
        $mapper = new Application_Model_ContactPointMapper();
        $daten = $mapper->fetchAll();

        $json = array();
        foreach ($daten as $value) {
            $json[] = $value->getArray();
        }

        $this->view->aaData = $json;
    }

}

