/*
 * RMIServerStateViewPanel.java
 *
 * Created on 21.06.2011, 18:24:54
 */
package de.versatel.noc.vsafe.server.mvc.views;
import de.versatel.noc.vSafe_Server.database.MySQL_PoolHandling.PoolState;
import de.versatel.noc.vSafe_Server.mvc.models.MySQLPoolModel;
import de.versatel.noc.vSafe_Server.mvc.controllers.MySQLPoolController;

import de.versatel.noc.vSafe.mvc.AbstractViewPanel;
import de.versatel.noc.vSafe.system.SystemSettings;
import de.versatel.noc.vSafe.util.BlinkLabel;
import de.versatel.noc.vSafe.util.IconLibrary;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import java.beans.PropertyChangeEvent;

/**
 *
 * @author ulrich.stemmer
 */
public class MySQLPoolStateViewPanel extends AbstractViewPanel {

    private final MySQLPoolController mySQLPoolController;
    private PoolState state = PoolState.DISCONNECTED;
    private BlinkLabel blinkLabel_MySQLPoolState;
    private Dimension textDimension = new Dimension(50, 18);
    private Dimension iconDimension = new Dimension(20, 20);
    private boolean iconsComplete;
    private ImageIcon stateSQLDisconnectedIcon;
    private ImageIcon stateSQLConnectingBlinkingOnIcon;
    private ImageIcon stateSQLConnectingBlinkingOffIcon;
    private ImageIcon stateSQLConnectedIcon;
    private ImageIcon stateSQLDisconnectingBlinkingOnIcon;
    private ImageIcon stateSQLDisconnectingBlinkingOffIcon;

    /** Creates new form RMIServerStateViewPanel */
    public MySQLPoolStateViewPanel(MySQLPoolController controller) {
        super.addPropertyChangeListener(controller);
        this.mySQLPoolController = controller;
        init();
        //initComponents();
    }

    private void init() {

        blinkLabel_MySQLPoolState = new BlinkLabel();
        IconLibrary il = new IconLibrary();
        stateSQLDisconnectedIcon = il.getImageIcon(IconLibrary.StateStoppedIcon);
        stateSQLConnectingBlinkingOnIcon = il.getImageIcon(IconLibrary.StateStartingBlinkingOnIcon);
        stateSQLConnectingBlinkingOffIcon = il.getImageIcon(IconLibrary.StateStartingBlinkingOffIcon);
        stateSQLConnectedIcon = il.getImageIcon(IconLibrary.StateStartedIcon);
        stateSQLDisconnectingBlinkingOnIcon = il.getImageIcon(IconLibrary.StateStoppingBlinkingOnIcon);
        stateSQLDisconnectingBlinkingOffIcon = il.getImageIcon(IconLibrary.StateStoppingBlinkingOffIcon);

        setIconsComplete();

        if (iconsComplete) {
            blinkLabel_MySQLPoolState.setSize(iconDimension);
        } else {
            blinkLabel_MySQLPoolState.setSize(textDimension);
        }

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        blinkLabel_MySQLPoolState.setText("Server gestoppt");

        blinkLabel_MySQLPoolState.setBlinkingStarted(false);

        blinkLabel_MySQLPoolState.setOffBackgroundColor(blinkLabel_MySQLPoolState.getBackground());
        blinkLabel_MySQLPoolState.setOffDisabledIcon(stateSQLDisconnectedIcon);
        blinkLabel_MySQLPoolState.setOffDuration(500);
        blinkLabel_MySQLPoolState.setOffEnabledIcon(stateSQLDisconnectedIcon);
        blinkLabel_MySQLPoolState.setOffForegroundColor(blinkLabel_MySQLPoolState.getForeground());

        blinkLabel_MySQLPoolState.setOnBackgroundColor(blinkLabel_MySQLPoolState.getBackground());
        blinkLabel_MySQLPoolState.setOnDisabledIcon(stateSQLDisconnectedIcon);
        blinkLabel_MySQLPoolState.setOnDuration(500);
        blinkLabel_MySQLPoolState.setOnEnabledIcon(stateSQLDisconnectedIcon);
        blinkLabel_MySQLPoolState.setOnForegroundColor(blinkLabel_MySQLPoolState.getForeground());

        blinkLabel_MySQLPoolState.setMaximumSize(textDimension);
        blinkLabel_MySQLPoolState.setMinimumSize(textDimension);
        blinkLabel_MySQLPoolState.setPreferredSize(textDimension);

        blinkLabel_MySQLPoolState.addMouseListener(new java.awt.event.MouseAdapter() {

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
                if (state.equals(PoolState.DISCONNECTED)) {
                    mySQLPoolController.connect();
                } else if (state.equals(PoolState.CONNECTED)) {
                    mySQLPoolController.disconnect();
                }
            }
        });
        add(blinkLabel_MySQLPoolState);
    }

    @Override
    public void modelPropertyChange(final PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(MySQLPoolModel.POOL_CONNECTED)) {
            state = (PoolState) evt.getNewValue();
            if (state.equals(PoolState.CONNECTED)) {
                blinkLabel_MySQLPoolState.setEnabled(true);
                blinkLabel_MySQLPoolState.setBlinkingStarted(false);
                blinkLabel_MySQLPoolState.setToolTipText("SQL-Verbindungen sind aufgebaut");
                if (iconsComplete) {
                    blinkLabel_MySQLPoolState.setIcon(stateSQLConnectedIcon);
                } else {
                    blinkLabel_MySQLPoolState.setText("SQL-Server: verbunden");
                }
            } else if (state.equals(PoolState.DISCONNECTED)) {
                blinkLabel_MySQLPoolState.setEnabled(true);
                blinkLabel_MySQLPoolState.setBlinkingStarted(false);
                blinkLabel_MySQLPoolState.setToolTipText("SQL-Verbindungen sind getrennt.");
                if (iconsComplete) {
                    blinkLabel_MySQLPoolState.setIcon(stateSQLDisconnectedIcon);
                } else {
                    blinkLabel_MySQLPoolState.setText("SQL-Server: getrennt");
                }
            } else if (state.equals(PoolState.CONNECTING)) {
                blinkLabel_MySQLPoolState.setEnabled(false);
                blinkLabel_MySQLPoolState.setBlinkingStarted(true);
                blinkLabel_MySQLPoolState.setToolTipText("Baue SQL-Verbindungen auf...");
                if (iconsComplete) {
                    blinkLabel_MySQLPoolState.setOnDisabledIcon(stateSQLConnectingBlinkingOnIcon);
                    blinkLabel_MySQLPoolState.setOffDisabledIcon(stateSQLConnectingBlinkingOffIcon);
                } else {
                    blinkLabel_MySQLPoolState.setText("SQL-Server: verbinde...");
                }
            } else if (state.equals(PoolState.DISCONNECTING)) {
                blinkLabel_MySQLPoolState.setEnabled(false);
                blinkLabel_MySQLPoolState.setBlinkingStarted(true);
                blinkLabel_MySQLPoolState.setToolTipText("Trenne SQL-Verbindungen...");
                if (iconsComplete) {
                    blinkLabel_MySQLPoolState.setOnDisabledIcon(stateSQLDisconnectingBlinkingOnIcon);
                    blinkLabel_MySQLPoolState.setOffDisabledIcon(stateSQLDisconnectingBlinkingOffIcon);
                } else {
                    blinkLabel_MySQLPoolState.setText("SQL-Server: trenne...");
                }
            }
        }
    }

    public void setStateStartedIcon(ImageIcon stateSQLConnectedIcon) {
        this.stateSQLConnectedIcon = stateSQLConnectedIcon;
        setIconsComplete();
    }

    public void setStateSQLConnectingBlinkingOffIcon(ImageIcon stateSQLConnectingBlinkingOffIcon) {
        this.stateSQLConnectingBlinkingOffIcon = stateSQLConnectingBlinkingOffIcon;
        setIconsComplete();
    }

    public void setStateSQLConnectingBlinkingOnIcon(ImageIcon stateSQLConnectingBlinkingOnIcon) {
        this.stateSQLConnectingBlinkingOnIcon = stateSQLConnectingBlinkingOnIcon;
        setIconsComplete();
    }

    public void setStateSQLDisconnectedIcon(ImageIcon stateSQLDisconnectedIcon) {
        this.stateSQLDisconnectedIcon = stateSQLDisconnectedIcon;
        setIconsComplete();
    }

    public void setStateSQLDisconnectingBlinkingOffIcon(ImageIcon stateSQLDisconnectingBlinkingOffIcon) {
        this.stateSQLDisconnectingBlinkingOffIcon = stateSQLDisconnectingBlinkingOffIcon;
        setIconsComplete();
    }

    public void setStateSQLDisconnectingBlinkingOnIcon(ImageIcon stateSQLDisconnectingBlinkingOnIcon) {
        this.stateSQLDisconnectingBlinkingOnIcon = stateSQLDisconnectingBlinkingOnIcon;
        setIconsComplete();
    }

    private void setIconsComplete() {
        if (stateSQLDisconnectedIcon != null
                && stateSQLConnectingBlinkingOnIcon != null
                && stateSQLConnectingBlinkingOffIcon != null
                && stateSQLConnectedIcon != null
                && stateSQLDisconnectingBlinkingOnIcon != null
                && stateSQLDisconnectingBlinkingOffIcon != null) {
            iconsComplete = true;
        } else {
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
