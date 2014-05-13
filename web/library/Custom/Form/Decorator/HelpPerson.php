<?php

class Custom_Form_Decorator_HelpPerson extends Zend_Form_Decorator_Abstract {

    public function render($content) {
        $element = $this->getElement();
        if (!$element instanceof Custom_Form_Element_HelpPerson) {
            // only want to render Date elements
            return $content;
        }

        $view = $element->getView();
        if (!$view instanceof Zend_View_Interface) {
            // using view helpers, so do nothing if no view present
            return $content;
        }

        $namePerson = $element->getNamePerson();
        $phoneNumber = $element->getPhoneNumber();

        $name = $element->getFullyQualifiedName();

        $params = array(
            'size' => 10,
//            'maxlength' => 2,
        );
//        $yearParams = array(
//            'size'      => 4,
//            'maxlength' => 4,
//        );

        $markup = $view->formLabel($name . '[name]', 'Name'). $view->formText($name . '[name]', $namePerson, $params)
                . ' / '  . $view->formLabel($name . '[phonenumber]', 'Nummer') .  $view->formText($name . '[phonenumber]', $phoneNumber, $params)
                . '<button name="btnRemove" class="emergency_case_delete_button">L&ouml;schen</button>';

        switch ($this->getPlacement()) {
            case self::PREPEND:
                return $markup . $this->getSeparator() . $content;
            case self::APPEND:
            default:
                return $content . $this->getSeparator() . $markup;
        }
    }

}
