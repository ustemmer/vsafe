/*
 * MySQLConnectionObjectViewPanel.java
 *
 * Created on 07.11.2011, 18:12:07
 */
package de.versatel.noc.vsafe.server.mvc.views;

import de.versatel.noc.vSafe_Server.mvc.controllers.MySQLConnectionListController;
import de.versatel.noc.vSafe_Server.mvc.models.MySQLConnectionObjectModel;

import de.versatel.noc.vSafe.mvc.AbstractViewPanel;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.IndexedPropertyChangeEvent;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author ulrich.stemmer
 */
public class MySQLConnectionObjectViewPanel extends AbstractViewPanel {

    //private final JInternalFrame frame;
    private final MySQLConnectionListController mySQLConnectionListController;
    private final Color COLOR_GREEN = new Color(180, 231, 185);
    private final Color COLOR_RED = new Color(254, 134, 134);
    private boolean connected = false;
    private boolean inAdminState = false;
    private int index = 0;
    private long connectedTimestamp = 0L;
    private long disconnectedTimestamp = 0L;
    private long lastUseTimestamp = 0L;
    private long preparedTimestamp = 0L;
    private int counter = 0;
    private boolean isIdle = false;

    /** Creates new form MySQLConnectionObjectViewPanel */
    public MySQLConnectionObjectViewPanel(
            JInternalFrame frame,
            MySQLConnectionListController mySQLConnectionListController) {
        //this.frame = frame;
        this.mySQLConnectionListController = mySQLConnectionListController;
        Toolkit.getDefaultToolkit().addAWTEventListener(new TargetedMouseHandler(this, frame), AWTEvent.MOUSE_EVENT_MASK);
        initComponents();
        initDefaultGUI();

    }

    private void initDefaultGUI() {
        updateAdminStateGUI();
        updateConnectedGUI();
        updateCounterGUI();
        updateIdleStateGUI();
        updateIndexGUI();
        updateTimestampConnectedGUI();
        updateTimestampDisconnectedGUI();
        updateTimestampLastUseGUI();
        updateTimestampPreparedGUI();
    }

    private void updateAdminStateGUI() {
        if (inAdminState) {
            jButtonBlocked.setText("Freigeben");
            jButtonBlocked.setToolTipText("Verbindung '" + (index + 1) + "' freigeben.");
            jLabelBlockSign.setBackground(COLOR_RED);
            jLabelBlockSign.setToolTipText("Verbindung '" + (index + 1) + "', gesperrt.");
        } else {
            jButtonBlocked.setText("Sperren");
            jButtonBlocked.setToolTipText("Verbindung '" + (index + 1) + "' sperren.");
            jLabelBlockSign.setBackground(COLOR_GREEN);
            jLabelBlockSign.setToolTipText("Verbindung '" + (index + 1) + "', freigegeben.");
        }
    }

    private void updateConnectedGUI() {
        if (connected) {
            jButtonConnect.setText("Trennen");
            jButtonConnect.setToolTipText("Verbindung '" + (index + 1) + "' trennen.");
            jLabelConnectSign.setBackground(COLOR_GREEN);
            jLabelConnectSign.setToolTipText("Verbindung '" + (index + 1) + "' verbunden.");
        } else {
            jButtonConnect.setText("Verbinden");
            jButtonConnect.setToolTipText("Verbindung '" + (index + 1) + "' verbinden.");
            jLabelConnectSign.setBackground(COLOR_RED);
            jLabelConnectSign.setToolTipText("Verbindung '" + (index + 1) + "' getrennt.");
        }
    }

    private void updateCounterGUI() {
        jLabelCounter.setText(Integer.toString(counter));
        jLabelCounter.setToolTipText("Verbindung '" + (index + 1) + "': " + counter + " Zugriffe.");
    }

    private void updateIdleStateGUI() {
        if (isIdle) {
            jLabelState.setText("frei");
            jLabelState.setToolTipText("Verbindung '" + (index + 1) + "', Status: frei.");
        } else {
            jLabelState.setText("belegt");
            jLabelState.setToolTipText("Verbindung '" + (index + 1) + "', Status: belegt.");
        }
    }

    private void updateIndexGUI() {
        jLabelIndex.setText(Integer.toString(index + 1));
        jLabelIndex.setToolTipText("Verbindung '" + (index + 1) + ".");
    }

    private void updateTimestampConnectedGUI() {
        if (connectedTimestamp == 0L) {
            jLabelTimestamp_ConnectedAt.setText("");
            jLabelTimestamp_ConnectedAt.setToolTipText("Verbindung '" + (index + 1) + "', verbunden seit:");
        } else {
            jLabelTimestamp_ConnectedAt.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date(connectedTimestamp)));
            jLabelTimestamp_ConnectedAt.setToolTipText("Verbindung '" + (index + 1) + "', verbunden seit: '" + jLabelTimestamp_ConnectedAt.getText() + "'.");
        }
    }

    private void updateTimestampDisconnectedGUI() {
        if (disconnectedTimestamp == 0L) {
            jLabelTimestamp_DisconnectedAt.setText("");
            jLabelTimestamp_DisconnectedAt.setToolTipText("Verbindung '" + (index + 1) + "', getrennt seit:");
        } else {
            jLabelTimestamp_DisconnectedAt.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date(disconnectedTimestamp)));
            jLabelTimestamp_DisconnectedAt.setToolTipText("Verbindung '" + (index + 1) + "', getrennt seit: '" + jLabelTimestamp_DisconnectedAt.getText() + "'.");
        }
    }

    private void updateTimestampLastUseGUI() {
        if (lastUseTimestamp == 0L) {
            jLabelTimestamp_LastUse.setText("");
            jLabelTimestamp_LastUse.setToolTipText("Verbindung '" + (index + 1) + "', letzter Zugriff:");
        } else {
            jLabelTimestamp_LastUse.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date(lastUseTimestamp)));
            jLabelTimestamp_LastUse.setToolTipText("Verbindung '" + (index + 1) + "', letzter Zugriff: '" + jLabelTimestamp_LastUse.getText() + "'.");
        }
    }

    private void updateTimestampPreparedGUI() {
        if (preparedTimestamp == 0L) {
            jLabelTimestamp_PreparedAt.setText("");
            jLabelTimestamp_PreparedAt.setToolTipText("Verbindung '" + (index + 1) + "', erzeugt am:");
        } else {
            jLabelTimestamp_PreparedAt.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date(preparedTimestamp)));
            jLabelTimestamp_PreparedAt.setToolTipText("Verbindung '" + (index + 1) + "', erzeugt am: '" + jLabelTimestamp_PreparedAt.getText() + "'.");
        }
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent pce) {
        if (pce instanceof IndexedPropertyChangeEvent) {
            IndexedPropertyChangeEvent ipce = (IndexedPropertyChangeEvent) pce;
            if (ipce.getIndex() == index) {
                //System.out.println("connView(" + index + "): property=" + pce.getPropertyName() + ", value=" + pce.getNewValue());
                if (pce.getPropertyName().equals(MySQLConnectionObjectModel.CONNECTION_ADMINSTATE)) {
                    if (inAdminState != (Boolean) pce.getNewValue()) {
                        inAdminState = (Boolean) pce.getNewValue();
                        updateAdminStateGUI();
                    }
                } else if (pce.getPropertyName().equals(MySQLConnectionObjectModel.CONNECTION_CONNECTED)) {
                    boolean newValue = (Boolean) pce.getNewValue();
                    //System.out.println("connected: old=" + connected + ", new=" + newValue);
                    if (newValue != connected) {
                        connected = newValue;
                        updateConnectedGUI();
                    }
                } else if (pce.getPropertyName().equals(MySQLConnectionObjectModel.CONNECTION_COUNTER)) {
                    if (counter != (Integer) pce.getNewValue()) {
                        counter = (Integer) pce.getNewValue();
                        updateCounterGUI();
                    }
                } else if (pce.getPropertyName().equals(MySQLConnectionObjectModel.CONNECTION_IDLESTATE)) {
                    if (isIdle != (Boolean) pce.getNewValue()) {
                        isIdle = (Boolean) pce.getNewValue();
                        updateIdleStateGUI();
                    }
                } else if (pce.getPropertyName().equals(MySQLConnectionObjectModel.CONNECTION_INDEX)) {
                    if (index != (Integer) pce.getNewValue()) {
                        index = (Integer) pce.getNewValue();
                        updateIndexGUI();
                    }
                } else if (pce.getPropertyName().equals(MySQLConnectionObjectModel.CONNECTION_TIMESTAMP_CONNECTED)) {
                    Long ts = (Long) pce.getNewValue();
                    if (connectedTimestamp != ts) {
                        connectedTimestamp = (Long) pce.getNewValue();
                        updateTimestampConnectedGUI();
                    }
                } else if (pce.getPropertyName().equals(MySQLConnectionObjectModel.CONNECTION_TIMESTAMP_DISCONNECTED)) {
                    Long ts = (Long) pce.getNewValue();
                    if (disconnectedTimestamp != ts) {
                        disconnectedTimestamp = (Long) pce.getNewValue();
                        updateTimestampDisconnectedGUI();
                    }
                } else if (pce.getPropertyName().equals(MySQLConnectionObjectModel.CONNECTION_TIMESTAMP_LASTUSE)) {
                    Long ts = (Long) pce.getNewValue();
                    if (lastUseTimestamp != ts) {
                        lastUseTimestamp = (Long) pce.getNewValue();
                        updateTimestampLastUseGUI();
                    }
                } else if (pce.getPropertyName().equals(MySQLConnectionObjectModel.CONNECTION_TIMESTAMP_PREPARED)) {
                    Long ts = (Long) pce.getNewValue();
                    if (preparedTimestamp != ts) {
                        preparedTimestamp = (Long) pce.getNewValue();
                        updateTimestampPreparedGUI();
                    }
                } else if (pce.getPropertyName().equals(MySQLConnectionObjectModel.CONNECTION_ERROR)) {
                    if (pce.getNewValue() != null) {
                        Exception e = (Exception) pce.getNewValue();
                        JOptionPane.showMessageDialog(new JFrame().getContentPane(), e.getMessage(), "EXCEPTION", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    public void setIndex(int index) {
        this.index = index;
        jLabelIndex.setText(Integer.toString(index + 1));
        jLabelIndex.setToolTipText("Verbindung '" + (index + 1) + ".");
    }

    public class TargetedMouseHandler implements AWTEventListener {

        private Component parent;
        private Component innerBound;
        private boolean hasExited = true;

        public TargetedMouseHandler(Component p , Component p2 ) {
            parent = p;
            innerBound = p2;
        }

        @Override
        public void eventDispatched(AWTEvent e) {
            if (e instanceof MouseEvent) {
                MouseEvent m = (MouseEvent) e;
                if (SwingUtilities.isDescendingFrom((Component) e.getSource(), parent)) {
                    if (m.getID() == MouseEvent.MOUSE_ENTERED) {
                        if (hasExited) {
                            setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 205, 114), 1));
                            hasExited = false;
                        }
                    } else if (m.getID() == MouseEvent.MOUSE_EXITED) {
                        Point p = SwingUtilities.convertPoint(
                                (Component) e.getSource(),
                                m.getPoint(),
                                innerBound);
                        if (p!= null && !innerBound.getBounds().contains(p)) {
                            setBorder(null);
                            hasExited = true;
                        }
                    }
                }else{

                }
            }
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
        java.awt.GridBagConstraints gridBagConstraints;

        jLabelConnectSign = new javax.swing.JLabel();
        jLabelBlockSign = new javax.swing.JLabel();
        jLabelIndex = new javax.swing.JLabel();
        jLabelTimestamp_PreparedAt = new javax.swing.JLabel();
        jLabelTimestamp_ConnectedAt = new javax.swing.JLabel();
        jLabelTimestamp_LastUse = new javax.swing.JLabel();
        jLabelTimestamp_DisconnectedAt = new javax.swing.JLabel();
        jLabelCounter = new javax.swing.JLabel();
        jLabelState = new javax.swing.JLabel();
        jPanelFiller = new javax.swing.JPanel();
        jButtonBlocked = new javax.swing.JButton();
        jButtonConnect = new javax.swing.JButton();
        jButtonResetCounter = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(2147483647, 24));
        setMinimumSize(new java.awt.Dimension(1080, 24));
        setPreferredSize(new java.awt.Dimension(1080, 24));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });
        setLayout(new java.awt.GridBagLayout());

        jLabelConnectSign.setBackground(new java.awt.Color(180, 231, 185));
        jLabelConnectSign.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelConnectSign.setMaximumSize(new java.awt.Dimension(10, 18));
        jLabelConnectSign.setMinimumSize(new java.awt.Dimension(10, 18));
        jLabelConnectSign.setOpaque(true);
        jLabelConnectSign.setPreferredSize(new java.awt.Dimension(10, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 2, 2);
        add(jLabelConnectSign, gridBagConstraints);

        jLabelBlockSign.setBackground(new java.awt.Color(180, 231, 185));
        jLabelBlockSign.setMaximumSize(new java.awt.Dimension(10, 18));
        jLabelBlockSign.setMinimumSize(new java.awt.Dimension(10, 18));
        jLabelBlockSign.setOpaque(true);
        jLabelBlockSign.setPreferredSize(new java.awt.Dimension(10, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(jLabelBlockSign, gridBagConstraints);

        jLabelIndex.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelIndex.setToolTipText("bliblap");
        jLabelIndex.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabelIndex.setMaximumSize(new java.awt.Dimension(40, 18));
        jLabelIndex.setMinimumSize(new java.awt.Dimension(40, 18));
        jLabelIndex.setOpaque(true);
        jLabelIndex.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 1);
        add(jLabelIndex, gridBagConstraints);

        jLabelTimestamp_PreparedAt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTimestamp_PreparedAt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabelTimestamp_PreparedAt.setMaximumSize(new java.awt.Dimension(140, 18));
        jLabelTimestamp_PreparedAt.setMinimumSize(new java.awt.Dimension(140, 18));
        jLabelTimestamp_PreparedAt.setPreferredSize(new java.awt.Dimension(140, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 1);
        add(jLabelTimestamp_PreparedAt, gridBagConstraints);

        jLabelTimestamp_ConnectedAt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTimestamp_ConnectedAt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabelTimestamp_ConnectedAt.setMaximumSize(new java.awt.Dimension(140, 18));
        jLabelTimestamp_ConnectedAt.setMinimumSize(new java.awt.Dimension(140, 18));
        jLabelTimestamp_ConnectedAt.setPreferredSize(new java.awt.Dimension(140, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 1);
        add(jLabelTimestamp_ConnectedAt, gridBagConstraints);

        jLabelTimestamp_LastUse.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTimestamp_LastUse.setToolTipText("");
        jLabelTimestamp_LastUse.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabelTimestamp_LastUse.setMaximumSize(new java.awt.Dimension(140, 18));
        jLabelTimestamp_LastUse.setMinimumSize(new java.awt.Dimension(140, 18));
        jLabelTimestamp_LastUse.setPreferredSize(new java.awt.Dimension(140, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 1);
        add(jLabelTimestamp_LastUse, gridBagConstraints);

        jLabelTimestamp_DisconnectedAt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTimestamp_DisconnectedAt.setToolTipText("");
        jLabelTimestamp_DisconnectedAt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabelTimestamp_DisconnectedAt.setMaximumSize(new java.awt.Dimension(140, 18));
        jLabelTimestamp_DisconnectedAt.setMinimumSize(new java.awt.Dimension(140, 18));
        jLabelTimestamp_DisconnectedAt.setPreferredSize(new java.awt.Dimension(140, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 1);
        add(jLabelTimestamp_DisconnectedAt, gridBagConstraints);

        jLabelCounter.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelCounter.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabelCounter.setMaximumSize(new java.awt.Dimension(80, 18));
        jLabelCounter.setMinimumSize(new java.awt.Dimension(80, 18));
        jLabelCounter.setPreferredSize(new java.awt.Dimension(80, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 1);
        add(jLabelCounter, gridBagConstraints);

        jLabelState.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelState.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabelState.setMaximumSize(new java.awt.Dimension(60, 18));
        jLabelState.setMinimumSize(new java.awt.Dimension(60, 18));
        jLabelState.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 1);
        add(jLabelState, gridBagConstraints);

        javax.swing.GroupLayout jPanelFillerLayout = new javax.swing.GroupLayout(jPanelFiller);
        jPanelFiller.setLayout(jPanelFillerLayout);
        jPanelFillerLayout.setHorizontalGroup(
            jPanelFillerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 9, Short.MAX_VALUE)
        );
        jPanelFillerLayout.setVerticalGroup(
            jPanelFillerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(jPanelFiller, gridBagConstraints);

        jButtonBlocked.setText("Sperren");
        jButtonBlocked.setMargin(new java.awt.Insets(2, 4, 2, 4));
        jButtonBlocked.setMaximumSize(new java.awt.Dimension(70, 20));
        jButtonBlocked.setMinimumSize(new java.awt.Dimension(70, 20));
        jButtonBlocked.setPreferredSize(new java.awt.Dimension(70, 20));
        jButtonBlocked.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBlockedActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(jButtonBlocked, gridBagConstraints);

        jButtonConnect.setText("Verbinden");
        jButtonConnect.setMaximumSize(new java.awt.Dimension(90, 20));
        jButtonConnect.setMinimumSize(new java.awt.Dimension(90, 20));
        jButtonConnect.setPreferredSize(new java.awt.Dimension(90, 20));
        jButtonConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConnectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(jButtonConnect, gridBagConstraints);

        jButtonResetCounter.setText("Reset Counter");
        jButtonResetCounter.setMaximumSize(new java.awt.Dimension(110, 20));
        jButtonResetCounter.setMinimumSize(new java.awt.Dimension(110, 20));
        jButtonResetCounter.setPreferredSize(new java.awt.Dimension(110, 20));
        jButtonResetCounter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetCounterActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        add(jButtonResetCounter, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        //setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 205, 114), 2));
    }//GEN-LAST:event_formMouseEntered

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        //setBorder(null);
    }//GEN-LAST:event_formMouseExited

    private void jButtonBlockedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBlockedActionPerformed
        if (inAdminState) {
            mySQLConnectionListController.setAdminBlocked(index, false);
        } else {
            mySQLConnectionListController.setAdminBlocked(index, true);
        }
    }//GEN-LAST:event_jButtonBlockedActionPerformed

    private void jButtonResetCounterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetCounterActionPerformed
        mySQLConnectionListController.resetCounter(index);
    }//GEN-LAST:event_jButtonResetCounterActionPerformed

    private void jButtonConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConnectActionPerformed
        if (connected) {
            //System.out.println("Anforderung: disconnect");
            mySQLConnectionListController.disconnect(index);
        } else {
            //System.out.println("Anforderung: connect");
            mySQLConnectionListController.connect(index);
        }

    }//GEN-LAST:event_jButtonConnectActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBlocked;
    private javax.swing.JButton jButtonConnect;
    private javax.swing.JButton jButtonResetCounter;
    private javax.swing.JLabel jLabelBlockSign;
    private javax.swing.JLabel jLabelConnectSign;
    private javax.swing.JLabel jLabelCounter;
    private javax.swing.JLabel jLabelIndex;
    private javax.swing.JLabel jLabelState;
    private javax.swing.JLabel jLabelTimestamp_ConnectedAt;
    private javax.swing.JLabel jLabelTimestamp_DisconnectedAt;
    private javax.swing.JLabel jLabelTimestamp_LastUse;
    private javax.swing.JLabel jLabelTimestamp_PreparedAt;
    private javax.swing.JPanel jPanelFiller;
    // End of variables declaration//GEN-END:variables
}
