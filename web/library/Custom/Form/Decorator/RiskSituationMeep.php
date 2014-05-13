<?php

class Custom_Form_Decorator_RiskSituationMeep extends Zend_Form_Decorator_Abstract {
    
    protected $_format = '<input id="%s" name="%s" type="text" value="%s"/><button name="btnRemove" class="emergency_case_delete_button">L&ouml;schen</button>';

    public function render($content) {
        $element = $this->getElement();
        $name = htmlentities($element->getFullyQualifiedName());
        $label = htmlentities($element->getLabel());
        $id = htmlentities($element->getId());
        $value = htmlentities($element->getValue());

        $markup = sprintf($this->_format, $id, $name, $value);
//        return $markup;
        return $content . $markup;
    }

}