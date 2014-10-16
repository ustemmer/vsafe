/*
 * MySQLConnectionObjectViewPanel.java
 *
 * Created on 07.11.2011, 18:12:07
 */
package de.versatel.noc.vsafe.server.mvc.views;

import de.versatel.noc.vSafe.mvc.AbstractViewPanel;


import de.versatel.noc.vSafe_Server.mvc.controllers.MySQLConnectionListController;
import de.versatel.noc.vSafe_Server.mvc.models.MySQLConnectionObjectModel;



import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.IndexedPropertyChangeEvent;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author ulrich.stemmer
 */
public class MySQLConnectionObjectViewPanel2 extends AbstractViewPanel {

    private final JInternalFrame frame;
    //private final Container parent;
    private final MySQLConnectionListController mySQLConnectionObjectController;
    private boolean inAdminState;
    private int counter;
    private boolean connected;
    private int index;
    private boolean isIdle;
    private long connectedTimestamp;
    private long disconnectedTimestamp;
    private long lastUseTimestamp;
    private long preparedTimestamp;

    /** Creates new form MySQLConnectionObjectViewPanel */
    public MySQLConnectionObjectViewPanel2(
            JInternalFrame frame,
            MySQLConnectionListController mySQLConnectionObjectController) {
        this.frame = frame;
        this.mySQLConnectionObjectController = mySQLConnectionObjectController;
        initComponents();
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        IndexedPropertyChangeEvent ievt = (IndexedPropertyChangeEvent) evt;
        if (ievt.getIndex() == index) {
            if (evt.getPropertyName().equals(MySQLConnectionObjectModel.CONNECTION_ADMINSTATE)) {
                if (inAdminState != (Boolean) evt.getNewValue()) {
                    inAdminState = (Boolean) evt.getNewValue();
                    if (inAdminState) {
                        jLabelBlockSign.setBackground(new java.awt.Color(254, 134, 134));
                        jLabelBlockSign.setToolTipText("Verbindung '" + index + "', gesperrt.");
                    } else {
                        jLabelBlockSign.setBackground(new java.awt.Color(180, 231, 185));
                        jLabelBlockSign.setToolTipText("Verbindung '" + index + "', freigegeben.");
                    }
                }
            } else if (evt.getPropertyName().equals(MySQLConnectionObjectModel.CONNECTION_CONNECTED)) {
                if (connected != (Boolean) evt.getNewValue()) {
                    connected = (Boolean) evt.getNewValue();
                    if (connected) {
                        jLabelConnectSign.setBackground(new java.awt.Color(254, 134, 134));
                        jLabelConnectSign.setToolTipText("Verbindung '" + index + "', Status: verbunden.");
                    } else {
                        jLabelConnectSign.setBackground(new java.awt.Color(180, 231, 185));
                        jLabelConnectSign.setToolTipText("Verbindung '" + index + "', Status: getrennt.");
                    }
                }
            } else if (evt.getPropertyName().equals(MySQLConnectionObjectModel.CONNECTION_COUNTER)) {
                if (counter != (Integer) evt.getNewValue()) {
                    counter = (Integer) evt.getNewValue();
                    jLabelCounter.setText(Integer.toString(counter));
                    jLabelState.setToolTipText("Verbindung '" + index + "': " + counter + " Zugriffe.");
                }
            } else if (evt.getPropertyName().equals(MySQLConnectionObjectModel.CONNECTION_IDLESTATE)) {
                if (isIdle != (Boolean) evt.getNewValue()) {
                    isIdle = (Boolean) evt.getNewValue();
                    if (isIdle) {
                        jLabelState.setText("frei");
                        jLabelState.setToolTipText("Verbindung '" + index + "', Status: frei.");
                    } else {
                        jLabelState.setText("belegt");
                        jLabelState.setToolTipText("Verbindung '" + index + "', Status: belegt.");
                    }
                }
            } else if (evt.getPropertyName().equals(MySQLConnectionObjectModel.CONNECTION_INDEX)) {
                if (index != (Integer) evt.getNewValue()) {
                    index = (Integer) evt.getNewValue();
                    jLabelIndex.setText(Integer.toString(index));
                    jLabelIndex.setToolTipText("Verbindung '" + index + ".");
                }
            } else if (evt.getPropertyName().equals(MySQLConnectionObjectModel.CONNECTION_RESETCOUNTER)) {
            } else if (evt.getPropertyName().equals(MySQLConnectionObjectModel.CONNECTION_SETADMINSTATE)) {
            } else if (evt.getPropertyName().equals(MySQLConnectionObjectModel.CONNECTION_TIMESTAMP_CONNECTED)) {
                connectedTimestamp = (Long) evt.getNewValue();
                jLabelTimestamp_ConnectedAt.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date(connectedTimestamp)));
                jLabelTimestamp_ConnectedAt.setToolTipText("Verbindung '" + index + "', verbunden seit: '" + jLabelTimestamp_ConnectedAt.getText() + "'.");
            } else if (evt.getPropertyName().equals(MySQLConnectionObjectModel.CONNECTION_TIMESTAMP_DISCONNECTED)) {
                disconnectedTimestamp = (Long) evt.getNewValue();
                jLabelTimestamp_DisconnectedAt.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date(disconnectedTimestamp)));
                jLabelTimestamp_DisconnectedAt.setToolTipText("Verbindung '" + index + "', getrennt seit: '" + jLabelTimestamp_DisconnectedAt.getText() + "'.");
            } else if (evt.getPropertyName().equals(MySQLConnectionObjectModel.CONNECTION_TIMESTAMP_LASTUSE)) {
                lastUseTimestamp = (Long) evt.getNewValue();
                jLabelTimestamp_LastUse.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date(lastUseTimestamp)));
                jLabelTimestamp_LastUse.setToolTipText("Verbindung '" + index + "', letzter Zugriff: '" + jLabelTimestamp_LastUse.getText() + "'.");
            } else if (evt.getPropertyName().equals(MySQLConnectionObjectModel.CONNECTION_TIMESTAMP_PREPARED)) {
                preparedTimestamp = (Long) evt.getNewValue();
                jLabelTimestamp_PreparedAt.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date(preparedTimestamp)));
                jLabelTimestamp_PreparedAt.setToolTipText("Verbindung '" + index + "', erzeugt am: '" + jLabelTimestamp_PreparedAt.getText() + "'.");
            }


            jLabelConnectSign.setBackground(new java.awt.Color(254, 134, 134));
        }
    }

    public void setIndex(int index) {
        this.index = index;
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

        jPanel_Left = new javax.swing.JPanel();
        jLabelConnectSign = new javax.swing.JLabel();
        jLabelBlockSign = new javax.swing.JLabel();
        jPanelFiller1 = new javax.swing.JPanel();
        jPanel_Top = new javax.swing.JPanel();
        jLabelIndex = new javax.swing.JLabel();
        jLabelCounter = new javax.swing.JLabel();
        jLabelState = new javax.swing.JLabel();
        jPanelFiller = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButtonBlocked = new javax.swing.JButton();
        jPanelBottom = new javax.swing.JPanel();
        jLabelTimestamp_PreparedAt = new javax.swing.JLabel();
        jLabelTimestamp_ConnectedAt = new javax.swing.JLabel();
        jLabelTimestamp_LastUse = new javax.swing.JLabel();
        jLabelTimestamp_DisconnectedAt = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanelFiller2 = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(2147483647, 24));
        setMinimumSize(new java.awt.Dimension(550, 24));
        setPreferredSize(new java.awt.Dimension(550, 24));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });
        setLayout(new java.awt.GridBagLayout());

        jPanel_Left.setLayout(new java.awt.GridBagLayout());

        jLabelConnectSign.setBackground(new java.awt.Color(180, 231, 185));
        jLabelConnectSign.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelConnectSign.setMaximumSize(new java.awt.Dimension(100, 100));
        jLabelConnectSign.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel_Left.add(jLabelConnectSign, gridBagConstraints);

        jLabelBlockSign.setBackground(new java.awt.Color(180, 231, 185));
        jLabelBlockSign.setMaximumSize(new java.awt.Dimension(100, 100));
        jLabelBlockSign.setMinimumSize(new java.awt.Dimension(16, 16));
        jLabelBlockSign.setOpaque(true);
        jLabelBlockSign.setPreferredSize(new java.awt.Dimension(16, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel_Left.add(jLabelBlockSign, gridBagConstraints);

        javax.swing.GroupLayout jPanelFiller1Layout = new javax.swing.GroupLayout(jPanelFiller1);
        jPanelFiller1.setLayout(jPanelFiller1Layout);
        jPanelFiller1Layout.setHorizontalGroup(
            jPanelFiller1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelFiller1Layout.setVerticalGroup(
            jPanelFiller1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel_Left.add(jPanelFiller1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        add(jPanel_Left, gridBagConstraints);

        jPanel_Top.setLayout(new java.awt.GridBagLayout());

        jLabelIndex.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelIndex.setText("9999");
        jLabelIndex.setToolTipText("bliblap");
        jLabelIndex.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabelIndex.setMaximumSize(new java.awt.Dimension(40, 18));
        jLabelIndex.setMinimumSize(new java.awt.Dimension(40, 18));
        jLabelIndex.setOpaque(true);
        jLabelIndex.setPreferredSize(new java.awt.Dimension(40, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 1);
        jPanel_Top.add(jLabelIndex, gridBagConstraints);

        jLabelCounter.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelCounter.setText("9999999");
        jLabelCounter.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabelCounter.setMaximumSize(new java.awt.Dimension(80, 18));
        jLabelCounter.setMinimumSize(new java.awt.Dimension(80, 18));
        jLabelCounter.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel_Top.add(jLabelCounter, gridBagConstraints);

        jLabelState.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelState.setText("frei");
        jLabelState.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabelState.setMaximumSize(new java.awt.Dimension(60, 18));
        jLabelState.setMinimumSize(new java.awt.Dimension(60, 18));
        jLabelState.setPreferredSize(new java.awt.Dimension(60, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel_Top.add(jLabelState, gridBagConstraints);

        javax.swing.GroupLayout jPanelFillerLayout = new javax.swing.GroupLayout(jPanelFiller);
        jPanelFiller.setLayout(jPanelFillerLayout);
        jPanelFillerLayout.setHorizontalGroup(
            jPanelFillerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 62, Short.MAX_VALUE)
        );
        jPanelFillerLayout.setVerticalGroup(
            jPanelFillerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel_Top.add(jPanelFiller, gridBagConstraints);

        jButton1.setText("reset counter");
        jButton1.setMaximumSize(new java.awt.Dimension(100, 20));
        jButton1.setMinimumSize(new java.awt.Dimension(100, 20));
        jButton1.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 1);
        jPanel_Top.add(jButton1, gridBagConstraints);

        jButtonBlocked.setText("sperren");
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
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 1);
        jPanel_Top.add(jButtonBlocked, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(jPanel_Top, gridBagConstraints);

        jPanelBottom.setLayout(new java.awt.GridBagLayout());

        jLabelTimestamp_PreparedAt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTimestamp_PreparedAt.setText("28.01.2008 23:59:00.000");
        jLabelTimestamp_PreparedAt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabelTimestamp_PreparedAt.setMaximumSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 1);
        jPanelBottom.add(jLabelTimestamp_PreparedAt, gridBagConstraints);

        jLabelTimestamp_ConnectedAt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTimestamp_ConnectedAt.setText("28.01.2008 23:59:00.000");
        jLabelTimestamp_ConnectedAt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabelTimestamp_ConnectedAt.setMaximumSize(new java.awt.Dimension(140, 18));
        jLabelTimestamp_ConnectedAt.setMinimumSize(new java.awt.Dimension(140, 18));
        jLabelTimestamp_ConnectedAt.setPreferredSize(new java.awt.Dimension(140, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 1);
        jPanelBottom.add(jLabelTimestamp_ConnectedAt, gridBagConstraints);

        jLabelTimestamp_LastUse.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTimestamp_LastUse.setText("28.01.2008 23:59:00.000");
        jLabelTimestamp_LastUse.setToolTipText("28.01.2008 23:59:00.000");
        jLabelTimestamp_LastUse.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabelTimestamp_LastUse.setMaximumSize(new java.awt.Dimension(140, 18));
        jLabelTimestamp_LastUse.setMinimumSize(new java.awt.Dimension(140, 18));
        jLabelTimestamp_LastUse.setPreferredSize(new java.awt.Dimension(140, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 1);
        jPanelBottom.add(jLabelTimestamp_LastUse, gridBagConstraints);

        jLabelTimestamp_DisconnectedAt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTimestamp_DisconnectedAt.setText("28.01.2008 23:59:00.000");
        jLabelTimestamp_DisconnectedAt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabelTimestamp_DisconnectedAt.setMaximumSize(new java.awt.Dimension(140, 18));
        jLabelTimestamp_DisconnectedAt.setMinimumSize(new java.awt.Dimension(140, 18));
        jLabelTimestamp_DisconnectedAt.setPreferredSize(new java.awt.Dimension(140, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 1);
        jPanelBottom.add(jLabelTimestamp_DisconnectedAt, gridBagConstraints);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanelBottom.add(jPanel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(jPanelBottom, gridBagConstraints);

        javax.swing.GroupLayout jPanelFiller2Layout = new javax.swing.GroupLayout(jPanelFiller2);
        jPanelFiller2.setLayout(jPanelFiller2Layout);
        jPanelFiller2Layout.setHorizontalGroup(
            jPanelFiller2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        jPanelFiller2Layout.setVerticalGroup(
            jPanelFiller2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(jPanelFiller2, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 205, 114), 2));
    }//GEN-LAST:event_formMouseEntered

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
    }//GEN-LAST:event_formMouseExited

    private void jButtonBlockedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBlockedActionPerformed
        if (inAdminState) {
            //mySQLConnectionObjectController.setAdminBlocked(false);
        } else {
            //mySQLConnectionObjectController.setAdminBlocked(true);
        }
    }//GEN-LAST:event_jButtonBlockedActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonBlocked;
    private javax.swing.JLabel jLabelBlockSign;
    private javax.swing.JLabel jLabelConnectSign;
    private javax.swing.JLabel jLabelCounter;
    private javax.swing.JLabel jLabelIndex;
    private javax.swing.JLabel jLabelState;
    private javax.swing.JLabel jLabelTimestamp_ConnectedAt;
    private javax.swing.JLabel jLabelTimestamp_DisconnectedAt;
    private javax.swing.JLabel jLabelTimestamp_LastUse;
    private javax.swing.JLabel jLabelTimestamp_PreparedAt;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelFiller;
    private javax.swing.JPanel jPanelFiller1;
    private javax.swing.JPanel jPanelFiller2;
    private javax.swing.JPanel jPanel_Left;
    private javax.swing.JPanel jPanel_Top;
    // End of variables declaration//GEN-END:variables
}
