/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UserGroupViewPanel.java
 *
 * Created on 13.01.2012, 19:08:32
 */

package de.versatel.noc.vsafe.server.mvc.views;

/**
 *
 * @author ulrich.stemmer
 */
public class UserGroupViewPanel extends javax.swing.JPanel {

    /** Creates new form UserGroupViewPanel */
    public UserGroupViewPanel() {
        initComponents();
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

        jLabel_Index = new javax.swing.JLabel();
        jTextField_Name = new javax.swing.JTextField();
        jTextField_Remark = new javax.swing.JTextField();
        jPanel_Filler = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        jLabel_Index.setText("jLabel1");
        jLabel_Index.setMaximumSize(new java.awt.Dimension(100, 20));
        jLabel_Index.setMinimumSize(new java.awt.Dimension(34, 20));
        jLabel_Index.setPreferredSize(new java.awt.Dimension(34, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        add(jLabel_Index, gridBagConstraints);

        jTextField_Name.setMinimumSize(new java.awt.Dimension(150, 20));
        jTextField_Name.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        add(jTextField_Name, gridBagConstraints);

        jTextField_Remark.setMinimumSize(new java.awt.Dimension(350, 20));
        jTextField_Remark.setPreferredSize(new java.awt.Dimension(450, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        add(jTextField_Remark, gridBagConstraints);

        javax.swing.GroupLayout jPanel_FillerLayout = new javax.swing.GroupLayout(jPanel_Filler);
        jPanel_Filler.setLayout(jPanel_FillerLayout);
        jPanel_FillerLayout.setHorizontalGroup(
            jPanel_FillerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel_FillerLayout.setVerticalGroup(
            jPanel_FillerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel_Filler, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel_Index;
    private javax.swing.JPanel jPanel_Filler;
    private javax.swing.JTextField jTextField_Name;
    private javax.swing.JTextField jTextField_Remark;
    // End of variables declaration//GEN-END:variables

}
