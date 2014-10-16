/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.versatel.noc.vsafe.server.mvc.controllers;

//import de.versatel.noc.vSafe_Server.system.SystemProperties;

import de.versatel.noc.vSafe.mvc.AbstractController;


/**
 *
 * @author ulrich.stemmer
 */
public class SystemPropertiesController extends AbstractController {

    public void changeSystemProperty(String propertyName, Object newValue) {
        setModelProperty(propertyName, newValue);
    }
}
