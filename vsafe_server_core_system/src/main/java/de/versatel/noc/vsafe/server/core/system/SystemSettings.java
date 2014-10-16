package de.versatel.noc.vsafe.server.core.system;

/**
 *
 * @author ulrich.stemmer
 */
public class SystemSettings {

    // Enumerations
    public static enum LookNFeel {

        Windows, Java
    };

    // Application
    public static final String APPLICATION_NAME = "vSafe-Server";
    public static final String APPLICATION_VERSION = "1.00";
    public static final String APPLICATION_TITLE = APPLICATION_NAME + ", Version " + APPLICATION_VERSION;
    // ErrorMessages
    public static final String ERRORMESSAGE_PROPERTYFILENAME = "resources/errorinfos.properties";
    // Frames
    public static final String IFRAME_USERS = APPLICATION_TITLE + ": Benutzerverwaltung";
    // Properties
    public static final String SYSTEMPROPERTY_FILENAME = APPLICATION_NAME + ".properties";
    public static final String SYSTEMLOGGING_PROPERTYFILENAME = "resources/log4j.properties";
    public static final String SYSTEMLOGGING_ERRORFILENAME = "logs/" + APPLICATION_NAME + "-error.log";
    public static final String SYSTEMLOGGING_ACTIONFILENAME = "logs/" + APPLICATION_NAME + "-protocol.log";
    public static final String SYSTEMPROPERTY_CRYPTALGORYTHM = "AES";
    public static final int SYSTEMPROPERTY_CRYPTKEYLENGTH = 128;
    public static final String SYSTEMPROPERTY_CRYPTKEY_256 = "9670C02AFB9A8D190F37DE1E575BB88AB2063C9A5B06CCE91009B2B7483D49DC";
    // 128Bit/16Byte String
    // 26 6F 82 E1
    // 60 0A A4 05
    // EB 92 42 7E 
    // 9A DB 05 7B
    //public static final String SYSTEMPROPERTY_CRYPTKEY_128 = "266F82E1600AA405EB92427E9ADB057B";
    public static byte[] SYSTEMPROPERTY_CRYPTKEY_16BYTE = {
        (byte) 0x26, (byte) 0x6f, (byte) 0x82, (byte) 0xe1,
        (byte) 0x60, (byte) 0x0a, (byte) 0xa4, (byte) 0x05,
        (byte) 0xeb, (byte) 0x92, (byte) 0x42, (byte) 0x7e,
        (byte) 0x9a, (byte) 0xdb, (byte) 0x05, (byte) 0x7b
    };
    // Users
    public static final int USER_DEFAULTPASSWORDLENGTH = 256;
    public static final int PERMISSION_ARRAYSIZE = 256;
    public static final int PERMISSION_HEXSTRINGLENGTH = PERMISSION_ARRAYSIZE / 4;
    // Services
    public static final long SERIALVERSIONUID = 1999L;
    public static final long SERVICEID_USER = 1L;
    public static final long SERVICEID_SYSTEM = 2L;
    public static final long SERVICEID_RMI = 3L;
    public static final long SERVICEID_MYSQL = 4L;
    public static final String SERVICENAME_USER = "Benutzer";
    public static final String SERVICENAME_SYSTEM = "System";
    public static final String SERVICENAME_RMI = "RMI";
    public static final String SERVICENAME_MYSQL = "Datenbank";
    // RMI
    public static final String RMI_URL = "vsafe_server";
    public static final int RMI_PORT_MIN = 1024;
    public static final int RMI_PORT_MAX = 49151;
    public static final long RMI_UID = 2889356266L;
    // System
    public static final String SYSTEM_LINESEPERATOR = System.getProperty("line.separator");

    public SystemSettings() {
    }
}
