/*
 * FrameMain.java
 *
 * Created on 08.02.2011, 04:42:02
 */
package de.versatel.noc.vsafe.server.gui;

/**
 *
 * @author ulrich.stemmer
 */
import de.versatel.noc.vsafe.shared.core.settings.SystemSettings;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author ulrich.stemmer
 */
public class FrameMain extends JFrame {

    static final long serialVersionUID = SystemSettings.SERIALVERSIONUID;
    private GuiHandling guiHandler;

    /** Creates new form FrameMain
     * @param startWindow
     */
    public FrameMain(GuiHandling guiHandler) {
        this.guiHandler = guiHandler;
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

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jPanel_BottomBar = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jMenuBar_Main = new javax.swing.JMenuBar();
        jMenu_File = new javax.swing.JMenu();
        jMenuItem_Settings = new javax.swing.JMenuItem();
        jMenuItem_DebugWindow = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem_Exit = new javax.swing.JMenuItem();
        jMenu_Edit = new javax.swing.JMenu();
        jMenu_View = new javax.swing.JMenu();
        jCheckBoxMenuItem_DBState = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem_RMIState = new javax.swing.JCheckBoxMenuItem();
        jMenu_Database = new javax.swing.JMenu();
        jMenuItem_Pool = new javax.swing.JMenuItem();
        jMenu_Usermanagement = new javax.swing.JMenu();
        jMenuItem_UserGroups = new javax.swing.JMenuItem();
        jMenuItem_Users = new javax.swing.JMenuItem();
        jMenuItem_Sessions = new javax.swing.JMenuItem();
        jMenu_Window = new javax.swing.JMenu();
        jMenu_TileVertical = new javax.swing.JMenuItem();
        jMenu_LF = new javax.swing.JMenu();
        jMenuItem_Java = new javax.swing.JMenuItem();
        jMenuItem_Windows = new javax.swing.JMenuItem();
        jMenu_Help = new javax.swing.JMenu();
        jMenuItem_About = new javax.swing.JMenuItem();
        jMenuItem_Versions = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jDesktopPane1.setBackground(new java.awt.Color(255, 255, 255));
        jDesktopPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jDesktopPane1.setAlignmentX(0.0F);
        jDesktopPane1.setAlignmentY(0.0F);
        getContentPane().add(jDesktopPane1, java.awt.BorderLayout.CENTER);

        jPanel_BottomBar.setAlignmentX(0.0F);
        jPanel_BottomBar.setAlignmentY(0.0F);
        jPanel_BottomBar.setMinimumSize(new java.awt.Dimension(2, 20));
        jPanel_BottomBar.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanel_BottomBar.setLayout(new java.awt.CardLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jPanel_BottomBar.add(jToolBar1, "card2");

        getContentPane().add(jPanel_BottomBar, java.awt.BorderLayout.SOUTH);

        jMenu_File.setMnemonic('D');
        jMenu_File.setText("Datei");

        jMenuItem_Settings.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem_Settings.setText("Einstellungen...");
        jMenuItem_Settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_SettingsActionPerformed(evt);
            }
        });
        jMenu_File.add(jMenuItem_Settings);

        jMenuItem_DebugWindow.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem_DebugWindow.setText("Debug-Fenster");
        jMenuItem_DebugWindow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_DebugWindowActionPerformed(evt);
            }
        });
        jMenu_File.add(jMenuItem_DebugWindow);
        jMenu_File.add(jSeparator1);

        jMenuItem_Exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem_Exit.setText("Beenden");
        jMenuItem_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_ExitActionPerformed(evt);
            }
        });
        jMenu_File.add(jMenuItem_Exit);

        jMenuBar_Main.add(jMenu_File);

        jMenu_Edit.setMnemonic('B');
        jMenu_Edit.setText("Bearbeiten");
        jMenuBar_Main.add(jMenu_Edit);

        jMenu_View.setMnemonic('A');
        jMenu_View.setText("Ansicht");

        jCheckBoxMenuItem_DBState.setSelected(true);
        jCheckBoxMenuItem_DBState.setLabel("Datenbankstatus");
        jCheckBoxMenuItem_DBState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem_DBStateActionPerformed(evt);
            }
        });
        jMenu_View.add(jCheckBoxMenuItem_DBState);

        jCheckBoxMenuItem_RMIState.setSelected(true);
        jCheckBoxMenuItem_RMIState.setText("Serverstatus");
        jCheckBoxMenuItem_RMIState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem_RMIStateActionPerformed(evt);
            }
        });
        jMenu_View.add(jCheckBoxMenuItem_RMIState);

        jMenuBar_Main.add(jMenu_View);

        jMenu_Database.setMnemonic('B');
        jMenu_Database.setText("Datenbank");

        jMenuItem_Pool.setMnemonic('V');
        jMenuItem_Pool.setText("Verbindungen");
        jMenuItem_Pool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_PoolActionPerformed(evt);
            }
        });
        jMenu_Database.add(jMenuItem_Pool);

        jMenuBar_Main.add(jMenu_Database);

        jMenu_Usermanagement.setMnemonic('u');
        jMenu_Usermanagement.setText("Benutzerverwaltung");

        jMenuItem_UserGroups.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem_UserGroups.setText("Benutzergruppen...");
        jMenuItem_UserGroups.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_UserGroupsActionPerformed(evt);
            }
        });
        jMenu_Usermanagement.add(jMenuItem_UserGroups);

        jMenuItem_Users.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem_Users.setText("Benutzer....");
        jMenuItem_Users.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_UsersActionPerformed(evt);
            }
        });
        jMenu_Usermanagement.add(jMenuItem_Users);

        jMenuItem_Sessions.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem_Sessions.setText("Sessions...");
        jMenuItem_Sessions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_SessionsActionPerformed(evt);
            }
        });
        jMenu_Usermanagement.add(jMenuItem_Sessions);

        jMenuBar_Main.add(jMenu_Usermanagement);

        jMenu_Window.setText("Fenster");

        jMenu_TileVertical.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.ALT_MASK));
        jMenu_TileVertical.setText("Vertikal anordnen");
        jMenu_TileVertical.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu_TileVerticalActionPerformed(evt);
            }
        });
        jMenu_Window.add(jMenu_TileVertical);

        jMenuBar_Main.add(jMenu_Window);

        jMenu_LF.setMnemonic('L');
        jMenu_LF.setText("Layout");

        jMenuItem_Java.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_J, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem_Java.setText("Java");
        jMenuItem_Java.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_JavaActionPerformed(evt);
            }
        });
        jMenu_LF.add(jMenuItem_Java);

        jMenuItem_Windows.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem_Windows.setText("Windows");
        jMenuItem_Windows.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_WindowsActionPerformed(evt);
            }
        });
        jMenu_LF.add(jMenuItem_Windows);

        jMenuBar_Main.add(jMenu_LF);

        jMenu_Help.setMnemonic('H');
        jMenu_Help.setText("Hilfe");

        jMenuItem_About.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem_About.setText("Über...");
        jMenuItem_About.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_AboutActionPerformed(evt);
            }
        });
        jMenu_Help.add(jMenuItem_About);

        jMenuItem_Versions.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem_Versions.setText("Versionen...");
        jMenuItem_Versions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_VersionsActionPerformed(evt);
            }
        });
        jMenu_Help.add(jMenuItem_Versions);

        jMenuBar_Main.add(jMenu_Help);

        setJMenuBar(jMenuBar_Main);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (guiHandler.getSystemManager().getSystemProperties().isAskForServerClosing()) {
            int i = JOptionPane.showConfirmDialog(this.getContentPane(),
                    "Soll die Anwendung wirklich beendet werden ?", "Frage:", JOptionPane.YES_NO_OPTION);
            if (i != JOptionPane.YES_OPTION) {
                return;
            }
        }
        guiHandler.getSystemManager().shutdown();
        dispose();
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem_SettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_SettingsActionPerformed
        guiHandler.openSettingFrame();
    }//GEN-LAST:event_jMenuItem_SettingsActionPerformed

    private void jMenuItem_UsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_UsersActionPerformed
        guiHandler.openUserFrame();
    }//GEN-LAST:event_jMenuItem_UsersActionPerformed

    private void jMenuItem_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_ExitActionPerformed
        dispose();
    }//GEN-LAST:event_jMenuItem_ExitActionPerformed

    private void jMenu_TileVerticalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu_TileVerticalActionPerformed
        // TODO Benutzerdefinierte Anordnung der Fenster einrichten
        //java.awt.Component[] components = this.jDesktopPane1.getComponents();
        // for ()
    }//GEN-LAST:event_jMenu_TileVerticalActionPerformed

    private void jMenuItem_JavaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_JavaActionPerformed

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            guiHandler.getSystemManager().getSystemProperties().setLookAndFeel(SystemSettings.LookNFeel.Java);
            jMenuItem_Java.setEnabled(false);
            jMenuItem_Windows.setEnabled(true);
        } catch (Exception e) {
            // TODO HelperMethods.showErrorMessage((Throwable) e, getContentPane());
        }
        SwingUtilities.updateComponentTreeUI(this);

    }//GEN-LAST:event_jMenuItem_JavaActionPerformed

    private void jMenuItem_WindowsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_WindowsActionPerformed
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            guiHandler.getSystemManager().getSystemProperties().setLookAndFeel(SystemSettings.LookNFeel.Windows);
            jMenuItem_Windows.setEnabled(false);
            jMenuItem_Java.setEnabled(true);
        } catch (Exception e) {
            // TODO HelperMethods.showErrorMessage((Throwable) e, getContentPane());
        }
        SwingUtilities.updateComponentTreeUI(this);
    }//GEN-LAST:event_jMenuItem_WindowsActionPerformed

    private void jMenuItem_UserGroupsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_UserGroupsActionPerformed
        // TODO UserGroupsActionPerformed
    }//GEN-LAST:event_jMenuItem_UserGroupsActionPerformed

    private void jMenuItem_SessionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_SessionsActionPerformed
        // TODO SessionsActionPerformed
    }//GEN-LAST:event_jMenuItem_SessionsActionPerformed

    private void jMenuItem_AboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_AboutActionPerformed
        // TODO AboutActionPerformed
    }//GEN-LAST:event_jMenuItem_AboutActionPerformed

    private void jMenuItem_VersionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_VersionsActionPerformed
        // TODO VersionsActionPerformed
    }//GEN-LAST:event_jMenuItem_VersionsActionPerformed

    private void jMenuItem_PoolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_PoolActionPerformed
        guiHandler.openPoolFrame();
    }//GEN-LAST:event_jMenuItem_PoolActionPerformed

    private void jCheckBoxMenuItem_DBStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem_DBStateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxMenuItem_DBStateActionPerformed

    private void jCheckBoxMenuItem_RMIStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem_RMIStateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxMenuItem_RMIStateActionPerformed

    private void jMenuItem_DebugWindowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_DebugWindowActionPerformed
        guiHandler.openDebuggingFrame();
    }//GEN-LAST:event_jMenuItem_DebugWindowActionPerformed
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem_DBState;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem_RMIState;
    public javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenuBar jMenuBar_Main;
    private javax.swing.JMenuItem jMenuItem_About;
    private javax.swing.JMenuItem jMenuItem_DebugWindow;
    private javax.swing.JMenuItem jMenuItem_Exit;
    private javax.swing.JMenuItem jMenuItem_Java;
    private javax.swing.JMenuItem jMenuItem_Pool;
    private javax.swing.JMenuItem jMenuItem_Sessions;
    private javax.swing.JMenuItem jMenuItem_Settings;
    private javax.swing.JMenuItem jMenuItem_UserGroups;
    private javax.swing.JMenuItem jMenuItem_Users;
    private javax.swing.JMenuItem jMenuItem_Versions;
    private javax.swing.JMenuItem jMenuItem_Windows;
    private javax.swing.JMenu jMenu_Database;
    private javax.swing.JMenu jMenu_Edit;
    private javax.swing.JMenu jMenu_File;
    private javax.swing.JMenu jMenu_Help;
    private javax.swing.JMenu jMenu_LF;
    private javax.swing.JMenuItem jMenu_TileVertical;
    private javax.swing.JMenu jMenu_Usermanagement;
    private javax.swing.JMenu jMenu_View;
    private javax.swing.JMenu jMenu_Window;
    private javax.swing.JPanel jPanel_BottomBar;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
