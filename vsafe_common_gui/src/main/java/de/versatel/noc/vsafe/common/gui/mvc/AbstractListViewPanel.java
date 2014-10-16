package de.versatel.noc.vsafe.common.gui.mvc;

import java.beans.IndexedPropertyChangeEvent;
import javax.swing.JPanel;

public abstract class AbstractListViewPanel extends JPanel {
    
    public abstract void modelPropertyChange(IndexedPropertyChangeEvent evt);
   
}
