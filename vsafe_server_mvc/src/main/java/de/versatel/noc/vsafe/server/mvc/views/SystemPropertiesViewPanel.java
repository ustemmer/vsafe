/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SystemPropertiesViewPanel.java
 *
 * Created on 12.06.2011, 17:00:06
 */
package de.versatel.noc.vsafe.server.mvc.views;

import de.versatel.noc.vSafe_Server.database.MySQL_PoolHandling.PoolRequestMode;
import de.versatel.noc.vSafe_Server.mvc.controllers.MySQLPoolController;
import de.versatel.noc.vSafe_Server.mvc.controllers.RMIServerController;
import de.versatel.noc.vSafe_Server.mvc.controllers.SystemPropertiesController;
import de.versatel.noc.vSafe_Server.mvc.models.MySQLPoolModel;
import de.versatel.noc.vSafe_Server.mvc.models.RMIServerModel;
import de.versatel.noc.vSafe_Server.mvc.models.SystemPropertiesModel;
import de.versatel.noc.vSafe_Server.rmi.RMIServerHandling.ServerState;

import de.versatel.noc.vSafe.mvc.AbstractViewPanel;
import de.versatel.noc.vSafe.system.SystemSettings;

import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author ulrich.stemmer
 */
public class SystemPropertiesViewPanel extends AbstractViewPanel {

    private Container contentPane;
    //private GuiHandling guiHandling;
    private final JInternalFrame frame;
    //private final Container parent;
    private final SystemPropertiesController systemPropertiesController;
    private final RMIServerController rMIServerController;
    private final MySQLPoolController poolController;
    private ServerState rMIServerState;
    private boolean jCheckBox_AutRMIStartChanged = false;
    private boolean jCheckBox_AutSQLStartChanged = false;
    private boolean jCheckBox_iWindowCloseChanged = false;
    private boolean jCheckBox_askToCloseServerChanged = false;
    private boolean jCheckBox_LogSQLChanged = false;
    private boolean jCheckBox_LogUserActionsChanged = false;
    private boolean jCheckBox_WdActiveChanged = false;
    private boolean jTextField_RMIAdressChanged = false;
    private boolean jTextField_RMIPortChanged = false;
    private boolean jTextField_SQL_IPChanged = false;
    private boolean jTextField_SQL_PortChanged = false;
    private boolean jTextField_SQL_DbChanged = false;
    private boolean jPasswordField_SQL_UserChanged = false;
    private boolean jPasswordField_SQL_PasswordChanged = false;
    private boolean jTextField_PoolsizeChanged = false;
    private boolean jTextFieldl_MaxPoolSizeChanged = false;
    private boolean jTextField_WdIntervalChanged = false;
    private boolean jTextField_WdDeadlockToChanged = false;
    private boolean jRadioButton_LFWindowsChanged = false;
    private boolean jRadioButton_LFJavaChanged = false;
    private boolean jRadioButton_ModeSeqChanged = false;
    private boolean jRadioButton_ModeCyclChanged = false;

    /** Creates new form SystemPropertiesViewPanel */
    public SystemPropertiesViewPanel(
            JInternalFrame frame,
            Container contentPane,
            SystemPropertiesController systemPropertiesController,
            RMIServerController rMIServerController, MySQLPoolController poolController) {
        this.frame = frame;
        this.contentPane = contentPane;
        this.systemPropertiesController = systemPropertiesController;
        this.rMIServerController = rMIServerController;
        this.poolController = poolController;
        initComponents();
    }

    public void modelPropertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(SystemPropertiesModel.ASKFORSERVERCLOSING)) {
            boolean b = (Boolean) evt.getNewValue();
            if (b != this.jCheckBox_askToCloseServer.isSelected()) {
                jCheckBox_askToCloseServer.setSelected(b);
            }
        } else if (evt.getPropertyName().equals(SystemPropertiesModel.AUTOMATICINIWINDOWCLOSE)) {
            boolean b = (Boolean) evt.getNewValue();
            if (b != this.jCheckBox_iWindowClose.isSelected()) {
                jCheckBox_iWindowClose.setSelected(b);
            }
        } else if (evt.getPropertyName().equals(SystemPropertiesModel.AUTOMATICRMISTART)) {
            boolean b = (Boolean) evt.getNewValue();
            if (b != this.jCheckBox_AutRMIStart.isSelected()) {
                jCheckBox_AutRMIStart.setSelected(b);
            }
        } else if (evt.getPropertyName().equals(SystemPropertiesModel.AUTOMATICSQLSTART)) {
            boolean b = (Boolean) evt.getNewValue();
            if (b != jCheckBox_AutSQLStart.isSelected()) {
                jCheckBox_AutSQLStart.setSelected(b);
            }
        } else if (evt.getPropertyName().equals(SystemPropertiesModel.LOOKANDFEEL)) {
            SystemSettings.LookNFeel lf = (SystemSettings.LookNFeel) evt.getNewValue();
            if (lf.equals(SystemSettings.LookNFeel.Windows) && !jRadioButton_LFWindows.isSelected()) {
                jRadioButton_LFWindows.setSelected(true);
            } else if (lf.equals(SystemSettings.LookNFeel.Java) && !jRadioButton_LFJava.isSelected()) {
                jRadioButton_LFJava.setSelected(true);
            }

        } else if (evt.getPropertyName().equals(SystemPropertiesModel.RMIPORT)) {
            String nv = Integer.toString((Integer) evt.getNewValue());
            if (!jTextField_RMIPort.getText().equals(nv)) {
                jTextField_RMIPort.setText(Integer.toString((Integer) evt.getNewValue()));
            }

        } else if (evt.getPropertyName().equals(SystemPropertiesModel.RMISERVERADDRESS)) {
            if (!evt.getNewValue().equals(this.jTextField_RMIAdress.getText())) {
                this.jTextField_RMIAdress.setText(evt.getNewValue().toString());
            }

        } else if (evt.getPropertyName().equals(SystemPropertiesModel.SQLDEADLOCKTIMEOUT)) {
            Long millies = (Long) evt.getNewValue();
            int seconds = (int) (millies / 1000);
            int fieldVal = 0;
            try {
                fieldVal = Integer.parseInt(jTextField_WdDeadlockTo.getText().trim());
            } catch (NumberFormatException e) {
            }
            if (fieldVal != seconds) {
                this.jTextField_WdDeadlockTo.setText(String.valueOf(seconds));
            }

        } else if (evt.getPropertyName().equals(SystemPropertiesModel.SQLMAXPOOLSIZE)) {
            String nv = Integer.toString((Integer) evt.getNewValue());
            if (!jTextFieldl_MaxPoolSize.getText().trim().equals(nv)) {
                jTextFieldl_MaxPoolSize.setText(Integer.toString((Integer) evt.getNewValue()));
            }

        } else if (evt.getPropertyName().equals(SystemPropertiesModel.SQLPOOLREQUESTMODE)) {
            if (evt.getNewValue().equals(PoolRequestMode.ZYKLISCH)
                    && !this.jRadioButton_ModeCycl.isSelected()) {
                jRadioButton_ModeCycl.setSelected(true);
            } else if (evt.getNewValue().equals(PoolRequestMode.SEQUENZIELL)
                    && !this.jRadioButton_ModeSeq.isSelected()) {
                jRadioButton_ModeSeq.setSelected(true);
            }

        } else if (evt.getPropertyName().equals(SystemPropertiesModel.SQLPOOLSIZE)) {
            String nv = Integer.toString((Integer) evt.getNewValue());
            if (!jTextField_Poolsize.getText().trim().equals(nv)) {
                jTextField_Poolsize.setText(Integer.toString((Integer) evt.getNewValue()));
            }

        } else if (evt.getPropertyName().equals(SystemPropertiesModel.SQLSERVERDB)) {
            if (!evt.getNewValue().equals(jTextField_SQL_Db.getText().trim())) {
                jTextField_SQL_Db.setText(evt.getNewValue().toString());
            }

        } else if (evt.getPropertyName().equals(SystemPropertiesModel.SQLSERVERDRIVER)) {
            if (!evt.getNewValue().equals(jTextField_SQL_Driver.getText().trim())) {
                jTextField_SQL_Driver.setText(evt.getNewValue().toString());
            }

        } else if (evt.getPropertyName().equals(SystemPropertiesModel.SQLSERVERIP)) {
            if (!evt.getNewValue().equals(jTextField_SQL_IP.getText().trim())) {
                jTextField_SQL_IP.setText(evt.getNewValue().toString());
            }

        } else if (evt.getPropertyName().equals(SystemPropertiesModel.SQLSERVERPW)) {
            if (!evt.getNewValue().equals(String.valueOf(jPasswordField_SQL_Password.getPassword()))) {
                jPasswordField_SQL_Password.setText(evt.getNewValue().toString());
            }

        } else if (evt.getPropertyName().equals(SystemPropertiesModel.SQLSERVERPORT)) {
            if (!evt.getNewValue().equals(jTextField_SQL_Port.getText().trim())) {
                jTextField_SQL_Port.setText(evt.getNewValue().toString());
            }

        } else if (evt.getPropertyName().equals(SystemPropertiesModel.SQLSERVERURL)) {
            if (!evt.getNewValue().equals(jTextField_SQL_URL.getText().trim())) {
                jTextField_SQL_URL.setText(evt.getNewValue().toString());
            }

        } else if (evt.getPropertyName().equals(SystemPropertiesModel.SQLSERVERUSER)) {
            if (!evt.getNewValue().equals(String.valueOf(jPasswordField_SQL_User.getPassword()))) {
                jPasswordField_SQL_User.setText(evt.getNewValue().toString());
            }

        } else if (evt.getPropertyName().equals(SystemPropertiesModel.SQLSERVERWATCHDOGACTIVE)) {
            boolean b = (Boolean) evt.getNewValue();
            if (b != jCheckBox_WdActive.isSelected()) {
                jCheckBox_WdActive.setSelected(b);
            }

        } else if (evt.getPropertyName().equals(SystemPropertiesModel.SQLSERVERWATCHDOGINTERVAL)) {
            Long millies = (Long) evt.getNewValue();
            int seconds = (int) (millies / 1000);
            // TODO java.lang.NumberFormatException
            int fieldVal = 0;
            try {
                fieldVal = Integer.parseInt(this.jTextField_WdInterval.getText().trim());
            } catch (NumberFormatException e) {
            }

            if (fieldVal != seconds) {
                this.jTextField_WdInterval.setText(String.valueOf(seconds));
            }

        } else if (evt.getPropertyName().equals(SystemPropertiesModel.SYSTEMACTIONLOGGING)) {
            boolean b = (Boolean) evt.getNewValue();
            if (b != this.jCheckBox_LogUserActions.isSelected()) {
                jCheckBox_LogUserActions.setSelected(b);
            }

        } else if (evt.getPropertyName().equals(SystemPropertiesModel.SYSTEMSQLLOGGING)) {
            boolean b = (Boolean) evt.getNewValue();
            if (b != this.jCheckBox_LogSQL.isSelected()) {
                jCheckBox_LogSQL.setSelected(b);
            }
        } else if (evt.getPropertyName().equals(RMIServerModel.RMI_STATE)) {
            rMIServerState = (ServerState) evt.getNewValue();
            if (rMIServerState.equals(ServerState.STOPPED)) {
                this.jButton_RMIAdress.setEnabled(true);
                this.jButton_RMIPort.setEnabled(true);
                this.jCheckBox_AutRMIStart.setEnabled(true);
                this.jTextField_RMIAdress.setEnabled(false);
                this.jTextField_RMIPort.setEnabled(false);
            } else {
                this.jButton_RMIAdress.setEnabled(false);
                this.jButton_RMIPort.setEnabled(false);
                this.jCheckBox_AutRMIStart.setEnabled(false);
                this.jTextField_RMIAdress.setEnabled(false);
                this.jTextField_RMIPort.setEnabled(false);
            }
        }
    }

    private boolean changesValid() {
        // Allgemeine Einstellungen
        // RMI-Einstellungen
        if (jTextField_RMIAdress.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(contentPane,
                    "Bitte gültige RMI-Server Adresse eintragen!", "Fehler",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (rMIServerState != null && rMIServerState.equals(ServerState.STARTED)) {
            JOptionPane.showMessageDialog(contentPane,
                    "Bitte erst RMI-Server stoppen !", "Fehler",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // RMI-Port in Textfeld schreiben
        int number;
        try {
            number = Integer.parseInt(jTextField_RMIPort.getText());
            if ((number < SystemSettings.RMI_PORT_MIN) || (number > SystemSettings.RMI_PORT_MAX)) {
                return false;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(contentPane,
                    "Ungültige Port-Nummer !", "Fehler",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        jTextField_RMIPort.setEnabled(false);

        // Driver für SQL-Server in Textfeld schreiben
        jTextField_SQL_Driver.setEnabled(false);

        // URL für SQL-Server in Textfeld schreiben
        jTextField_SQL_URL.setEnabled(false);

        // IP für SQL-Server in Textfeld schreiben
        jTextField_SQL_IP.setEnabled(false);

        // Port für SQL-Server in Textfeld schreiben
        jTextField_SQL_Port.setEnabled(false);

        /* Text im SQLButton setzen
        if (frame .mySQL_Pool == null) {
        jButton_SQLConnect.setText("Connect");
        } else {
        jButton_SQLConnect.setText("Disconnect");
        }*/

        return true;

    }

    private void applyChanges() {

        // Seite 1 - Generell
        boolean changed = false;
        if (jCheckBox_iWindowCloseChanged) {
            systemPropertiesController.changeSystemProperty(SystemPropertiesModel.AUTOMATICINIWINDOWCLOSE, jCheckBox_iWindowClose.isSelected());
            changed = true;
            jCheckBox_iWindowCloseChanged = false;
        }
        if (jCheckBox_askToCloseServerChanged) {
            systemPropertiesController.changeSystemProperty(SystemPropertiesModel.ASKFORSERVERCLOSING, jCheckBox_askToCloseServer.isSelected());
            changed = true;
            jCheckBox_askToCloseServerChanged = false;
        }
        if (jRadioButton_LFWindowsChanged || jRadioButton_LFJavaChanged) {
            if (jRadioButton_LFJava.isSelected()) {
                systemPropertiesController.changeSystemProperty(SystemPropertiesModel.LOOKANDFEEL, SystemSettings.LookNFeel.Java);
            } else {
                systemPropertiesController.changeSystemProperty(SystemPropertiesModel.LOOKANDFEEL, SystemSettings.LookNFeel.Windows);
            }
            changed = true;
            jRadioButton_LFWindowsChanged = false;
            jRadioButton_LFJavaChanged = false;
        }
        if (jCheckBox_LogSQLChanged) {
            systemPropertiesController.changeSystemProperty(SystemPropertiesModel.SYSTEMSQLLOGGING, jCheckBox_LogSQL.isSelected());
            poolController.setLogging(jCheckBox_LogSQL.isSelected());
            changed = true;
            jCheckBox_LogSQLChanged = false;
        }
        if (jCheckBox_LogUserActionsChanged) {
            systemPropertiesController.changeSystemProperty(SystemPropertiesModel.SYSTEMACTIONLOGGING, jCheckBox_LogUserActions.isSelected());
            changed = true;
            jCheckBox_LogUserActionsChanged = false;
        }

        // Seite 2 - SQL
        if (jTextField_SQL_DbChanged) {
            systemPropertiesController.changeSystemProperty(SystemPropertiesModel.SQLSERVERDB, jTextField_SQL_Db.getText().trim());
            // TODO poolController (SQL - Database)
            changed = true;
            jTextField_SQL_DbChanged = false;
        }
        if (jPasswordField_SQL_UserChanged) {
            String user = String.valueOf(jPasswordField_SQL_User.getPassword()).trim();
            systemPropertiesController.changeSystemProperty(SystemPropertiesModel.SQLSERVERUSER, user);
            poolController.setUser(user);
            changed = true;
            jPasswordField_SQL_UserChanged = false;
        }
        if (jPasswordField_SQL_PasswordChanged) {
            systemPropertiesController.changeSystemProperty(SystemPropertiesModel.SQLSERVERPW, String.valueOf(jPasswordField_SQL_Password.getPassword()).trim());
            poolController.setPassword(String.valueOf(jPasswordField_SQL_Password.getPassword()).trim());
            changed = true;
            jPasswordField_SQL_PasswordChanged = false;
        }
        if (jTextField_PoolsizeChanged) {
            systemPropertiesController.changeSystemProperty(SystemPropertiesModel.SQLPOOLSIZE, Integer.parseInt(jTextField_Poolsize.getText().trim()));
            poolController.setPoolSize(Integer.parseInt(jTextField_Poolsize.getText().trim()));
            changed = true;
            jTextField_PoolsizeChanged = false;
        }
        if (jTextFieldl_MaxPoolSizeChanged) {
            systemPropertiesController.changeSystemProperty(SystemPropertiesModel.SQLMAXPOOLSIZE, jTextFieldl_MaxPoolSize.getText().trim());
            poolController.setMaxPoolSize(Integer.parseInt(jTextFieldl_MaxPoolSize.getText().trim()));
            changed = true;
            jTextFieldl_MaxPoolSizeChanged = false;
        }
        if (jRadioButton_ModeSeqChanged || jRadioButton_ModeCyclChanged) {
            if (jRadioButton_ModeSeq.isSelected()) {
                systemPropertiesController.changeSystemProperty(SystemPropertiesModel.SQLPOOLREQUESTMODE, PoolRequestMode.SEQUENZIELL);
                poolController.setRequestMode(PoolRequestMode.SEQUENZIELL);
            } else {
                systemPropertiesController.changeSystemProperty(SystemPropertiesModel.SQLPOOLREQUESTMODE, PoolRequestMode.ZYKLISCH);
                poolController.setRequestMode(PoolRequestMode.ZYKLISCH);
            }
            changed = true;
            jRadioButton_ModeSeqChanged = false;
            jRadioButton_ModeCyclChanged = false;
        }
        if (jCheckBox_WdActiveChanged) {
            systemPropertiesController.changeSystemProperty(SystemPropertiesModel.SQLSERVERWATCHDOGACTIVE, jCheckBox_WdActive.isSelected());
            poolController.setWatchdogActive(jCheckBox_WdActive.isSelected());
            changed = true;
            jCheckBox_WdActiveChanged = false;
        }
        if (jTextField_WdIntervalChanged) {
            systemPropertiesController.changeSystemProperty(SystemPropertiesModel.SQLSERVERWATCHDOGINTERVAL, jTextField_WdInterval.getText().trim());
            poolController.setWatchdogInterval( Long.parseLong(jTextField_WdInterval.getText().trim()));
            changed = true;
            jTextField_WdIntervalChanged = false;
        }
        if (jTextField_WdDeadlockToChanged) {
            systemPropertiesController.changeSystemProperty(SystemPropertiesModel.SQLDEADLOCKTIMEOUT, jTextField_WdDeadlockTo.getText().trim());
            // TODO poolController (SQL - Watchdog Deadlock Timeout)
            changed = true;
            jTextField_WdDeadlockToChanged = false;
        }
        if (jCheckBox_AutSQLStartChanged) {
            systemPropertiesController.changeSystemProperty(SystemPropertiesModel.AUTOMATICSQLSTART, jCheckBox_AutSQLStart.isSelected());
            // TODO poolController (SQL - Autoconnect)
            changed = true;
            jCheckBox_AutSQLStartChanged = false;
        }
        if (jTextField_SQL_IPChanged) {
            systemPropertiesController.changeSystemProperty(SystemPropertiesModel.SQLSERVERIP, jTextField_SQL_IP.getText().trim());
            // TODO poolController (SQL - IP)
            changed = true;
            jTextField_SQL_IPChanged = false;
        }
        if (jTextField_SQL_PortChanged) {
            systemPropertiesController.changeSystemProperty(SystemPropertiesModel.SQLSERVERPORT, jTextField_SQL_Port.getText().trim());
            // TODO poolController (SQL - Port)
            changed = true;
            jTextField_SQL_PortChanged = false;

            // Seite 3 - RMI
            if (jCheckBox_AutRMIStartChanged) {
                systemPropertiesController.changeSystemProperty(SystemPropertiesModel.AUTOMATICRMISTART, jCheckBox_AutRMIStart.isSelected());
                changed = true;
                jCheckBox_AutRMIStartChanged = false;
            }
            if (jTextField_RMIAdressChanged) {
                systemPropertiesController.changeSystemProperty(SystemPropertiesModel.RMISERVERADDRESS, jTextField_RMIAdress.getText().trim());
                changed = true;
                jTextField_RMIAdressChanged = false;
            }
            if (jTextField_RMIPortChanged) {
                systemPropertiesController.changeSystemProperty(SystemPropertiesModel.RMIPORT, jTextField_RMIPort.getText().trim());
                changed = true;
                jTextField_RMIPortChanged = false;
            }




        }

        if (changed) {
            systemPropertiesController.changeSystemProperty(SystemPropertiesModel.SAVEPROPS, null);
        } else {
            JOptionPane.showMessageDialog(contentPane,
                    "Keine Änderungen!", "Hinweis",
                    JOptionPane.WARNING_MESSAGE);
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

        jButtonGroup_LookAndFeel = new javax.swing.ButtonGroup();
        jButtonGroup_SQLRequestMode = new javax.swing.ButtonGroup();
        jTabbedPane_Settings = new javax.swing.JTabbedPane();
        jPanelGeneral = new javax.swing.JPanel();
        jPanel_GeneralSettings = new javax.swing.JPanel();
        jCheckBox_askToCloseServer = new javax.swing.JCheckBox();
        jCheckBox_iWindowClose = new javax.swing.JCheckBox();
        jLabel_LookAndFeel = new javax.swing.JLabel();
        jRadioButton_LFWindows = new javax.swing.JRadioButton();
        jRadioButton_LFJava = new javax.swing.JRadioButton();
        jPanelFiller1 = new javax.swing.JPanel();
        jPanel_Logging = new javax.swing.JPanel();
        jCheckBox_LogSQL = new javax.swing.JCheckBox();
        jCheckBox_LogUserActions = new javax.swing.JCheckBox();
        jPanel_FillerLogging = new javax.swing.JPanel();
        jPanel_FillerGS = new javax.swing.JPanel();
        jPanelRMI = new javax.swing.JPanel();
        jCheckBox_AutRMIStart = new javax.swing.JCheckBox();
        jPanel_RMISettings = new javax.swing.JPanel();
        jLabel_RMIAdress = new javax.swing.JLabel();
        jLabel_RMIPort = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jTextField_RMIAdress = new javax.swing.JTextField();
        jTextField_RMIPort = new javax.swing.JTextField();
        jButton_RMIAdress = new javax.swing.JButton();
        jButton_RMIPort = new javax.swing.JButton();
        jPanel_FillerRMI = new javax.swing.JPanel();
        jPanelMySQL = new javax.swing.JPanel();
        jPanel_MySQLSettings = new javax.swing.JPanel();
        jCheckBox_AutSQLStart = new javax.swing.JCheckBox();
        jPanel_SQLServer = new javax.swing.JPanel();
        jLabel_SQL_Port = new javax.swing.JLabel();
        jLabel_SQL_IP = new javax.swing.JLabel();
        jLabel_SQL_Driver = new javax.swing.JLabel();
        jLabel_SQL_URL = new javax.swing.JLabel();
        jLabel_SQL_Db = new javax.swing.JLabel();
        jLabel_SQL_User = new javax.swing.JLabel();
        jLabel_SQL_Password = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTextField_SQL_IP = new javax.swing.JTextField();
        jTextField_SQL_Port = new javax.swing.JTextField();
        jTextField_SQL_Driver = new javax.swing.JTextField();
        jTextField_SQL_URL = new javax.swing.JTextField();
        jTextField_SQL_Db = new javax.swing.JTextField();
        jPasswordField_SQL_User = new javax.swing.JPasswordField();
        jPasswordField_SQL_Password = new javax.swing.JPasswordField();
        jButton_SQLPortChange = new javax.swing.JButton();
        jButton_SQLIPChange = new javax.swing.JButton();
        jPanel_SQLPool = new javax.swing.JPanel();
        jLabel_Poolsize = new javax.swing.JLabel();
        jLabel_MaxPoolSize = new javax.swing.JLabel();
        jLabel_PoolMode = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTextField_Poolsize = new javax.swing.JTextField();
        jTextFieldl_MaxPoolSize = new javax.swing.JTextField();
        jRadioButton_ModeSeq = new javax.swing.JRadioButton();
        jRadioButton_ModeCycl = new javax.swing.JRadioButton();
        jPanel_SQLWatchdog = new javax.swing.JPanel();
        jLabel_WdInterval = new javax.swing.JLabel();
        jLabel_WdDeadlockTo = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jCheckBox_WdActive = new javax.swing.JCheckBox();
        jTextField_WdInterval = new javax.swing.JTextField();
        jTextField_WdDeadlockTo = new javax.swing.JTextField();
        jPanel_FillerSQL = new javax.swing.JPanel();
        jPanel_Buttons = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jButton_Cancel = new javax.swing.JButton();
        jButton_Apply = new javax.swing.JButton();
        jButton_Ok = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(420, 520));
        setPreferredSize(new java.awt.Dimension(420, 520));
        setLayout(new java.awt.GridBagLayout());

        jTabbedPane_Settings.setAlignmentX(0.0F);
        jTabbedPane_Settings.setAlignmentY(0.0F);
        jTabbedPane_Settings.setAutoscrolls(true);
        jTabbedPane_Settings.setOpaque(true);
        jTabbedPane_Settings.setPreferredSize(new java.awt.Dimension(507, 400));

        jPanelGeneral.setLayout(new java.awt.GridBagLayout());

        jPanel_GeneralSettings.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Programmeinstellungen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel_GeneralSettings.setFont(new java.awt.Font("Tahoma", 1, 11));
        jPanel_GeneralSettings.setLayout(new java.awt.GridBagLayout());

        jCheckBox_askToCloseServer.setText("Sicherheitsfrage beim Beenden des Programms");
        jCheckBox_askToCloseServer.setBorder(null);
        jCheckBox_askToCloseServer.setOpaque(false);
        jCheckBox_askToCloseServer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jCheckBox_askToCloseServerFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 5, 0);
        jPanel_GeneralSettings.add(jCheckBox_askToCloseServer, gridBagConstraints);

        jCheckBox_iWindowClose.setText("Initialisierungsfenster automatisch schliessen");
        jCheckBox_iWindowClose.setBorder(null);
        jCheckBox_iWindowClose.setMargin(new java.awt.Insets(10, 10, 0, 0));
        jCheckBox_iWindowClose.setOpaque(false);
        jCheckBox_iWindowClose.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jCheckBox_iWindowCloseFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel_GeneralSettings.add(jCheckBox_iWindowClose, gridBagConstraints);

        jLabel_LookAndFeel.setText("Look and Feel:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 5, 0);
        jPanel_GeneralSettings.add(jLabel_LookAndFeel, gridBagConstraints);

        jButtonGroup_LookAndFeel.add(jRadioButton_LFWindows);
        jRadioButton_LFWindows.setText("Windows");
        jRadioButton_LFWindows.setMargin(new java.awt.Insets(0, 2, 0, 2));
        jRadioButton_LFWindows.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jRadioButton_LFWindowsFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 5, 0);
        jPanel_GeneralSettings.add(jRadioButton_LFWindows, gridBagConstraints);

        jButtonGroup_LookAndFeel.add(jRadioButton_LFJava);
        jRadioButton_LFJava.setText("Java");
        jRadioButton_LFJava.setMargin(new java.awt.Insets(0, 2, 0, 2));
        jRadioButton_LFJava.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jRadioButton_LFJavaFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 5, 0);
        jPanel_GeneralSettings.add(jRadioButton_LFJava, gridBagConstraints);

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
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel_GeneralSettings.add(jPanelFiller1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        jPanelGeneral.add(jPanel_GeneralSettings, gridBagConstraints);

        jPanel_Logging.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Loggingeinstellungen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel_Logging.setLayout(new java.awt.GridBagLayout());

        jCheckBox_LogSQL.setText("SQL-Meldungen");
        jCheckBox_LogSQL.setPreferredSize(new java.awt.Dimension(101, 15));
        jCheckBox_LogSQL.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jCheckBox_LogSQLFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel_Logging.add(jCheckBox_LogSQL, gridBagConstraints);

        jCheckBox_LogUserActions.setText("Benutzeraktionen");
        jCheckBox_LogUserActions.setPreferredSize(new java.awt.Dimension(109, 15));
        jCheckBox_LogUserActions.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jCheckBox_LogUserActionsFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 5, 0);
        jPanel_Logging.add(jCheckBox_LogUserActions, gridBagConstraints);

        javax.swing.GroupLayout jPanel_FillerLoggingLayout = new javax.swing.GroupLayout(jPanel_FillerLogging);
        jPanel_FillerLogging.setLayout(jPanel_FillerLoggingLayout);
        jPanel_FillerLoggingLayout.setHorizontalGroup(
            jPanel_FillerLoggingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel_FillerLoggingLayout.setVerticalGroup(
            jPanel_FillerLoggingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        jPanel_Logging.add(jPanel_FillerLogging, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        jPanelGeneral.add(jPanel_Logging, gridBagConstraints);

        javax.swing.GroupLayout jPanel_FillerGSLayout = new javax.swing.GroupLayout(jPanel_FillerGS);
        jPanel_FillerGS.setLayout(jPanel_FillerGSLayout);
        jPanel_FillerGSLayout.setHorizontalGroup(
            jPanel_FillerGSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel_FillerGSLayout.setVerticalGroup(
            jPanel_FillerGSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 211, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weighty = 1.0;
        jPanelGeneral.add(jPanel_FillerGS, gridBagConstraints);

        jTabbedPane_Settings.addTab("Generell", jPanelGeneral);

        jPanelRMI.setLayout(new java.awt.GridBagLayout());

        jCheckBox_AutRMIStart.setText("RMI-Server bei Programmstart automatisch starten");
        jCheckBox_AutRMIStart.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jCheckBox_AutRMIStartFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        jPanelRMI.add(jCheckBox_AutRMIStart, gridBagConstraints);

        jPanel_RMISettings.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "RMI-Einstellungen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel_RMISettings.setLayout(new java.awt.GridBagLayout());

        jLabel_RMIAdress.setText("Server-Adresse :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 0);
        jPanel_RMISettings.add(jLabel_RMIAdress, gridBagConstraints);

        jLabel_RMIPort.setText("Incoming Port (1024-49151) :");
        jLabel_RMIPort.setMaximumSize(new java.awt.Dimension(142, 20));
        jLabel_RMIPort.setMinimumSize(new java.awt.Dimension(142, 20));
        jLabel_RMIPort.setPreferredSize(new java.awt.Dimension(142, 20));
        jLabel_RMIPort.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 5, 0);
        jPanel_RMISettings.add(jLabel_RMIPort, gridBagConstraints);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel_RMISettings.add(jPanel8, gridBagConstraints);

        jTextField_RMIAdress.setEnabled(false);
        jTextField_RMIAdress.setMinimumSize(new java.awt.Dimension(150, 20));
        jTextField_RMIAdress.setPreferredSize(new java.awt.Dimension(150, 20));
        jTextField_RMIAdress.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_RMIAdressFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 0, 0);
        jPanel_RMISettings.add(jTextField_RMIAdress, gridBagConstraints);

        jTextField_RMIPort.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField_RMIPort.setText("49151");
        jTextField_RMIPort.setEnabled(false);
        jTextField_RMIPort.setMinimumSize(new java.awt.Dimension(50, 20));
        jTextField_RMIPort.setPreferredSize(new java.awt.Dimension(50, 20));
        jTextField_RMIPort.setRequestFocusEnabled(false);
        jTextField_RMIPort.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_RMIPortFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 5, 0);
        jPanel_RMISettings.add(jTextField_RMIPort, gridBagConstraints);

        jButton_RMIAdress.setText("Ändern");
        jButton_RMIAdress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RMIAdressActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 5);
        jPanel_RMISettings.add(jButton_RMIAdress, gridBagConstraints);

        jButton_RMIPort.setText("Ändern");
        jButton_RMIPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RMIPortActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 5, 5);
        jPanel_RMISettings.add(jButton_RMIPort, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        jPanelRMI.add(jPanel_RMISettings, gridBagConstraints);

        javax.swing.GroupLayout jPanel_FillerRMILayout = new javax.swing.GroupLayout(jPanel_FillerRMI);
        jPanel_FillerRMI.setLayout(jPanel_FillerRMILayout);
        jPanel_FillerRMILayout.setHorizontalGroup(
            jPanel_FillerRMILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel_FillerRMILayout.setVerticalGroup(
            jPanel_FillerRMILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 291, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelRMI.add(jPanel_FillerRMI, gridBagConstraints);

        jTabbedPane_Settings.addTab("RMI-Server", jPanelRMI);

        jPanelMySQL.setLayout(new java.awt.GridBagLayout());

        jPanel_MySQLSettings.setLayout(new java.awt.GridBagLayout());

        jCheckBox_AutSQLStart.setText("SQL-Verbindungen beim Start automatisch aufbauen");
        jCheckBox_AutSQLStart.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jCheckBox_AutSQLStartFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel_MySQLSettings.add(jCheckBox_AutSQLStart, gridBagConstraints);

        jPanel_SQLServer.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Server - Einstellungen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel_SQLServer.setLayout(new java.awt.GridBagLayout());

        jLabel_SQL_Port.setText("Port:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 0);
        jPanel_SQLServer.add(jLabel_SQL_Port, gridBagConstraints);

        jLabel_SQL_IP.setText("IP:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 0);
        jPanel_SQLServer.add(jLabel_SQL_IP, gridBagConstraints);

        jLabel_SQL_Driver.setText("Driver:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 0);
        jPanel_SQLServer.add(jLabel_SQL_Driver, gridBagConstraints);

        jLabel_SQL_URL.setText("URL:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 0);
        jPanel_SQLServer.add(jLabel_SQL_URL, gridBagConstraints);

        jLabel_SQL_Db.setText("Datenbank:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 0);
        jPanel_SQLServer.add(jLabel_SQL_Db, gridBagConstraints);

        jLabel_SQL_User.setText("Username:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 0);
        jPanel_SQLServer.add(jLabel_SQL_User, gridBagConstraints);

        jLabel_SQL_Password.setText("Password:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 3, 0);
        jPanel_SQLServer.add(jLabel_SQL_Password, gridBagConstraints);

        jPanel1.setRequestFocusEnabled(false);

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
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel_SQLServer.add(jPanel1, gridBagConstraints);

        jTextField_SQL_IP.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField_SQL_IP.setText("000.000.000.000");
        jTextField_SQL_IP.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextField_SQL_IP.setEnabled(false);
        jTextField_SQL_IP.setPreferredSize(new java.awt.Dimension(100, 20));
        jTextField_SQL_IP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_SQL_IPFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 94;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 0);
        jPanel_SQLServer.add(jTextField_SQL_IP, gridBagConstraints);

        jTextField_SQL_Port.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField_SQL_Port.setText("10000");
        jTextField_SQL_Port.setEnabled(false);
        jTextField_SQL_Port.setMinimumSize(new java.awt.Dimension(60, 20));
        jTextField_SQL_Port.setPreferredSize(new java.awt.Dimension(60, 20));
        jTextField_SQL_Port.setRequestFocusEnabled(false);
        jTextField_SQL_Port.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_SQL_PortFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 0, 0);
        jPanel_SQLServer.add(jTextField_SQL_Port, gridBagConstraints);

        jTextField_SQL_Driver.setEditable(false);
        jTextField_SQL_Driver.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField_SQL_Driver.setMinimumSize(new java.awt.Dimension(200, 20));
        jTextField_SQL_Driver.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        jPanel_SQLServer.add(jTextField_SQL_Driver, gridBagConstraints);

        jTextField_SQL_URL.setEditable(false);
        jTextField_SQL_URL.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField_SQL_URL.setMinimumSize(new java.awt.Dimension(200, 20));
        jTextField_SQL_URL.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        jPanel_SQLServer.add(jTextField_SQL_URL, gridBagConstraints);

        jTextField_SQL_Db.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField_SQL_Db.setMinimumSize(new java.awt.Dimension(150, 20));
        jTextField_SQL_Db.setPreferredSize(new java.awt.Dimension(150, 20));
        jTextField_SQL_Db.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_SQL_DbFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        jPanel_SQLServer.add(jTextField_SQL_Db, gridBagConstraints);

        jPasswordField_SQL_User.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPasswordField_SQL_User.setText("jPasswordField1");
        jPasswordField_SQL_User.setMinimumSize(new java.awt.Dimension(150, 20));
        jPasswordField_SQL_User.setPreferredSize(new java.awt.Dimension(150, 20));
        jPasswordField_SQL_User.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPasswordField_SQL_UserFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        jPanel_SQLServer.add(jPasswordField_SQL_User, gridBagConstraints);

        jPasswordField_SQL_Password.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPasswordField_SQL_Password.setText("jPasswordField2");
        jPasswordField_SQL_Password.setMinimumSize(new java.awt.Dimension(150, 20));
        jPasswordField_SQL_Password.setPreferredSize(new java.awt.Dimension(150, 20));
        jPasswordField_SQL_Password.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPasswordField_SQL_PasswordFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        jPanel_SQLServer.add(jPasswordField_SQL_Password, gridBagConstraints);

        jButton_SQLPortChange.setText("Ändern");
        jButton_SQLPortChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SQLPortChangeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 5);
        jPanel_SQLServer.add(jButton_SQLPortChange, gridBagConstraints);

        jButton_SQLIPChange.setText("Ändern");
        jButton_SQLIPChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SQLIPChangeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 5);
        jPanel_SQLServer.add(jButton_SQLIPChange, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 5);
        jPanel_MySQLSettings.add(jPanel_SQLServer, gridBagConstraints);

        jPanel_SQLPool.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pool - Einstellungen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel_SQLPool.setLayout(new java.awt.GridBagLayout());

        jLabel_Poolsize.setText("Größe:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 0);
        jPanel_SQLPool.add(jLabel_Poolsize, gridBagConstraints);

        jLabel_MaxPoolSize.setText("Maximale Größe:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 0);
        jPanel_SQLPool.add(jLabel_MaxPoolSize, gridBagConstraints);

        jLabel_PoolMode.setText("Abfragemodus:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 3, 0);
        jPanel_SQLPool.add(jLabel_PoolMode, gridBagConstraints);

        jPanel2.setPreferredSize(new java.awt.Dimension(0, 72));

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
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel_SQLPool.add(jPanel2, gridBagConstraints);

        jTextField_Poolsize.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField_Poolsize.setPreferredSize(new java.awt.Dimension(50, 20));
        jTextField_Poolsize.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_PoolsizeFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 7);
        jPanel_SQLPool.add(jTextField_Poolsize, gridBagConstraints);

        jTextFieldl_MaxPoolSize.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldl_MaxPoolSize.setPreferredSize(new java.awt.Dimension(50, 20));
        jTextFieldl_MaxPoolSize.setRequestFocusEnabled(false);
        jTextFieldl_MaxPoolSize.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldl_MaxPoolSizeFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 7);
        jPanel_SQLPool.add(jTextFieldl_MaxPoolSize, gridBagConstraints);

        jButtonGroup_SQLRequestMode.add(jRadioButton_ModeSeq);
        jRadioButton_ModeSeq.setText("SEQUENZIELL");
        jRadioButton_ModeSeq.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jRadioButton_ModeSeq.setMargin(new java.awt.Insets(2, 2, 2, 0));
        jRadioButton_ModeSeq.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jRadioButton_ModeSeqFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 0, 0);
        jPanel_SQLPool.add(jRadioButton_ModeSeq, gridBagConstraints);

        jButtonGroup_SQLRequestMode.add(jRadioButton_ModeCycl);
        jRadioButton_ModeCycl.setText("ZYKLISCH");
        jRadioButton_ModeCycl.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jRadioButton_ModeCycl.setMargin(new java.awt.Insets(2, 2, 2, 0));
        jRadioButton_ModeCycl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jRadioButton_ModeCyclFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 7);
        jPanel_SQLPool.add(jRadioButton_ModeCycl, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jPanel_MySQLSettings.add(jPanel_SQLPool, gridBagConstraints);

        jPanel_SQLWatchdog.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Watchdog - Einstellungen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel_SQLWatchdog.setLayout(new java.awt.GridBagLayout());

        jLabel_WdInterval.setText("Interval (Sekunden):");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 0);
        jPanel_SQLWatchdog.add(jLabel_WdInterval, gridBagConstraints);

        jLabel_WdDeadlockTo.setText("Deadlock Timeout (Sekunden):");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 0);
        jPanel_SQLWatchdog.add(jLabel_WdDeadlockTo, gridBagConstraints);

        jPanel4.setPreferredSize(new java.awt.Dimension(0, 72));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel_SQLWatchdog.add(jPanel4, gridBagConstraints);

        jCheckBox_WdActive.setText("Aktiv");
        jCheckBox_WdActive.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jCheckBox_WdActiveFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 5);
        jPanel_SQLWatchdog.add(jCheckBox_WdActive, gridBagConstraints);

        jTextField_WdInterval.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField_WdInterval.setPreferredSize(new java.awt.Dimension(50, 20));
        jTextField_WdInterval.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_WdIntervalFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 7);
        jPanel_SQLWatchdog.add(jTextField_WdInterval, gridBagConstraints);

        jTextField_WdDeadlockTo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField_WdDeadlockTo.setPreferredSize(new java.awt.Dimension(50, 20));
        jTextField_WdDeadlockTo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_WdDeadlockToFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 7);
        jPanel_SQLWatchdog.add(jTextField_WdDeadlockTo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel_MySQLSettings.add(jPanel_SQLWatchdog, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        jPanelMySQL.add(jPanel_MySQLSettings, gridBagConstraints);

        javax.swing.GroupLayout jPanel_FillerSQLLayout = new javax.swing.GroupLayout(jPanel_FillerSQL);
        jPanel_FillerSQL.setLayout(jPanel_FillerSQLLayout);
        jPanel_FillerSQLLayout.setHorizontalGroup(
            jPanel_FillerSQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel_FillerSQLLayout.setVerticalGroup(
            jPanel_FillerSQLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelMySQL.add(jPanel_FillerSQL, gridBagConstraints);

        jTabbedPane_Settings.addTab("MySQL-Pool", jPanelMySQL);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jTabbedPane_Settings, gridBagConstraints);

        jPanel_Buttons.setLayout(new java.awt.GridBagLayout());

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        jPanel_Buttons.add(jPanel10, gridBagConstraints);

        jButton_Cancel.setText("Abbrechen");
        jButton_Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel_Buttons.add(jButton_Cancel, gridBagConstraints);

        jButton_Apply.setText("Übernehmen");
        jButton_Apply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ApplyActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 10);
        jPanel_Buttons.add(jButton_Apply, gridBagConstraints);

        jButton_Ok.setText("Ok");
        jButton_Ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_OkActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 10);
        jPanel_Buttons.add(jButton_Ok, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        add(jPanel_Buttons, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_RMIPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RMIPortActionPerformed
        if (jButton_RMIPort.getText().equals("Ändern")) {
            jButton_RMIPort.setText("OK");
            jTextField_RMIPort.setEnabled(true);
        } else {
            String text = jTextField_RMIPort.getText().trim();
            int number;
            try {
                number = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                number = 0;
                JOptionPane.showMessageDialog(contentPane, "Eingabe fehlerhaft !",
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if ((number < 1024) || (number > 49151)) {
                JOptionPane.showMessageDialog(contentPane,
                        "Keine gültige PortNummer !", "Fehler",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            jButton_RMIPort.setText("Ändern");
            jTextField_RMIPort.setEnabled(false);
        }
    }//GEN-LAST:event_jButton_RMIPortActionPerformed

    private void jButton_SQLPortChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SQLPortChangeActionPerformed
        if (jButton_SQLPortChange.getText().equals("Ändern")) {
            jButton_SQLPortChange.setText("OK");
            jTextField_SQL_Port.setEnabled(true);
        } else {
            jButton_SQLPortChange.setText("Ändern");
            jTextField_SQL_Port.setEnabled(false);
        }
}//GEN-LAST:event_jButton_SQLPortChangeActionPerformed

    private void jButton_SQLIPChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SQLIPChangeActionPerformed
        if (jButton_SQLIPChange.getText().equals("Ändern")) {
            jButton_SQLIPChange.setText("OK");
            jTextField_SQL_IP.setEnabled(true);
        } else {
            jButton_SQLIPChange.setText("Ändern");
            jTextField_SQL_IP.setEnabled(false);
        }
}//GEN-LAST:event_jButton_SQLIPChangeActionPerformed

    private void jButton_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CancelActionPerformed
        int result = JOptionPane.showConfirmDialog(contentPane,
                "Sollen die Änderungen verworfen werden?", "Abbruch!", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                frame.setClosed(true);
            } catch (PropertyVetoException ex) {
            }
        }
}//GEN-LAST:event_jButton_CancelActionPerformed

    private void jButton_ApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ApplyActionPerformed
        if (changesValid()) {
            applyChanges();
        }
}//GEN-LAST:event_jButton_ApplyActionPerformed

    private void jButton_OkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_OkActionPerformed
        if (changesValid()) {
            applyChanges();
            try {
                frame.setClosed(true);
            } catch (PropertyVetoException ex) {
                //Logger.getLogger(SystemPropertiesViewPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
}//GEN-LAST:event_jButton_OkActionPerformed
    private void jCheckBox_iWindowCloseFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jCheckBox_iWindowCloseFocusLost
        jCheckBox_iWindowCloseChanged = true;
    }//GEN-LAST:event_jCheckBox_iWindowCloseFocusLost
    private void jCheckBox_askToCloseServerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jCheckBox_askToCloseServerFocusLost
        jCheckBox_askToCloseServerChanged = true;
    }//GEN-LAST:event_jCheckBox_askToCloseServerFocusLost
    private void jRadioButton_LFWindowsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jRadioButton_LFWindowsFocusLost
        jRadioButton_LFWindowsChanged = true;
    }//GEN-LAST:event_jRadioButton_LFWindowsFocusLost
    private void jCheckBox_LogSQLFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jCheckBox_LogSQLFocusLost
        jCheckBox_LogSQLChanged = true;
    }//GEN-LAST:event_jCheckBox_LogSQLFocusLost
    private void jCheckBox_LogUserActionsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jCheckBox_LogUserActionsFocusLost
        jCheckBox_LogUserActionsChanged = true;
    }//GEN-LAST:event_jCheckBox_LogUserActionsFocusLost
    private void jCheckBox_AutRMIStartFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jCheckBox_AutRMIStartFocusLost
        jCheckBox_AutRMIStartChanged = true;
    }//GEN-LAST:event_jCheckBox_AutRMIStartFocusLost
    private void jTextField_RMIAdressFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_RMIAdressFocusLost
        jTextField_RMIAdressChanged = true;
    }//GEN-LAST:event_jTextField_RMIAdressFocusLost
    private void jTextField_RMIPortFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_RMIPortFocusLost
        jTextField_RMIPortChanged = true;
    }//GEN-LAST:event_jTextField_RMIPortFocusLost
    private void jTextField_SQL_DbFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_SQL_DbFocusLost
        jTextField_SQL_DbChanged = true;
    }//GEN-LAST:event_jTextField_SQL_DbFocusLost
    private void jPasswordField_SQL_UserFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordField_SQL_UserFocusLost
        jPasswordField_SQL_UserChanged = true;
    }//GEN-LAST:event_jPasswordField_SQL_UserFocusLost
    private void jPasswordField_SQL_PasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordField_SQL_PasswordFocusLost
        jPasswordField_SQL_PasswordChanged = true;
    }//GEN-LAST:event_jPasswordField_SQL_PasswordFocusLost
    private void jTextField_PoolsizeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_PoolsizeFocusLost
        jTextField_PoolsizeChanged = true;
    }//GEN-LAST:event_jTextField_PoolsizeFocusLost
    private void jTextFieldl_MaxPoolSizeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldl_MaxPoolSizeFocusLost
        jTextFieldl_MaxPoolSizeChanged = true;
    }//GEN-LAST:event_jTextFieldl_MaxPoolSizeFocusLost
    private void jRadioButton_ModeSeqFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jRadioButton_ModeSeqFocusLost
        jRadioButton_ModeSeqChanged = true;
    }//GEN-LAST:event_jRadioButton_ModeSeqFocusLost
    private void jRadioButton_ModeCyclFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jRadioButton_ModeCyclFocusLost
        jRadioButton_ModeCyclChanged = true;
    }//GEN-LAST:event_jRadioButton_ModeCyclFocusLost
    private void jCheckBox_WdActiveFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jCheckBox_WdActiveFocusLost
        jCheckBox_WdActiveChanged = true;
    }//GEN-LAST:event_jCheckBox_WdActiveFocusLost
    private void jTextField_WdIntervalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_WdIntervalFocusLost
        jTextField_WdIntervalChanged = true;
    }//GEN-LAST:event_jTextField_WdIntervalFocusLost
    private void jTextField_WdDeadlockToFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_WdDeadlockToFocusLost
        jTextField_WdDeadlockToChanged = true;
    }//GEN-LAST:event_jTextField_WdDeadlockToFocusLost

    private void jTextField_SQL_IPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_SQL_IPFocusLost
        if (jTextField_SQL_IP.isEnabled()) {
            jTextField_SQL_IPChanged = true;
        }
    }//GEN-LAST:event_jTextField_SQL_IPFocusLost

    private void jTextField_SQL_PortFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_SQL_PortFocusLost
        if (jTextField_SQL_Port.isEnabled()) {
            jTextField_SQL_PortChanged = true;
        }
    }//GEN-LAST:event_jTextField_SQL_PortFocusLost

    private void jRadioButton_LFJavaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jRadioButton_LFJavaFocusLost
        jRadioButton_LFJavaChanged = true;
    }//GEN-LAST:event_jRadioButton_LFJavaFocusLost

    private void jButton_RMIAdressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RMIAdressActionPerformed
        if (jButton_RMIAdress.getText().equals("Ändern")) {
            jButton_RMIAdress.setText("OK");
            jTextField_RMIAdress.setEnabled(true);
        } else {
            jTextField_RMIAdress.setText(jTextField_RMIAdress.getText().trim());
            jButton_RMIAdress.setText("Ändern");
            jTextField_RMIAdress.setEnabled(false);
        }
    }//GEN-LAST:event_jButton_RMIAdressActionPerformed

    private void jCheckBox_AutSQLStartFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jCheckBox_AutSQLStartFocusLost
        jCheckBox_AutSQLStartChanged = true;
    }//GEN-LAST:event_jCheckBox_AutSQLStartFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup jButtonGroup_LookAndFeel;
    private javax.swing.ButtonGroup jButtonGroup_SQLRequestMode;
    private javax.swing.JButton jButton_Apply;
    private javax.swing.JButton jButton_Cancel;
    private javax.swing.JButton jButton_Ok;
    private javax.swing.JButton jButton_RMIAdress;
    private javax.swing.JButton jButton_RMIPort;
    private javax.swing.JButton jButton_SQLIPChange;
    private javax.swing.JButton jButton_SQLPortChange;
    private javax.swing.JCheckBox jCheckBox_AutRMIStart;
    private javax.swing.JCheckBox jCheckBox_AutSQLStart;
    private javax.swing.JCheckBox jCheckBox_LogSQL;
    private javax.swing.JCheckBox jCheckBox_LogUserActions;
    private javax.swing.JCheckBox jCheckBox_WdActive;
    private javax.swing.JCheckBox jCheckBox_askToCloseServer;
    private javax.swing.JCheckBox jCheckBox_iWindowClose;
    private javax.swing.JLabel jLabel_LookAndFeel;
    private javax.swing.JLabel jLabel_MaxPoolSize;
    private javax.swing.JLabel jLabel_PoolMode;
    private javax.swing.JLabel jLabel_Poolsize;
    private javax.swing.JLabel jLabel_RMIAdress;
    private javax.swing.JLabel jLabel_RMIPort;
    private javax.swing.JLabel jLabel_SQL_Db;
    private javax.swing.JLabel jLabel_SQL_Driver;
    private javax.swing.JLabel jLabel_SQL_IP;
    private javax.swing.JLabel jLabel_SQL_Password;
    private javax.swing.JLabel jLabel_SQL_Port;
    private javax.swing.JLabel jLabel_SQL_URL;
    private javax.swing.JLabel jLabel_SQL_User;
    private javax.swing.JLabel jLabel_WdDeadlockTo;
    private javax.swing.JLabel jLabel_WdInterval;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanelFiller1;
    private javax.swing.JPanel jPanelGeneral;
    private javax.swing.JPanel jPanelMySQL;
    private javax.swing.JPanel jPanelRMI;
    private javax.swing.JPanel jPanel_Buttons;
    private javax.swing.JPanel jPanel_FillerGS;
    private javax.swing.JPanel jPanel_FillerLogging;
    private javax.swing.JPanel jPanel_FillerRMI;
    private javax.swing.JPanel jPanel_FillerSQL;
    private javax.swing.JPanel jPanel_GeneralSettings;
    private javax.swing.JPanel jPanel_Logging;
    private javax.swing.JPanel jPanel_MySQLSettings;
    private javax.swing.JPanel jPanel_RMISettings;
    private javax.swing.JPanel jPanel_SQLPool;
    private javax.swing.JPanel jPanel_SQLServer;
    private javax.swing.JPanel jPanel_SQLWatchdog;
    private javax.swing.JPasswordField jPasswordField_SQL_Password;
    private javax.swing.JPasswordField jPasswordField_SQL_User;
    private javax.swing.JRadioButton jRadioButton_LFJava;
    private javax.swing.JRadioButton jRadioButton_LFWindows;
    private javax.swing.JRadioButton jRadioButton_ModeCycl;
    private javax.swing.JRadioButton jRadioButton_ModeSeq;
    private javax.swing.JTabbedPane jTabbedPane_Settings;
    private javax.swing.JTextField jTextField_Poolsize;
    private javax.swing.JTextField jTextField_RMIAdress;
    private javax.swing.JTextField jTextField_RMIPort;
    private javax.swing.JTextField jTextField_SQL_Db;
    private javax.swing.JTextField jTextField_SQL_Driver;
    private javax.swing.JTextField jTextField_SQL_IP;
    private javax.swing.JTextField jTextField_SQL_Port;
    private javax.swing.JTextField jTextField_SQL_URL;
    private javax.swing.JTextField jTextField_WdDeadlockTo;
    private javax.swing.JTextField jTextField_WdInterval;
    private javax.swing.JTextField jTextFieldl_MaxPoolSize;
    // End of variables declaration//GEN-END:variables
}
