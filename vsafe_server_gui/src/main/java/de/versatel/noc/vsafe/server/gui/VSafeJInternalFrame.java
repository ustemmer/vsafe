package de.versatel.noc.vsafe.server.gui;

import javax.swing.JInternalFrame;

/**
 *
 * @author ulrich.stemmer
 */
public class VSafeJInternalFrame extends JInternalFrame {

    protected final GuiHandling guiHandling;

    public VSafeJInternalFrame(GuiHandling guiHandling) {
        super();
        this.guiHandling = guiHandling;
    }

    public GuiHandling getGuiHandling() {
        return guiHandling;
    }

    
}
