package de.versatel.noc.vsafe.server.mvc.util;

import de.versatel.noc.vSafe.mvc.AbstractModel;
import java.awt.Font;
//import java.beans.PropertyChangeEvent;

/**
 * A sample class that mimics some properties found in a text element, including
 * its X and Y position, rotation, opacity, string, and font.
 *
 * @author Robert Eckstein
 */
public class TextElementModel extends AbstractModel {

    public static final String TEXT = "text";
    public static final String FONT = "font";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String OPACITY = "opacity";
    public static final String ROTATION = "rotation";

    /**
     * Default constructor
     */
    public TextElementModel() {
        addProperty (new MVCProperty(TEXT, "Sample Text"));
        addProperty (new MVCProperty(FONT, new Font("Arial", Font.BOLD, 24)));
        addProperty (new MVCProperty(X, 50));
        addProperty (new MVCProperty(Y, 50));
        addProperty (new MVCProperty(OPACITY, 89));
        addProperty (new MVCProperty(ROTATION, 0));
    }
}
