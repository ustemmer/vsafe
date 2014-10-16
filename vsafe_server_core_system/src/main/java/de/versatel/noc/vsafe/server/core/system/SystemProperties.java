package de.versatel.noc.vsafe.server.core.system;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.ImageIcon;

/**
 *
 * @author ulrich.stemmer
 */
public class SystemProperties {

    //private SystemSettings systemSettings;
    /**
     *
     */
    public static enum Primitives {

        STRING, INT, BOOLEAN, LONG
    };
    private String propsFileName;
    private String cryptAlgorythm;
    //private int cryptKeyLength;
    private byte[] cryptKey;
    private Properties properties;
    private Properties defaultSystemProperties;
    private boolean automaticRMIStart;
    private boolean automaticSQLStart;
    private String applicationIcon;
    private String rMIServerAddress;
    private int rMIPort;
    private String sQLServerDriver;
    private String sQLServerURL;
    private String sQLServerIP;
    private String sQLServerPort;
    private int sQLPoolsize;
    private int sQLMaxPoolsize;
    private String sQLPoolRequestMode;
    private boolean sQLServerWatchdogActive;
    private long sQLServerWatchdogInterval;
    private long sQLDeadlockTimeout;
    private String sQLServerDB;
    private String sQLServerUser;
    private String sQLServerPW;
    private boolean automaticIniWindowClose;
    private boolean askForServerClosing;
    private SystemSettings.LookNFeel lookAndFeel;
    private boolean systemActionLogging;
    private boolean systemSQLLogging;
    public static final String AutomaticRMIStart = "AutomaticRMIStart";
    public static final String AutomaticSQLStart = "AutomaticSQLStart";
    public static final String ApplicationIcon = "ApplicationIcon";
    public static final String RMIServerAddress = "RMIServerAddress";
    public static final String RMIPort = "RMIPort";
    public static final String SQLServerDriver = "SQLServerDriver";
    public static final String SQLServerURL = "SQLServerURL";
    public static final String SQLServerIP = "SQLServerIP";
    public static final String SQLServerPort = "SQLServerPort";
    public static final String SQLPoolsize = "SQLPoolsize";
    public static final String SQLMaxPoolsize = "SQLMaxPoolsize";
    public static final String SQLPoolRequestMode = "SQLPoolRequestMode";
    public static final String SQLServerWatchdogActive = "SQLServerWatchdogActive";
    public static final String SQLServerWatchdogInterval = "SQLServerWatchdogInterval";
    public static final String SQLDeadlockTimeout = "SQLDeadlockTimeout";
    public static final String SQLServerDB = "SQLServerDB";
    public static final String SQLServerUser = "SQLServerUser";
    public static final String SQLServerPW = "SQLServerPW";
    public static final String AutomaticIniWindowClose = "AutomaticIniWindowClose";
    public static final String AskForServerClosing = "AskForServerClosing";
    public static final String LookAndFeel = "LookAndFeel";
    public static final String SystemActionLogging = "SystemActionLogging";
    public static final String SystemSQLLogging = "SystemSQLLogging";

    //public SystemProperties(String propsFilename, String algorythm, int keylength, String key) {
    public SystemProperties(String propsFilename, String algorythm, byte[] key) {
        this.propsFileName = propsFilename;
        this.cryptAlgorythm = algorythm;
        //this.cryptKeyLength = keylength;
        this.cryptKey = key;
        this.properties = load();
        getDefaultProperties();
        compare();
        propsToVars();
    }

    public int getIntValue(String propertyName) {
        if (propertyName == null || propertyName.length() == 0) {
            throw new IllegalArgumentException();
        }
        String s = properties.getProperty(RMIPort);
        if (s == null || s.length() == 0) {
            return Integer.parseInt(s);
        } else {
            return -1;
        }
    }
    
     public long getLongValue(String propertyName) {
        if (propertyName == null || propertyName.length() == 0) {
            throw new IllegalArgumentException();
        }
        String s = properties.getProperty(RMIPort);
        if (s == null || s.length() == 0) {
            return Long.parseLong(s);
        } else {
            return -1L;
        }
    }
     
    public String getStringValue(String propertyName) {
        if (propertyName == null || propertyName.length() == 0) {
            throw new IllegalArgumentException();
        }
        return properties.getProperty(RMIPort);
    }
 

    private String compare() {

        Enumeration propNames;
        List<String> faultyItems = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();

        // properties initialisieren
        if (properties == null) {
            properties = new Properties();
        }

        // Suche Name in properties und ergänze fehlende
        propNames = defaultSystemProperties.propertyNames();
        while (propNames.hasMoreElements()) {
            String propName = (String) propNames.nextElement();
            if (!properties.containsKey(propName)) {
                String s = defaultSystemProperties.getProperty(propName);
                properties.setProperty(propName, s);
                if (sb.length() > 0) {
                    sb.append(System.getProperty("line.separator"));
                }
                sb.append("Property:'");
                sb.append(propName);
                sb.append("' wurde auf Default gesetzt: '");
                sb.append(s);
                sb.append("'.");
                faultyItems.add(sb.toString());
            }
        }

        // Suche und entferne ungültige Namen
        List<String> invalidKeys = new ArrayList<String>();
        propNames = properties.propertyNames();
        while (propNames.hasMoreElements()) {
            String propName = (String) propNames.nextElement();
            if (!defaultSystemProperties.containsKey(propName)) {
                invalidKeys.add(propName);
            }
        }
        if (!invalidKeys.isEmpty()) {
            for (String s : invalidKeys) {
                if (sb.length() > 0) {
                    sb.append(System.getProperty("line.separator"));
                }
                sb.append("Property:'");
                sb.append(s);
                sb.append("' wurde entfernt.");
            }
        }
        return sb.toString();


    }

    /**
     * @return the applicationIcon
     */
    public ImageIcon getApplicationIcon() {
        if (applicationIcon != null && !applicationIcon.isEmpty()) {
            return new ImageIcon(applicationIcon);
        }
        return null;
    }

    private void getDefaultProperties() {
        defaultSystemProperties = new Properties();
        defaultSystemProperties.setProperty(AutomaticRMIStart, "false");
        defaultSystemProperties.setProperty(AutomaticSQLStart, "false");
        defaultSystemProperties.setProperty(ApplicationIcon, "resources/gif/vSafe_Server.gif");
        defaultSystemProperties.setProperty(RMIServerAddress, "localhost");
        defaultSystemProperties.setProperty(RMIPort, "1099");
        defaultSystemProperties.setProperty(SQLServerDriver, "com.mysql.jdbc.Driver");
        defaultSystemProperties.setProperty(SQLServerURL, "jdbc:mysql://10.231.201.43:3306/");
        defaultSystemProperties.setProperty(SQLServerIP, "10.231.201.43");
        defaultSystemProperties.setProperty(SQLServerPort, "3306");
        defaultSystemProperties.setProperty(SQLPoolsize, "5");
        defaultSystemProperties.setProperty(SQLMaxPoolsize, "20");
        defaultSystemProperties.setProperty(SQLPoolRequestMode, "SEQUENTIAL");
        defaultSystemProperties.setProperty(SQLServerWatchdogActive, "false");
        defaultSystemProperties.setProperty(SQLServerWatchdogInterval, "60000");
        defaultSystemProperties.setProperty(SQLDeadlockTimeout, "30000");
        defaultSystemProperties.setProperty(SQLServerDB, "vSafe");
        defaultSystemProperties.setProperty(SQLServerUser, "nocTest");
        defaultSystemProperties.setProperty(SQLServerPW, "konsole5");
        defaultSystemProperties.setProperty(AutomaticIniWindowClose, "false");
        defaultSystemProperties.setProperty(AskForServerClosing, "true");
        defaultSystemProperties.setProperty(LookAndFeel, SystemSettings.LookNFeel.Windows.toString());
        defaultSystemProperties.setProperty(SystemActionLogging, "true");
        defaultSystemProperties.setProperty(SystemSQLLogging, "true");
    }

    /**
     * @return the lookAndFeel
     */
    public SystemSettings.LookNFeel getLookAndFeel() {
        return lookAndFeel;
    }

    /**
     * @return the rMIPort
     */
    public int getRMIPort() {
        return rMIPort;
    }

    /**
     * @return the rMIServerAddress
     */
    public String getRMIServerAddress() {
        return rMIServerAddress;
    }

    /**
     * @return the sQLDeadlockTimeout
     */
    public long getSQLDeadlockTimeout() {
        return sQLDeadlockTimeout;
    }

    public void setProperty(String propertyName, Object o) {
        if (propertyName != null && !propertyName.isEmpty()) {
            if (propertyName.equals(AutomaticRMIStart)) {
                setAutomaticRMIStart((Boolean) o);
            } else if (propertyName.equals(AutomaticSQLStart)) {
                setAutomaticSQLStart((Boolean) o);
            } else if (propertyName.equals(ApplicationIcon)) {
                setApplicationIcon((String) o);
            } else if (propertyName.equals(RMIServerAddress)) {
                setRMIServerAddress((String) o);
            } else if (propertyName.equals(RMIPort)) {
                setRMIPort((Integer) o);
            } else if (propertyName.equals(SQLServerDriver)) {
                // nicht änderbar
            } else if (propertyName.equals(SQLServerURL)) {
                setSQLServerURL((String) o);
            } else if (propertyName.equals(SQLServerIP)) {
                setSQLServerIP((String) o);
            } else if (propertyName.equals(SQLServerPort)) {
                setSQLServerPort((Integer) o);
            } else if (propertyName.equals(SQLPoolsize)) {
                setSQLPoolsize((Integer) o);
            } else if (propertyName.equals(SQLMaxPoolsize)) {
                setSQLMaxPoolsize((Integer) o);
            } else if (propertyName.equals(SQLPoolRequestMode)) {
                setSQLPoolRequestMode((String) o);
            } else if (propertyName.equals(SQLServerWatchdogActive)) {
                setSQLServerWatchdogActive((Boolean) o);
            } else if (propertyName.equals(SQLServerWatchdogInterval)) {
                setSQLServerWatchdogInterval((Long) o);
            } else if (propertyName.equals(SQLDeadlockTimeout)) {
                setSQLDeadlockTimeout((Long) o);
            } else if (propertyName.equals(SQLServerDB)) {
                setSQLServerDB((String) o);
            } else if (propertyName.equals(SQLServerUser)) {
                setSQLServerUser((String) o);
            } else if (propertyName.equals(SQLServerPW)) {
                setSQLServerPW((String) o);
            } else if (propertyName.equals(AutomaticIniWindowClose)) {
                setAutomaticIniWindowClose((Boolean) o);
            } else if (propertyName.equals(AskForServerClosing)) {
                setAskForServerClosing((Boolean) o);
            } else if (propertyName.equals(LookAndFeel)) {
                setLookAndFeel((SystemSettings.LookNFeel) o);
            } else if (propertyName.equals(SystemActionLogging)) {
                setSystemActionLogging((Boolean) o);
            } else if (propertyName.equals(SystemSQLLogging)) {
                setSystemSQLLogging((Boolean) o);
            }
        }
    }

    public String init() {
        StringBuilder sb = new StringBuilder();
        Properties loadedProps = this.load();
        if (loadedProps != null) {
            properties = loadedProps;
        } else {
            sb.append("Properties konnten nicht geladen werden, setze Default.");
        }
        String s = compare();
        if (s != null && !s.isEmpty()) {
            if (sb.length() > 0) {
                sb.append(System.getProperty("line.separator"));
            }
            sb.append(s);
        }
        propsToVars();
        return s.toString();
    }

    /**
     * allgemeine Systemproperties aus ini-File laden
     *
     * @return
     */
    private Properties load() {
        if (propsFileName == null || propsFileName.isEmpty()) {
            return null;
        }
        File file = new File(propsFileName);
        if (!file.exists()) {
            return null;
        }

        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(cryptKey, cryptAlgorythm);
            Cipher cipher = Cipher.getInstance(cryptAlgorythm);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            CipherInputStream cis = new CipherInputStream(fis, cipher);

            byte[] block = new byte[16];
            int i;
            while ((i = cis.read(block)) != -1) {
                bos.write(block, 0, i);
            }
            byte[] decrypted = bos.toByteArray();
            String[] decryptedStrings = new String(decrypted).split("&");

            Properties p = new Properties();
            i = 0;
            for (i = 0; i < decryptedStrings.length; i++) {
                String[] temp = decryptedStrings[i].split("=");
                Object o = p.setProperty(temp[0], temp[1]);
            }

            return p;
        } catch (Exception e) {
            return null;
        }
    }

    private void propsToVars() {
        automaticRMIStart = Boolean.parseBoolean(properties.getProperty(AutomaticRMIStart));
        automaticSQLStart = Boolean.parseBoolean(properties.getProperty(AutomaticSQLStart));
        applicationIcon = properties.getProperty(ApplicationIcon);
        rMIServerAddress = properties.getProperty(RMIServerAddress);
        rMIPort = Integer.parseInt(properties.getProperty(RMIPort));
        sQLServerDriver = properties.getProperty(SQLServerDriver);
        sQLServerURL = properties.getProperty(SQLServerURL);
        sQLServerIP = properties.getProperty(SQLServerIP);
        sQLServerPort = properties.getProperty(SQLServerPort);
        sQLPoolsize = Integer.parseInt(properties.getProperty(SQLPoolsize));
        sQLMaxPoolsize = Integer.parseInt(properties.getProperty(SQLMaxPoolsize));
        sQLPoolRequestMode = properties.getProperty(SQLPoolRequestMode);
        sQLServerWatchdogActive = Boolean.parseBoolean(properties.getProperty(SQLServerWatchdogActive));
        sQLServerWatchdogInterval = Long.parseLong(properties.getProperty(SQLServerWatchdogInterval));
        sQLDeadlockTimeout = Long.parseLong(properties.getProperty(SQLDeadlockTimeout));
        sQLServerDB = properties.getProperty(SQLServerDB);
        sQLServerUser = properties.getProperty(SQLServerUser);
        sQLServerPW = properties.getProperty(SQLServerPW);
        automaticIniWindowClose = Boolean.parseBoolean(properties.getProperty(AutomaticIniWindowClose));
        askForServerClosing = Boolean.parseBoolean(properties.getProperty(AskForServerClosing));
        try {
            lookAndFeel = SystemSettings.LookNFeel.valueOf(properties.getProperty(LookAndFeel));
        } catch (IllegalArgumentException e) {
            lookAndFeel = SystemSettings.LookNFeel.Windows;
        }
        lookAndFeel = SystemSettings.LookNFeel.valueOf(properties.getProperty(LookAndFeel));
        systemActionLogging = Boolean.parseBoolean(properties.getProperty(SystemActionLogging));
        systemSQLLogging = Boolean.parseBoolean(properties.getProperty(SystemSQLLogging));
    }

    /**
     * @return the automaticRMIStart
     */
    public boolean isAutomaticRMIStart() {
        return automaticRMIStart;
    }

    /**
     * @param automaticRMIStart the automaticRMIStart to set
     */
    public void setAutomaticRMIStart(boolean automaticRMIStart) {
        this.automaticRMIStart = automaticRMIStart;
        properties.setProperty(AutomaticRMIStart, Boolean.toString(automaticRMIStart));
    }

    /**
     * @return the automaticSQLStart
     */
    public boolean isAutomaticSQLStart() {
        return automaticSQLStart;
    }

    /**
     * @param automaticSQLStart the automaticSQLStart to set
     */
    public void setAutomaticSQLStart(boolean automaticSQLStart) {
        this.automaticSQLStart = automaticSQLStart;
        properties.setProperty(AutomaticSQLStart, Boolean.toString(automaticSQLStart));
    }

    /**
     * @param applicationIcon the applicationIcon to set
     */
    private void setApplicationIcon(String applicationIcon) {
        this.applicationIcon = applicationIcon;
    }

    /**
     * @param rMIServerAddress the rMIServerAddress to set
     */
    public void setRMIServerAddress(String rMIServerAddress) {
        if (rMIServerAddress == null || rMIServerAddress.isEmpty()) {
            return;
        }
        this.rMIServerAddress = rMIServerAddress;
        properties.setProperty(RMIServerAddress, rMIServerAddress);
    }

    /**
     * @param rMIPort the rMIPort to set
     */
    public void setRMIPort(int rMIPort) {
        //int portNumber;
        this.rMIPort = rMIPort;
        properties.setProperty(RMIPort, Integer.toString(rMIPort));
    }

    /**
     * @return the sQLServerDriver
     */
    public String getSQLServerDriver() {
        return sQLServerDriver;
    }

    /**
     * @param sQLServerDriver the sQLServerDriver to set
     */
    public void setSQLServerDriver(String sQLServerDriver) {
        if (sQLServerDriver == null || sQLServerDriver.isEmpty()) {
            return;
        }
        this.sQLServerDriver = sQLServerDriver;
        properties.setProperty(SQLServerDriver, sQLServerDriver);
    }

    /**
     * @return the sQLServerURL
     */
    public String getSQLServerURL() {
        return sQLServerURL;
    }

    /**
     * @param sQLServerURL the sQLServerURL to set
     */
    public void setSQLServerURL(String sQLServerURL) {
        if (sQLServerURL == null || sQLServerURL.isEmpty()) {
            return;
        }
        this.sQLServerURL = sQLServerURL;
        properties.setProperty(SQLServerURL, sQLServerURL);
    }

    /**
     * @return the sQLServerIP
     */
    public String getSQLServerIP() {
        return sQLServerIP;
    }

    /**
     * @param sQLServerIP the sQLServerIP to set
     */
    public void setSQLServerIP(String sQLServerIP) {
        if (sQLServerIP == null || sQLServerIP.isEmpty()) {
            return;
        }
        this.sQLServerIP = sQLServerIP;
        properties.setProperty(SQLServerIP, sQLServerIP);
    }

    /**
     * @return the sQLServerPort
     */
    public String getSQLServerPort() {
        return sQLServerPort;
    }

    /**
     * @param sQLServerPort the sQLServerPort to set
     */
    public void setSQLServerPort(int portNumber) {
        if (portNumber < 1024 || portNumber > 65535) {
            return;
        }
        sQLServerPort = Integer.toString(portNumber);
        properties.setProperty(SQLServerPort, sQLServerPort);
    }

    /**
     * @return the sQLPoolsize
     */
    public int getSQLPoolsize() {
        return sQLPoolsize;
    }

    /**
     * @param sQLPoolsize the sQLPoolsize to set
     */
    public void setSQLPoolsize(int sQLPoolsize) {
        if (sQLPoolsize < 0) {
            return;
        }
        this.sQLPoolsize = sQLPoolsize;
        properties.setProperty(SQLPoolsize, Integer.toString(sQLPoolsize));
    }

    /**
     * @return the sQLPoolsize
     */
    public int getSQLMaxPoolsize() {
        return sQLMaxPoolsize;
    }

    /**
     * @param sQLPoolsize the sQLPoolsize to set
     */
    public void setSQLMaxPoolsize(int sQLMaxPoolsize) {
        if (sQLMaxPoolsize < 0) {
            return;
        }
        this.sQLMaxPoolsize = sQLMaxPoolsize;
        properties.setProperty(SQLMaxPoolsize, Integer.toString(sQLMaxPoolsize));
    }

    /**
     * @return the sQLPoolRequestMode
     */
    public String getSQLPoolRequestMode() {
        return sQLPoolRequestMode;
    }

    /**
     * @param sQLPoolRequestMode the sQLPoolRequestMode to set
     */
    public void setSQLPoolRequestMode(String sQLPoolRequestMode) {
        this.sQLPoolRequestMode = sQLPoolRequestMode;
        properties.setProperty(SQLPoolRequestMode, sQLPoolRequestMode.toString());
    }

    /**
     * @return the sQLServerWatchdogActive
     */
    public boolean isSQLServerWatchdogActive() {
        return sQLServerWatchdogActive;
    }

    /**
     * @param sQLServerWatchdogActive the sQLServerWatchdogActive to set
     */
    public void setSQLServerWatchdogActive(boolean sQLServerWatchdogActive) {
        this.sQLServerWatchdogActive = sQLServerWatchdogActive;
        properties.setProperty(SQLServerWatchdogActive, Boolean.toString(sQLServerWatchdogActive));
    }

    /**
     * @return the sQLServerWatchdogInterval
     */
    public long getSQLServerWatchdogInterval() {
        return sQLServerWatchdogInterval;
    }

    /**
     * @return the sQLServerDB
     */
    public String getSQLServerDB() {
        return sQLServerDB;
    }

    /**
     * @param sQLServerDB the sQLServerDB to set
     */
    public void setSQLServerDB(String sQLServerDB) {
        if (sQLServerIP == null || sQLServerIP.isEmpty()) {
            return;
        }
        this.sQLServerDB = sQLServerDB;
        properties.setProperty(SQLServerDB, sQLServerDB);
    }

    /**
     * @return the sQLServerUser
     */
    public String getSQLServerUser() {
        return sQLServerUser;
    }

    /**
     * @param sQLServerUser the sQLServerUser to set
     */
    public void setSQLServerUser(String sQLServerUser) {
        if (sQLServerUser == null || sQLServerUser.isEmpty()) {
            return;
        }
        this.sQLServerUser = sQLServerUser;
        properties.setProperty(SQLServerUser, sQLServerUser);
    }

    /**
     * @return the sQLServerPW
     */
    public String getSQLServerPW() {
        return sQLServerPW;
    }

    /**
     * @param sQLServerPW the sQLServerPW to set
     */
    public void setSQLServerPW(String sQLServerPW) {
        if (sQLServerPW == null || sQLServerPW.isEmpty()) {
            return;
        }
        this.sQLServerPW = sQLServerPW;
        properties.setProperty(SQLServerPW, sQLServerPW);
    }

    /**
     * @return the automaticIniWindowClose
     */
    public boolean isAutomaticIniWindowClose() {
        return automaticIniWindowClose;
    }

    /**
     * @param automaticIniWindowClose the automaticIniWindowClose to set
     */
    public void setAutomaticIniWindowClose(boolean automaticIniWindowClose) {
        this.automaticIniWindowClose = automaticIniWindowClose;
        properties.setProperty(AutomaticIniWindowClose, Boolean.toString(automaticIniWindowClose));
    }

    /**
     * @return the askForServerClosing
     */
    public boolean isAskForServerClosing() {
        return askForServerClosing;
    }

    /**
     * @return the systemActionLogging
     */
    public boolean isSystemActionLogging() {
        return systemActionLogging;
    }

    /**
     * @return the systemSQLLogging
     */
    public boolean isSystemSQLLogging() {
        return systemSQLLogging;
    }

    /**
     * @param askForServerClosing the askForServerClosing to set
     */
    public void setAskForServerClosing(boolean askForServerClosing) {
        this.askForServerClosing = askForServerClosing;
        properties.setProperty(AskForServerClosing, Boolean.toString(askForServerClosing));
    }

    /**
     * @param sQLServerWatchdogInterval the sQLServerWatchdogInterval to set
     */
    public void setSQLServerWatchdogInterval(long sQLServerWatchdogInterval) {
        if (sQLServerWatchdogInterval < 0) {
            return;
        }
        this.sQLServerWatchdogInterval = sQLServerWatchdogInterval;
        properties.setProperty(SQLServerWatchdogInterval, Long.toString(sQLServerWatchdogInterval));
    }

    /**
     * @param sQLDeadlockTimeout the sQLDeadlockTimeout to set
     */
    public void setSQLDeadlockTimeout(long sQLDeadlockTimeout) {
        if (sQLDeadlockTimeout < 0) {
            return;
        }
        this.sQLDeadlockTimeout = sQLDeadlockTimeout;
        properties.setProperty(SQLDeadlockTimeout, Long.toString(sQLDeadlockTimeout));
    }

    /**
     * @param systemActionLogging the systemActionLogging to set
     */
    public void setSystemActionLogging(boolean systemActionLogging) {
        this.systemActionLogging = systemActionLogging;
        properties.setProperty(SystemActionLogging, Boolean.toString(systemActionLogging));
    }

    /**
     * @param systemSQLLogging the systemSQLLogging to set
     */
    public void setSystemSQLLogging(boolean systemSQLLogging) {
        this.systemSQLLogging = systemSQLLogging;
        properties.setProperty(SystemSQLLogging, Boolean.toString(systemSQLLogging));
    }

    /**
     * @param lookAndFeel the lookAndFeel to set
     */
    public void setLookAndFeel(String lookAndFeel) {
        this.lookAndFeel = SystemSettings.LookNFeel.valueOf(lookAndFeel);
        properties.setProperty(LookAndFeel, lookAndFeel);
    }

    /**
     * @param lookAndFeel the lookAndFeel to set
     */
    public void setLookAndFeel(SystemSettings.LookNFeel lookAndFeel) {
        this.lookAndFeel = lookAndFeel;
        properties.setProperty(LookAndFeel, this.lookAndFeel.toString());
    }

    public void store() throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, FileNotFoundException, IOException {
        if (properties == null || properties.isEmpty()) {
            return;
        }

        StringBuilder cryptBuffer = new StringBuilder();
        Enumeration<Object> propertyKeys = properties.keys();
        while (propertyKeys.hasMoreElements()) {
            String propertyKey = propertyKeys.nextElement().toString();
            if (0 != cryptBuffer.length()) {
                cryptBuffer.append("&");
            }
            cryptBuffer.append(propertyKey);
            cryptBuffer.append("=");
            cryptBuffer.append(properties.getProperty(propertyKey));
        }

        SecretKeySpec secretKeySpec = new SecretKeySpec(cryptKey, cryptAlgorythm);
        Cipher cipher = Cipher.getInstance(cryptAlgorythm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        ByteArrayInputStream bis = new ByteArrayInputStream(cryptBuffer.toString().getBytes());
        CipherInputStream cis = new CipherInputStream(bis, cipher);
        FileOutputStream fos = new FileOutputStream(new File(propsFileName));


        byte[] block = new byte[16];
        int i;
        while ((i = cis.read(block)) != -1) {
            fos.write(block, 0, i);
        }
        fos.close();

    }
}
