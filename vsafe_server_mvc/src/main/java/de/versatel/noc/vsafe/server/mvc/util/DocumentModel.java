/*
 * DocumentModel.java
 *
 * Created on January 22, 2007, 3:12 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package de.versatel.noc.vsafe.server.mvc.util;

import de.versatel.noc.vSafe.mvc.AbstractModel;

/**
 * A sample class that mimics some properties found in a document, including
 * its name, width, and height.
 *
 * @author Robert Eckstein
 */
public class DocumentModel extends AbstractModel
{

    public static final String NAME = "docname";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    
    /**
     * Default constructor
     */
    public DocumentModel()
    {
        addProperty (new MVCProperty(NAME,"Sample Document"));
        addProperty (new MVCProperty(WIDTH,500));
        addProperty (new MVCProperty(HEIGHT,500));
    }
}
    

