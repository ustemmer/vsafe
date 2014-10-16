/*
 * MySQLPoolViewPanel.java
 *
 * Created on 08.11.2011, 13:09:38
 */
package de.versatel.noc.vsafe.server.mvc.views;

import de.versatel.noc.vSafe_Server.database.MySQL_PoolHandling.PoolRequestMode;
import de.versatel.noc.vSafe_Server.database.MySQL_PoolHandling.PoolState;
import de.versatel.noc.vSafe_Server.mvc.controllers.MySQLConnectionListController;
import de.versatel.noc.vSafe_Server.mvc.controllers.MySQLPoolController;
import de.versatel.noc.vSafe_Server.mvc.models.MySQLPoolModel;

import de.versatel.noc.vSafe.mvc.AbstractViewPanel;
import de.versatel.noc.vSafe.system.exceptions.ExceptionCodes;
import java.beans.PropertyChangeEvent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author ulrich.stemmer
 */
public class MySQLPoolViewPanel extends AbstractViewPanel {

    private final JInternalFrame frame;
    private final MySQLPoolController mySQLPoolController;
    private final MySQLConnectionListController mySQLConnectionListController;
    private boolean logging = false;
    private int size = 0;
    private int maxsize = 0;
    private PoolRequestMode mode = PoolRequestMode.SEQUENZIELL;
    private PoolState state = PoolState.DISCONNECTED;
    //private boolean wdActive = false;

    /** Creates new form MySQLPoolViewPanel */
    public MySQLPoolViewPanel(
            JInternalFrame frame,
            MySQLPoolController mySQLPoolController,
            MySQLConnectionListController mySQLConnectionListController) {

        this.frame = frame;
        this.mySQLPoolController = mySQLPoolController;
        this.mySQLConnectionListController = mySQLConnectionListController;
        initComponents();
        initDefaultGUI();
    }

    private void addConnectionViewPanel(int index) {

        MySQLConnectionObjectViewPanel cOVP = new MySQLConnectionObjectViewPanel(frame, mySQLConnectionListController);
        cOVP.setIndex(index);
        jPanel_PoolObjects.add(cOVP);
        mySQLConnectionListController.addView(cOVP);
        //System.out.println("poolView: Connectionview added, index:" + index);
        //System.out.println("poolView: Anforderung: updateViews, index:" + index);
        mySQLConnectionListController.updateViews(jPanel_PoolObjects.getComponentCount() - 1);
        jScrollPane_PoolObjects.repaint();
    }

    private void initDefaultGUI() {
        updatePoolStateGUI();
        updateLoggingGUI();
        updateSizeGUI();
        updateRequestModeGUI();
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        //System.out.println("MySQLPoolViewPanel.modelPropertyChange() - evt.getPropertyName()=" + evt.getPropertyName() + ", evt.getNewValue()=" + evt.getNewValue());
        if (evt.getPropertyName().equals(MySQLPoolModel.POOL_STATE)) {
            PoolState newState = (PoolState) evt.getNewValue();
            if (!state.equals(newState)) {
                state = newState;
                updatePoolStateGUI();
            }
        } else if (evt.getPropertyName().equals(MySQLPoolModel.POOL_LOGGING)) {
            logging = (Boolean) evt.getNewValue();
            updateLoggingGUI();
        } else if (evt.getPropertyName().equals(MySQLPoolModel.POOL_MAXSIZE)) {
            if (maxsize != (Integer) evt.getNewValue()) {
                maxsize = (Integer) evt.getNewValue();
                updateSizeGUI();
            }
        } else if (evt.getPropertyName().equals(MySQLPoolModel.POOL_REQUESTMODE)) {
            if (!mode.equals((PoolRequestMode) evt.getNewValue())) {
                mode = (PoolRequestMode) evt.getNewValue();
                updateRequestModeGUI();
            }
        } else if (evt.getPropertyName().equals(MySQLPoolModel.POOL_SIZE)) {
            int newSize = (Integer) evt.getNewValue();
            if (newSize > 0 && newSize > size) {
                for (int i = size; i < newSize; i++) {
                    addConnectionViewPanel(i);
                }
                //System.out.println("poolView: added: " + size +"->" + newSize);
                size = newSize;
                updateSizeGUI();
            } else if (newSize > 0 && newSize < size) {
                for (int index = (size - 1); index > (newSize - 1); index--) {
                    removeConnectionViewPanel(index);
                }
                //System.out.println("poolView: removed: " + size +"->" + newSize);
                size = newSize;
                updateSizeGUI();
            }
        } else if (evt.getPropertyName().equals(MySQLPoolModel.POOL_ERROR)) {
            int errorMessage = (Integer) evt.getNewValue();
            ExceptionCodes sm = new ExceptionCodes();
            ExceptionCodes.ExceptionCode ei = sm.getExceptionCode(errorMessage);
            JOptionPane.showMessageDialog(frame, ei.getDetailedMessage() , "Fehler !", JOptionPane.ERROR_MESSAGE );
        }


    }

    private void removeConnectionViewPanel(int index) {
        MySQLConnectionObjectViewPanel cOVP = (MySQLConnectionObjectViewPanel) jPanel_PoolObjects.getComponent(index);
        jPanel_PoolObjects.remove(index);
        mySQLConnectionListController.removeView(cOVP);
    }

    private void updateLoggingGUI() {
        if (logging) {
            this.jLabel_Logging.setText("Logging: AN");
        } else {
            this.jLabel_Logging.setText("Logging: AUS");
        }
    }

        private void updatePoolStateGUI() {
        if (state.equals(PoolState.CONNECTED)) {
            jButton_Connect.setEnabled(true);
            jButton_Connect.setText("Trennen");
        } else if (state.equals(PoolState.CONNECTING)) {
            jButton_Connect.setEnabled(false);
            jButton_Connect.setText("Verbinde...");
        } else if (state.equals(PoolState.DISCONNECTED)) {
            jButton_Connect.setEnabled(true);
            jButton_Connect.setText("Verbinden");
        } else if (state.equals(PoolState.DISCONNECTING)) {
            jButton_Connect.setEnabled(false);
            jButton_Connect.setText("Trenne...");
        }
    }

    private void updateRequestModeGUI() {
        jLabel_RequestMode.setText("Abfragemodus: " + mode.toString().toUpperCase());
    }
    private void updateSizeGUI() {
        jLabel_Poolsize.setText("Eingestellte Poolgröße: " + size + "/" + maxsize);
        jPanel_PoolObjects.repaint();
    }



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel_ConnectionViews = new javax.swing.JPanel();
        jScrollPane_PoolObjects = new javax.swing.JScrollPane();
        jPanel_PoolObjects = new javax.swing.JPanel();
        jPanel_StatusBar = new javax.swing.JPanel();
        jLabel_Logging = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel_Poolsize = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel_RequestMode = new javax.swing.JLabel();
        jPanel_Filler1 = new javax.swing.JPanel();
        jButton_Connect = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(1100, 250));
        setPreferredSize(new java.awt.Dimension(1100, 250));
        setRequestFocusEnabled(false);
        setLayout(new java.awt.GridBagLayout());

        jPanel_ConnectionViews.setLayout(new javax.swing.BoxLayout(jPanel_ConnectionViews, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane_PoolObjects.setMinimumSize(new java.awt.Dimension(1080, 200));
        jScrollPane_PoolObjects.setPreferredSize(new java.awt.Dimension(1080, 200));
        jScrollPane_PoolObjects.setRequestFocusEnabled(false);

        jPanel_PoolObjects.setLayout(new javax.swing.BoxLayout(jPanel_PoolObjects, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane_PoolObjects.setViewportView(jPanel_PoolObjects);

        jPanel_ConnectionViews.add(jScrollPane_PoolObjects);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel_ConnectionViews, gridBagConstraints);

        jPanel_StatusBar.setMinimumSize(new java.awt.Dimension(1090, 23));
        jPanel_StatusBar.setPreferredSize(new java.awt.Dimension(1100, 23));
        jPanel_StatusBar.setRequestFocusEnabled(false);
        jPanel_StatusBar.setLayout(new java.awt.GridBagLayout());

        jLabel_Logging.setText("Logging:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 5);
        jPanel_StatusBar.add(jLabel_Logging, gridBagConstraints);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        jPanel_StatusBar.add(jSeparator1, gridBagConstraints);

        jLabel_Poolsize.setText("Eingestellte Poolgröße:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel_StatusBar.add(jLabel_Poolsize, gridBagConstraints);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        jPanel_StatusBar.add(jSeparator2, gridBagConstraints);

        jLabel_RequestMode.setText("Belegungsart:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel_StatusBar.add(jLabel_RequestMode, gridBagConstraints);

        jPanel_Filler1.setPreferredSize(new java.awt.Dimension(752, 30));

        javax.swing.GroupLayout jPanel_Filler1Layout = new javax.swing.GroupLayout(jPanel_Filler1);
        jPanel_Filler1.setLayout(jPanel_Filler1Layout);
        jPanel_Filler1Layout.setHorizontalGroup(
            jPanel_Filler1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 727, Short.MAX_VALUE)
        );
        jPanel_Filler1Layout.setVerticalGroup(
            jPanel_Filler1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 23, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel_StatusBar.add(jPanel_Filler1, gridBagConstraints);

        jButton_Connect.setText("Verbinden");
        jButton_Connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ConnectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 10);
        jPanel_StatusBar.add(jButton_Connect, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        add(jPanel_StatusBar, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_ConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ConnectActionPerformed
        if (state.equals(PoolState.CONNECTED)) {
            mySQLPoolController.disconnect();
        } else if (state.equals(PoolState.DISCONNECTED)) {
            mySQLPoolController.connect();
        }
    }//GEN-LAST:event_jButton_ConnectActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Connect;
    private javax.swing.JLabel jLabel_Logging;
    private javax.swing.JLabel jLabel_Poolsize;
    private javax.swing.JLabel jLabel_RequestMode;
    private javax.swing.JPanel jPanel_ConnectionViews;
    private javax.swing.JPanel jPanel_Filler1;
    private javax.swing.JPanel jPanel_PoolObjects;
    private javax.swing.JPanel jPanel_StatusBar;
    private javax.swing.JScrollPane jScrollPane_PoolObjects;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
