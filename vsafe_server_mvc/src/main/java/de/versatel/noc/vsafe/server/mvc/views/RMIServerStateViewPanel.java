/*
 * RMIServerStateViewPanel.java
 *
 * Created on 21.06.2011, 18:24:54
 */
package de.versatel.noc.vsafe.server.mvc.views;

import de.versatel.noc.vSafe_Server.mvc.models.RMIServerModel;
import de.versatel.noc.vSafe_Server.mvc.controllers.RMIServerController;
import de.versatel.noc.vSafe_Server.rmi.RMIServerHandling.ServerState;

import de.versatel.noc.vSafe.mvc.AbstractViewPanel;
import de.versatel.noc.vSafe.util.BlinkLabel;
import de.versatel.noc.vSafe.util.IconLibrary;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import java.beans.PropertyChangeEvent;

/**
 *
 * @author ulrich.stemmer
 */
public class RMIServerStateViewPanel extends AbstractViewPanel {

    private final RMIServerController controller;
    private ServerState state = ServerState.STOPPED;
    private BlinkLabel blinkLabel_RMIState;
    private Dimension textDimension = new Dimension(50, 18);
    private Dimension iconDimension = new Dimension(20, 20);
    private boolean iconsComplete;
    private ImageIcon stateStoppedIcon;
    private ImageIcon stateStartingBlinkingOnIcon;
    private ImageIcon stateStartingBlinkingOffIcon;
    private ImageIcon stateStartedIcon;
    private ImageIcon stateStoppingBlinkingOnIcon;
    private ImageIcon stateStoppingBlinkingOffIcon;

    /** Creates new form RMIServerStateViewPanel */
    public RMIServerStateViewPanel(RMIServerController controller) {
        super.addPropertyChangeListener(controller);
        this.controller = controller;
        init();
        //initComponents();
    }

    private void init() {

        blinkLabel_RMIState = new BlinkLabel();
        IconLibrary il = new IconLibrary();
        stateStoppedIcon = il.getImageIcon(IconLibrary.StateStoppedIcon);
        stateStartingBlinkingOnIcon = il.getImageIcon(IconLibrary.StateStartingBlinkingOnIcon);
        stateStartingBlinkingOffIcon = il.getImageIcon(IconLibrary.StateStartingBlinkingOffIcon);
        stateStartedIcon = il.getImageIcon(IconLibrary.StateStartedIcon);
        stateStoppingBlinkingOnIcon = il.getImageIcon(IconLibrary.StateStoppingBlinkingOnIcon);
        stateStoppingBlinkingOffIcon = il.getImageIcon(IconLibrary.StateStoppingBlinkingOffIcon);

        setIconsComplete();

        if (iconsComplete) {
            blinkLabel_RMIState.setSize(iconDimension);
        } else {
            blinkLabel_RMIState.setSize(textDimension);
        }

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        blinkLabel_RMIState.setText("Server gestoppt");

        blinkLabel_RMIState.setBlinkingStarted(false);

        blinkLabel_RMIState.setOffBackgroundColor(blinkLabel_RMIState.getBackground());
        blinkLabel_RMIState.setOffDisabledIcon(stateStoppedIcon);
        blinkLabel_RMIState.setOffDuration(500);
        blinkLabel_RMIState.setOffEnabledIcon(stateStoppedIcon);
        blinkLabel_RMIState.setOffForegroundColor(blinkLabel_RMIState.getForeground());

        blinkLabel_RMIState.setOnBackgroundColor(blinkLabel_RMIState.getBackground());
        blinkLabel_RMIState.setOnDisabledIcon(stateStoppedIcon);
        blinkLabel_RMIState.setOnDuration(500);
        blinkLabel_RMIState.setOnEnabledIcon(stateStoppedIcon);
        blinkLabel_RMIState.setOnForegroundColor(blinkLabel_RMIState.getForeground());

        blinkLabel_RMIState.setMaximumSize(textDimension);
        blinkLabel_RMIState.setMinimumSize(textDimension);
        blinkLabel_RMIState.setPreferredSize(textDimension);

        blinkLabel_RMIState.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                //jLabel_RMIState.setDisabledIcon(null);
                //jLabel_RMIState.setIcon(null);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                //jLabel_RMIState.setDisabledIcon(null);
                //jLabel_RMIState.setIcon(null);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (state.equals(ServerState.STOPPED)) {
                    controller.changeSystemProperty(RMIServerModel.RMI_START, null);
                } else if (state.equals(ServerState.STARTED)) {
                    controller.changeSystemProperty(RMIServerModel.RMI_STOP, null);
                }
            }
        });
        add(blinkLabel_RMIState);
    }

    @Override
    public void modelPropertyChange(final PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(RMIServerModel.RMI_STATE)) {
            state = (ServerState) evt.getNewValue();
            if (state.equals(ServerState.STARTED)) {
                blinkLabel_RMIState.setEnabled(true);
                blinkLabel_RMIState.setBlinkingStarted(false);
                blinkLabel_RMIState.setToolTipText("Server gestartet");
                if (iconsComplete) {
                    blinkLabel_RMIState.setIcon(stateStartedIcon);
                } else {
                    blinkLabel_RMIState.setText("Server gestartet");
                }
            } else if (state.equals(ServerState.STOPPED)) {
                blinkLabel_RMIState.setEnabled(true);
                blinkLabel_RMIState.setBlinkingStarted(false);
                blinkLabel_RMIState.setToolTipText("Server gestoppt");
                if (iconsComplete) {
                    blinkLabel_RMIState.setIcon(stateStoppedIcon);
                } else {
                    blinkLabel_RMIState.setText("Server gestoppt");
                }
            } else if (state.equals(ServerState.STARTING)) {
                blinkLabel_RMIState.setEnabled(false);
                blinkLabel_RMIState.setBlinkingStarted(true);
                blinkLabel_RMIState.setToolTipText("Server startet...");
                if (iconsComplete) {
                    blinkLabel_RMIState.setOnDisabledIcon(stateStartingBlinkingOnIcon);
                    blinkLabel_RMIState.setOffDisabledIcon(stateStartingBlinkingOffIcon);
                } else {
                    blinkLabel_RMIState.setText("Server startet...");
                }
            } else if (state.equals(ServerState.STOPPING)) {
                blinkLabel_RMIState.setEnabled(false);
                blinkLabel_RMIState.setBlinkingStarted(true);
                blinkLabel_RMIState.setToolTipText("Server stoppt...");
                if (iconsComplete) {
                    blinkLabel_RMIState.setOnDisabledIcon(stateStoppingBlinkingOnIcon);
                    blinkLabel_RMIState.setOffDisabledIcon(stateStoppingBlinkingOffIcon);
                } else {
                    blinkLabel_RMIState.setText("Server stoppt...");
                }
            }
        }
    }

    public void setStateStartedIcon(ImageIcon stateStartedIcon) {
        this.stateStartedIcon = stateStartedIcon;
        setIconsComplete();
    }

    public void setStateStartingBlinkingOffIcon(ImageIcon stateStartingBlinkingOffIcon) {
        this.stateStartingBlinkingOffIcon = stateStartingBlinkingOffIcon;
        setIconsComplete();
    }

    public void setStateStartingBlinkingOnIcon(ImageIcon stateStartingBlinkingOnIcon) {
        this.stateStartingBlinkingOnIcon = stateStartingBlinkingOnIcon;
        setIconsComplete();
    }

    public void setStateStoppedIcon(ImageIcon stateStoppedIcon) {
        this.stateStoppedIcon = stateStoppedIcon;
        setIconsComplete();
    }

    public void setStateStoppingBlinkingOffIcon(ImageIcon stateStoppingBlinkingOffIcon) {
        this.stateStoppingBlinkingOffIcon = stateStoppingBlinkingOffIcon;
        setIconsComplete();
    }

    public void setStateStoppingBlinkingOnIcon(ImageIcon stateStoppingBlinkingOnIcon) {
        this.stateStoppingBlinkingOnIcon = stateStoppingBlinkingOnIcon;
        setIconsComplete();
    }

    private void setIconsComplete() {
        if (stateStoppedIcon != null
                && stateStartingBlinkingOnIcon != null
                && stateStartingBlinkingOffIcon != null
                && stateStartedIcon != null
                && stateStoppingBlinkingOnIcon != null
                && stateStoppingBlinkingOffIcon != null) {
            iconsComplete = true;
        }else {
        iconsComplete = true;
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
