package de.versatel.noc.vsafe.server.mvc.controllers;

import de.versatel.noc.vSafe.mvc.AbstractController;

/**
 *
 * @author ulrich.stemmer
 */
public class RMIServerController  extends AbstractController{

    public RMIServerController() {
        super();
    }

        public void changeSystemProperty(String propertyName, Object newValue) {

        setModelProperty(propertyName, newValue);
    }
}
