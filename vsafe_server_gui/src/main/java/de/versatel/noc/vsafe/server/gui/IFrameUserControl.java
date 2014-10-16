/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * IFrameUserControl.java
 *
 * Created on 12.02.2011, 03:14:00
 */
package de.versatel.noc.vsafe.server.gui;

/**
 *
 * @author ulrich.stemmer
 */
import de.versatel.noc.vSafe_Server.users.User;
import de.versatel.noc.vSafe_Server.users.UserMessages;
import de.versatel.noc.vSafe_Server.users.UserGroup;
import javax.swing.JOptionPane;

import de.versatel.noc.vSafe_Server.bl.*;

/**
 *
 * @author ulrich.stemmer
 */
public class IFrameUserControl extends javax.swing.JInternalFrame {

    /**
     *
     */
    public FrameMain frameMain;

    /** Creates new form IFrameUserControl */
    public IFrameUserControl() {
        initComponents();
    }

    /**
     *
     * @param frameMain
     */
    public IFrameUserControl(FrameMain frameMain) {
        initComponents();
        this.frameMain = frameMain;
        setIcons();
    }

    private void setIcons() {
        jLabel_Edit.setIcon(frameMain.iconLibrary.getImageIconButtonEdit2_20x20_MouseExited());
        jLabel_Edit.setDisabledIcon(frameMain.iconLibrary.getImageIconButtonAdd2_20x20_Disabled());
        jLabel_Add.setIcon(frameMain.iconLibrary.getImageIconButtonAdd2_20x20_MouseExited());
        jLabel_Add.setDisabledIcon(frameMain.iconLibrary.getImageIconButtonAdd2_20x20_Disabled());
        jLabel_Delete.setIcon(frameMain.iconLibrary.getImageIconButtonRemove2_20x20_MouseExited());
        jLabel_Delete.setDisabledIcon(frameMain.iconLibrary.getImageIconButtonRemove2_20x20_Disabled());
    }

    private void changeUser() {
        User user;
        User tempUser;
        Object o = this.jComboBox_Username.getSelectedItem();
        if (o instanceof User) {
            user = (User) o;
            tempUser = new User();
            tempUser.setUserid(user.getUserid());
            tempUser.setUsername(user.getUsername());
            tempUser.setPassword(new String(this.jPasswordField_1.getPassword()));
            tempUser.setFirstname(this.jTextField_FirstName.getText());
            tempUser.setName(this.jTextField_Name.getText());
            tempUser.setState(this.jComboBox_State.getSelectedIndex());
            UserGroup ug = (UserGroup) this.jComboBox_Usergroup.getSelectedItem();
            tempUser.setUserGroupId(ug.getUserGroupID());
            tempUser.setMaxWrongLogins(Integer.parseInt(this.jTextField_MaxWrongLogins.getText()));
            tempUser.setActWrongLogins(Integer.parseInt(this.jTextField_ActWrongLogins.getText()));
            tempUser.setMaxSessions(Integer.parseInt(this.jTextField_MaxSessions.getText()));
            tempUser.setSessionTimeout(Long.parseLong(this.jTextField_SessionTimeout.getText()));

            String s = null;
            int result = frameMain.userHandling.changeUser(user, tempUser);
            switch (result) {
                case UserMessages.NOTHING_TO_CHANGE:
                    s = "Es gibt nichts zu ändern.";
                case UserMessages.OK:
                    s = "Daten wurden geändert.";
                case UserMessages.DB_DBMISMATCH:
                    s = "Datenbankfehler.";
            }
            if (s != null) {
                JOptionPane.showMessageDialog(this.getContentPane(), s, "Hinweis",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void cancel() {
        String s = "Sollen die Änderungen verworfen werden ?";
        int i = javax.swing.JOptionPane.showConfirmDialog(this.getContentPane(), s, getTitle()
                + "Frage", javax.swing.JOptionPane.YES_NO_OPTION);
        if (i == javax.swing.JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }

    private void resetPassword() {
        String toCrypt = this.frameMain.userHandling.generateUserPassword(8);
        String s = "Soll das neue Passwort '" + toCrypt
                + "' übernommen werden ?";

        int i = JOptionPane.showConfirmDialog(frameMain.getContentPane(), s, getTitle() + "Frage",
                JOptionPane.YES_NO_OPTION);
        if (i == JOptionPane.YES_OPTION) {
            String cryptedPasswort = this.frameMain.userHandling.cryptUserPassword(toCrypt);
            jPasswordField_1.setText(cryptedPasswort);
        }
    }

    private void deleteUser() {
        String str = "Soll der User '"
                + this.jComboBox_Username.getSelectedItem()
                + "' wirklich gel�scht werden ?";
        int i = JOptionPane.showConfirmDialog(getContentPane(), str,
                getTitle() + "Frage", JOptionPane.YES_NO_OPTION);
        if (i == JOptionPane.YES_OPTION) {
            boolean b = frameMain.userHandling.deleteUser((User) this.jComboBox_Username.getSelectedItem());
            if (b) {
                String t = "Der User '"
                        + this.jComboBox_Username.getSelectedItem()
                        + "' wurde geloescht !";
                JOptionPane.showMessageDialog(getContentPane(), t, getTitle()
                        + "Hinweis", JOptionPane.OK_OPTION);
                this.jComboBox_Username.removeItem(this.jComboBox_Username.getSelectedItem());
            } else {
                String t = "Der User '"
                        + this.jComboBox_Username.getSelectedItem()
                        + "' konnte nicht geloescht werden ?";
                JOptionPane.showMessageDialog(getContentPane(), t, getTitle()
                        + "Hinweis", JOptionPane.OK_OPTION);
            }
        } else {
            return;
        }

    }

    private void showUserData(User user) {
        user = (User) jComboBox_Username.getSelectedItem();
        jPasswordField_1.setText(user.getPassword());
        for (int i = 0; i < jComboBox_Usergroup.getItemCount(); i++) {
            if (jComboBox_Usergroup.getItemAt(i) != null
                    && jComboBox_Usergroup.getItemAt(i) instanceof UserGroup) {
                UserGroup userGroup = (UserGroup) jComboBox_Usergroup.getItemAt(i);
                if (userGroup.getUserGroupID() == user.getUserGroupId()) {
                    jComboBox_Usergroup.setSelectedIndex(i);
                    break;
                }
            }
        }
        jTextField_Name.setText(user.getName());
        jTextField_FirstName.setText(user.getFirstname());
        jComboBox_State.setSelectedIndex(user.getState());
        jTextField_ActWrongLogins.setText(String.valueOf(user.getActWrongLogins()));
        jTextField_MaxWrongLogins.setText(String.valueOf(user.getMaxWrongLogins()));
        jTextField_MaxSessions.setText(String.valueOf(user.getMaxSessions()));
    }

    private void newUser() {
        IFrameNewUser iFrameNewUser = new IFrameNewUser(this);
        frameMain.jDesktopPane1.add(iFrameNewUser);
        this.setVisible(false);
        iFrameNewUser.setFrameIcon(this.getFrameIcon());
        iFrameNewUser.setTitle(frameMain.systemHandling.systemSettings.IFRAME_USERS);
        iFrameNewUser.setLocation(
                (frameMain.jDesktopPane1.getSize().width - iFrameNewUser.getSize().width) / 2,
                (frameMain.jDesktopPane1.getSize().height - iFrameNewUser.getSize().height) / 2);
        iFrameNewUser.setVisible(true);
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

        jPanel_N = new javax.swing.JPanel();
        jLabel_Title = new javax.swing.JLabel();
        jLabel_Username = new javax.swing.JLabel();
        jComboBox_Username = new javax.swing.JComboBox();
        jLabel_Edit = new javax.swing.JLabel();
        jButton_Edit = new javax.swing.JButton();
        jLabel_Add = new javax.swing.JLabel();
        jButton_Add = new javax.swing.JButton();
        jLabel_Delete = new javax.swing.JLabel();
        jButton_Delete = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel_CN = new javax.swing.JPanel();
        jLabel_Usergroup = new javax.swing.JLabel();
        jLabel_Name = new javax.swing.JLabel();
        jLabel_FirstName = new javax.swing.JLabel();
        jLabel_Password = new javax.swing.JLabel();
        jPanelCenter = new javax.swing.JPanel();
        jComboBox_Usergroup = new javax.swing.JComboBox();
        jTextField_Name = new javax.swing.JTextField();
        jTextField_FirstName = new javax.swing.JTextField();
        jPasswordField_1 = new javax.swing.JPasswordField();
        jButton_ResetPW = new javax.swing.JButton();
        jPanelSouth = new javax.swing.JPanel();
        jPanel_CS = new javax.swing.JPanel();
        jLabel_State = new javax.swing.JLabel();
        jComboBox_State = new javax.swing.JComboBox();
        jLabel_ActWrongLogins = new javax.swing.JLabel();
        jTextField_ActWrongLogins = new javax.swing.JTextField();
        jLabel_MaxWrongLogins = new javax.swing.JLabel();
        jTextField_MaxWrongLogins = new javax.swing.JTextField();
        jLabel_MaxSessions = new javax.swing.JLabel();
        jTextField_MaxSessions = new javax.swing.JTextField();
        jLabel_SessionTimeout = new javax.swing.JLabel();
        jTextField_SessionTimeout = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel_S = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jButton_Apply = new javax.swing.JButton();
        jButton_OK = new javax.swing.JButton();
        jButton_Cancel = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel_N.setLayout(new java.awt.GridBagLayout());

        jLabel_Title.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_Title.setText("Benutzerverwaltung");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 30, 0);
        jPanel_N.add(jLabel_Title, gridBagConstraints);

        jLabel_Username.setText("Benutzername:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel_N.add(jLabel_Username, gridBagConstraints);

        jComboBox_Username.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox_Username.setMinimumSize(new java.awt.Dimension(150, 20));
        jComboBox_Username.setPreferredSize(new java.awt.Dimension(150, 22));
        jComboBox_Username.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_UsernameItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        jPanel_N.add(jComboBox_Username, gridBagConstraints);

        jLabel_Edit.setText("jLabel1");
        jLabel_Edit.setMaximumSize(new java.awt.Dimension(20, 20));
        jLabel_Edit.setMinimumSize(new java.awt.Dimension(20, 20));
        jLabel_Edit.setPreferredSize(new java.awt.Dimension(20, 20));
        jLabel_Edit.setRequestFocusEnabled(false);
        jLabel_Edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel_EditMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel_EditMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel_EditMousePressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel_N.add(jLabel_Edit, gridBagConstraints);

        jButton_Edit.setText("bearbeiten");
        jButton_Edit.setToolTipText("bearbeiten");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        jPanel_N.add(jButton_Edit, gridBagConstraints);

        jLabel_Add.setText("jLabel2");
        jLabel_Add.setMaximumSize(new java.awt.Dimension(20, 20));
        jLabel_Add.setMinimumSize(new java.awt.Dimension(20, 20));
        jLabel_Add.setPreferredSize(new java.awt.Dimension(20, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        jPanel_N.add(jLabel_Add, gridBagConstraints);

        jButton_Add.setText("Neuer Benutzer");
        jButton_Add.setToolTipText("Neuer Benutzer");
        jButton_Add.setMaximumSize(new java.awt.Dimension(120, 23));
        jButton_Add.setMinimumSize(new java.awt.Dimension(120, 23));
        jButton_Add.setPreferredSize(new java.awt.Dimension(120, 23));
        jButton_Add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_AddMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_AddMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton_AddMousePressed(evt);
            }
        });
        jButton_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_AddActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        jPanel_N.add(jButton_Add, gridBagConstraints);

        jLabel_Delete.setText("jLabel3");
        jLabel_Delete.setMaximumSize(new java.awt.Dimension(20, 20));
        jLabel_Delete.setMinimumSize(new java.awt.Dimension(20, 20));
        jLabel_Delete.setPreferredSize(new java.awt.Dimension(20, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        jPanel_N.add(jLabel_Delete, gridBagConstraints);

        jButton_Delete.setText("Lösche Benutzer");
        jButton_Delete.setToolTipText("Lösche Benutzer");
        jButton_Delete.setMaximumSize(new java.awt.Dimension(120, 23));
        jButton_Delete.setMinimumSize(new java.awt.Dimension(120, 23));
        jButton_Delete.setPreferredSize(new java.awt.Dimension(120, 23));
        jButton_Delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_DeleteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_DeleteMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton_DeleteMousePressed(evt);
            }
        });
        jButton_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_DeleteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        jPanel_N.add(jButton_Delete, gridBagConstraints);

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
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel_N.add(jPanel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jPanel_N, gridBagConstraints);

        jPanel_CN.setBorder(javax.swing.BorderFactory.createTitledBorder("Benutzereinstellungen"));
        jPanel_CN.setLayout(new java.awt.GridBagLayout());

        jLabel_Usergroup.setText("Benutzergruppe:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel_CN.add(jLabel_Usergroup, gridBagConstraints);

        jLabel_Name.setText("Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 0, 0);
        jPanel_CN.add(jLabel_Name, gridBagConstraints);

        jLabel_FirstName.setText("Vorname:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 0, 0);
        jPanel_CN.add(jLabel_FirstName, gridBagConstraints);

        jLabel_Password.setText("Passwort:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 10, 0);
        jPanel_CN.add(jLabel_Password, gridBagConstraints);

        javax.swing.GroupLayout jPanelCenterLayout = new javax.swing.GroupLayout(jPanelCenter);
        jPanelCenter.setLayout(jPanelCenterLayout);
        jPanelCenterLayout.setHorizontalGroup(
            jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelCenterLayout.setVerticalGroup(
            jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel_CN.add(jPanelCenter, gridBagConstraints);

        jComboBox_Usergroup.setMinimumSize(new java.awt.Dimension(120, 20));
        jComboBox_Usergroup.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 0, 0);
        jPanel_CN.add(jComboBox_Usergroup, gridBagConstraints);

        jTextField_Name.setMinimumSize(new java.awt.Dimension(120, 20));
        jTextField_Name.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 0, 0);
        jPanel_CN.add(jTextField_Name, gridBagConstraints);

        jTextField_FirstName.setMinimumSize(new java.awt.Dimension(120, 20));
        jTextField_FirstName.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 0, 0);
        jPanel_CN.add(jTextField_FirstName, gridBagConstraints);

        jPasswordField_1.setText("jPasswordField1");
        jPasswordField_1.setMinimumSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 10, 0);
        jPanel_CN.add(jPasswordField_1, gridBagConstraints);

        jButton_ResetPW.setText("Neues Passwort");
        jButton_ResetPW.setToolTipText("Neues Passwort");
        jButton_ResetPW.setPreferredSize(new java.awt.Dimension(120, 24));
        jButton_ResetPW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ResetPWActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 10, 0);
        jPanel_CN.add(jButton_ResetPW, gridBagConstraints);

        javax.swing.GroupLayout jPanelSouthLayout = new javax.swing.GroupLayout(jPanelSouth);
        jPanelSouth.setLayout(jPanelSouthLayout);
        jPanelSouthLayout.setHorizontalGroup(
            jPanelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelSouthLayout.setVerticalGroup(
            jPanelSouthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weighty = 1.0;
        jPanel_CN.add(jPanelSouth, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        getContentPane().add(jPanel_CN, gridBagConstraints);

        jPanel_CS.setBorder(javax.swing.BorderFactory.createTitledBorder("Session Einstellungen"));
        jPanel_CS.setLayout(new java.awt.GridBagLayout());

        jLabel_State.setText("Status:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel_CS.add(jLabel_State, gridBagConstraints);

        jComboBox_State.setMinimumSize(new java.awt.Dimension(120, 20));
        jComboBox_State.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        jPanel_CS.add(jComboBox_State, gridBagConstraints);

        jLabel_ActWrongLogins.setText("Anzahl fehlerhafter Logins:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel_CS.add(jLabel_ActWrongLogins, gridBagConstraints);

        jTextField_ActWrongLogins.setEditable(false);
        jTextField_ActWrongLogins.setMinimumSize(new java.awt.Dimension(50, 20));
        jTextField_ActWrongLogins.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        jPanel_CS.add(jTextField_ActWrongLogins, gridBagConstraints);

        jLabel_MaxWrongLogins.setText("Anzahl max. fehlerh. Logins:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel_CS.add(jLabel_MaxWrongLogins, gridBagConstraints);

        jTextField_MaxWrongLogins.setMinimumSize(new java.awt.Dimension(120, 20));
        jTextField_MaxWrongLogins.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 10);
        jPanel_CS.add(jTextField_MaxWrongLogins, gridBagConstraints);

        jLabel_MaxSessions.setText("Anzahl gleichzeitiger Sessions:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel_CS.add(jLabel_MaxSessions, gridBagConstraints);

        jTextField_MaxSessions.setMinimumSize(new java.awt.Dimension(120, 20));
        jTextField_MaxSessions.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 10);
        jPanel_CS.add(jTextField_MaxSessions, gridBagConstraints);

        jLabel_SessionTimeout.setText("Session Timeout:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        jPanel_CS.add(jLabel_SessionTimeout, gridBagConstraints);

        jTextField_SessionTimeout.setMinimumSize(new java.awt.Dimension(50, 20));
        jTextField_SessionTimeout.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel_CS.add(jTextField_SessionTimeout, gridBagConstraints);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel_CS.add(jPanel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 0.5;
        getContentPane().add(jPanel_CS, gridBagConstraints);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel5, gridBagConstraints);

        jPanel_S.setLayout(new java.awt.GridBagLayout());

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        jPanel_S.add(jPanel6, gridBagConstraints);

        jButton_Apply.setText("Übernehmen");
        jButton_Apply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ApplyActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(30, 5, 10, 0);
        jPanel_S.add(jButton_Apply, gridBagConstraints);

        jButton_OK.setText("Ok");
        jButton_OK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_OKActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(30, 5, 10, 10);
        jPanel_S.add(jButton_OK, gridBagConstraints);

        jButton_Cancel.setText("Abbrechen");
        jButton_Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(30, 30, 10, 0);
        jPanel_S.add(jButton_Cancel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jPanel_S, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_ResetPWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ResetPWActionPerformed
        resetPassword();
    }//GEN-LAST:event_jButton_ResetPWActionPerformed

    private void jButton_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AddActionPerformed
        newUser();
    }//GEN-LAST:event_jButton_AddActionPerformed

    private void jButton_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_DeleteActionPerformed
        deleteUser();
    }//GEN-LAST:event_jButton_DeleteActionPerformed

    private void jButton_ApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ApplyActionPerformed
        changeUser();
    }//GEN-LAST:event_jButton_ApplyActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened

        // Combo-Box mit Usernamen fuellen:
        if (frameMain.userHandling.users != null && frameMain.userHandling.users.size() > 0) {
            for (User user : frameMain.userHandling.users) {
                jComboBox_Username.addItem(user);
            }

            for (UserGroup usergroup : frameMain.userHandling.userGroups) {
                jComboBox_Username.addItem(usergroup);
            }

            // Combo-Box mit States fuellen:
            for (String state : frameMain.userHandling.userStates) {
                jComboBox_State.addItem(state);
            }

            // Daten des Users holen und anzeigen:
            jComboBox_Username.setSelectedIndex(0);
            if (jComboBox_Username.getSelectedItem() != null
                    && jComboBox_Username.getSelectedItem() instanceof User) {
                showUserData((User) jComboBox_Username.getSelectedItem());
            }
        }
    }//GEN-LAST:event_formInternalFrameOpened

    private void jComboBox_UsernameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_UsernameItemStateChanged
        if (jComboBox_Username.getItemCount() > 0
                && jComboBox_Username.getSelectedItem() instanceof User) {
            User user = (User) this.jComboBox_Username.getSelectedItem();
            showUserData(user);

            jComboBox_Usergroup.setEnabled(false);
            jComboBox_State.setEnabled(false);

            user.getActWrongLogins();
            user.getFirstname();
            user.getMaxSessions();
            user.getMaxWrongLogins();
            user.getName();
            user.getPassword();
            user.getSessionTimeout();
            user.getState();
            user.getUserGroupId();
            user.getUserid();
            user.getUsername();

        }
    }//GEN-LAST:event_jComboBox_UsernameItemStateChanged

    private void jButton_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CancelActionPerformed
        cancel();
    }//GEN-LAST:event_jButton_CancelActionPerformed

    private void jButton_OKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_OKActionPerformed
        changeUser();
        this.dispose();
    }//GEN-LAST:event_jButton_OKActionPerformed

    private void jLabel_EditMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_EditMousePressed
        if (jLabel_Edit.isEnabled()){
            jLabel_Edit.setIcon(frameMain.iconLibrary.getImageIconButtonEdit2_20x20_MousePressed());
        }
    }//GEN-LAST:event_jLabel_EditMousePressed

    private void jLabel_EditMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_EditMouseEntered
        if (jLabel_Edit.isEnabled()){
            jLabel_Edit.setIcon(frameMain.iconLibrary.getImageIconButtonEdit2_20x20_MouseEntered());
        }
    }//GEN-LAST:event_jLabel_EditMouseEntered

    private void jLabel_EditMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_EditMouseExited
        if (jLabel_Edit.isEnabled()){
            jLabel_Edit.setIcon(frameMain.iconLibrary.getImageIconButtonEdit2_20x20_MouseExited());
        }
    }//GEN-LAST:event_jLabel_EditMouseExited

    private void jButton_AddMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_AddMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_AddMouseEntered

    private void jButton_AddMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_AddMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_AddMouseExited

    private void jButton_AddMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_AddMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_AddMousePressed

    private void jButton_DeleteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_DeleteMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_DeleteMouseEntered

    private void jButton_DeleteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_DeleteMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_DeleteMouseExited

    private void jButton_DeleteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_DeleteMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_DeleteMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton_Add;
    public javax.swing.JButton jButton_Apply;
    public javax.swing.JButton jButton_Cancel;
    public javax.swing.JButton jButton_Delete;
    public javax.swing.JButton jButton_Edit;
    public javax.swing.JButton jButton_OK;
    public javax.swing.JButton jButton_ResetPW;
    public javax.swing.JComboBox jComboBox_State;
    public javax.swing.JComboBox jComboBox_Usergroup;
    public javax.swing.JComboBox jComboBox_Username;
    public javax.swing.JLabel jLabel_ActWrongLogins;
    public javax.swing.JLabel jLabel_Add;
    public javax.swing.JLabel jLabel_Delete;
    public javax.swing.JLabel jLabel_Edit;
    public javax.swing.JLabel jLabel_FirstName;
    public javax.swing.JLabel jLabel_MaxSessions;
    public javax.swing.JLabel jLabel_MaxWrongLogins;
    public javax.swing.JLabel jLabel_Name;
    public javax.swing.JLabel jLabel_Password;
    public javax.swing.JLabel jLabel_SessionTimeout;
    public javax.swing.JLabel jLabel_State;
    public javax.swing.JLabel jLabel_Title;
    public javax.swing.JLabel jLabel_Usergroup;
    public javax.swing.JLabel jLabel_Username;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JPanel jPanel6;
    public javax.swing.JPanel jPanelCenter;
    public javax.swing.JPanel jPanelSouth;
    public javax.swing.JPanel jPanel_CN;
    public javax.swing.JPanel jPanel_CS;
    public javax.swing.JPanel jPanel_N;
    public javax.swing.JPanel jPanel_S;
    public javax.swing.JPasswordField jPasswordField_1;
    public javax.swing.JTextField jTextField_ActWrongLogins;
    public javax.swing.JTextField jTextField_FirstName;
    public javax.swing.JTextField jTextField_MaxSessions;
    public javax.swing.JTextField jTextField_MaxWrongLogins;
    public javax.swing.JTextField jTextField_Name;
    public javax.swing.JTextField jTextField_SessionTimeout;
    // End of variables declaration//GEN-END:variables
}
