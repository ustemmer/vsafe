package de.versatel.noc.vsafe.server.exception;

//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
import de.versatel.noc.vsafe.common.exception.ExceptionLevel;
import java.util.ArrayList;
import java.util.List;
//import java.util.Properties;

/**
 *
 * @author ulrich.stemmer
 */
public class ExceptionCodes {
    // Allgemein
    public static final int GENERAL_NOTHING_TO_CHANGE = 1;
    public static final int GENERAL_TOOLOW = 96;
    public static final int GENERAL_TOOHIGH = 97;
    public static final int GENERAL_NULLPOINTEREXCEPTION = 98;
    public static final int GENERAL_UNDEFINED_ERROR = 99;
    
    
    // Benutzerverwaltung
    public static final int USERGROUP_NOUSERGROUP = 100;
    public static final int USERGROUP_WRONGGROUPNAME = 101;
    public static final int USERGROUP_WRONGGROUPID = 102;
    public static final int USERGROUP_NOTADDED = 103;
    public static final int USER_NEWUSER = 120;
    public static final int USER_LOGGEDIN = 121;
    public static final int USER_LOGGEDOUT = 122;
    public static final int USER_WRONGPASSWORD = 123;
    public static final int USER_MAXWRONGPASSWORDS = 124;
    public static final int USER_USERADMINBARRED = 125;
    public static final int USER_USERSYSTEMBARRED = 126;
    public static final int USER_SESSIONLIMIT = 127;
    public static final int USER_NOUSER = 128;
    public static final int USER_LOGOUTERROR = 129;
    public static final int USER_WRONGUSERNAME = 130;
    public static final int USER_MISSINGDATA = 131;
    public static final int USER_WRONGGROUPID = 132;
    public static final int USER_WRONGUSERID = 133;
    public static final int USER_WRONGVALUE_MAXSESSIONS = 134;
    public static final int USER_WRONGVALUE_MAXWRONGLOGINS = 135;
    public static final int USER_WRONGVALUE_SESSIONLIFETIME = 136;
    public static final int USER_WRONGVALUE_SESSIONINACTIVITYTIMEOUT = 137;
    public static final int USER_WRONGGROUPNAME = 138;
    
    public static final int SESSION_WRONGSESSIONID = 140;
    public static final int SESSION_SESSIONIDNOTFOUND = 141;
    public static final int SESSION_HASEXPIRED = 142;
    public static final int SESSION_WRONGHOSTORIP = 143;
    public static final int SERVICE_WRONGSERVICE = 160;
    public static final int SERVICE_METHODNOTPERMITTED = 161;
    public static final int SERVICE_WRONGREQUESTOBJECT = 162;

    // Datenbank
    public static final int DB_WRONGSQLCOMMAND = 200;
    public static final int DB_DBMISMATCH = 201;
    public static final int DB_NOCONNECTION = 202;

    // RMI
    public static final int RMI_WRONGREQUESTOBJECT = 300;
    public static final int RMI_WRONGURL = 301;
    public static final int RMI_REMOTEEXCEPTION = 302;
    public static final int RMI_NOACCESSS = 303;
    public static final int RMI_NOTBOUND = 304;

    // MySQL
    public static final int MYSQL_WATCHDOG_ACTIVATED = 400;
    public static final int MYSQL_WATCHDOG_ALREADYACTIVATED = 401;
    public static final int MYSQL_WATCHDOG_DEACTIVATED = 402;
    public static final int MYSQL_WATCHDOG_ALREADYDEACTIVATED = 403;
    public static final int MYSQL_POOL_EMPTYDRIVER = 410;
    public static final int MYSQL_POOL_EMPTYSERVERURL = 411;
    public static final int MYSQL_POOL_POOLSIZEUNDERSIZED = 412;
    public static final int MYSQL_POOL_MAXPOOLSIZEUNDERSIZED = 413;
    public static final int MYSQL_POOL_POOLSIZEOVERSIZED = 414;
    public static final int MYSQL_POOL_UNKNOWNMODE = 415;
    public static final int MYSQL_POOL_EMPTYUSERNAME = 416;
    public static final int MYSQL_POOL_EMPTYPASSWORD = 417;
    public static final int MYSQL_POOL_EMPTYDRIVERSERVERURL = 418;
    public static final int MYSQL_POOL_POOLSIZEMISMATCH = 419;
    public static final int MYSQL_POOL_DRIVERNOTFOUND = 420;

    private List<ExceptionCode> ExceptionCodes;
    private final String lineSeparator = System.getProperty("line.separator");

    public ExceptionCodes() {

        ExceptionCodes = new ArrayList<ExceptionCode>();
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, GENERAL_NOTHING_TO_CHANGE, "GENERAL_NOTHING_TO_CHANGE", "Hier gibt es nichts zu ändern."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.WARNING, GENERAL_UNDEFINED_ERROR, "GENERAL_UNDEFINED_ERROR", "Undefinierter Fehler."));

        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.WARNING, USERGROUP_NOUSERGROUP, "USERGROUP_NOUSERGROUP", "Du bist keiner Benutzergruppe zugeordnet."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.WARNING, USERGROUP_WRONGGROUPNAME, "USERGROUP_WRONGGROUPNAME", "Falscher/Fehlender Gruppenname."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.WARNING, USERGROUP_WRONGGROUPID, "USERGROUP_WRONGGROUPID", "Falscher/Fehlender Gruppenindex."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.WARNING, USERGROUP_NOTADDED, "USERGROUP_NOTADDED", "Gruppe konnte nicht hinzugefügt werden."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, USER_NEWUSER, "USER_NEWUSER",
                "Du loggst Dich zum ersten Mal ein."
                + lineSeparator
                + "Bitte ändere Dein vorläufiges Passwort"));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, USER_LOGGEDIN, "USER_LOGGEDIN", "Du wurdest erfolgreich eingeloggt."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, USER_LOGGEDOUT, "USER_LOGGEDOUT", "Du wurdest erfolgreich ausgeloggt"));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.WARNING, USER_WRONGPASSWORD, "USER_WRONGPASSWORD", "Das Passwort ist falsch."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.WARNING, USER_MAXWRONGPASSWORDS, "USER_MAXWRONGPASSWORDS", "Dein Konto wurde gesperrt (Passwortlimit)"));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, USER_USERADMINBARRED, "USER_USERADMINBARRED", "Dein Konto wurde vom Administrator gesperrt."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, USER_USERSYSTEMBARRED, "USER_USERSYSTEMBARRED", "Dein Konto wurde vom System gesperrt."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, USER_SESSIONLIMIT, "USER_SESSIONLIMIT", "Du bist schon mehrmals eingeloggt, Limit erreicht."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, USER_NOUSER, "USER_NOUSER", "Der Benutzer existiert nicht."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, USER_LOGOUTERROR, "USER_LOGOUTERROR", "Fehler beim Ausloggen."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, USER_WRONGUSERNAME, "USER_WRONGUSERNAME", "Falscher/Fehlender Benutzername."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, USER_MISSINGDATA, "USER_MISSINGDATA", "Fehlende Benutzerdaten."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, USER_WRONGGROUPID, "USER_WRONGGROUPID", "Falscher/Fehlender Benutzergruppenindex."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, USER_WRONGUSERID, "USER_WRONGUSERID", "Falscher/Fehlender Benutzerindex."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, USER_WRONGVALUE_MAXSESSIONS, "USER_WRONGVALUE_MAXSESSIONS", "Falscher Wert (Maximale Sessions)."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, USER_WRONGVALUE_MAXWRONGLOGINS, "USER_WRONGVALUE_MAXWRONGLOGINS", "Falscher Wert (Fehlerhafte Einwahlen)."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, USER_WRONGVALUE_SESSIONLIFETIME, "USER_WRONGVALUE_SESSIONLIFETIME", "Falscher Wert (SessionLebensdauer)."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, USER_WRONGVALUE_SESSIONINACTIVITYTIMEOUT, "USER_WRONGVALUE_SESSIONINACTIVITYTIMEOUT", "Falscher Wert (InaktivTimeout)."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, USER_WRONGGROUPNAME, "USER_WRONGGROUPNAME", "Falscher/Fehlender Gruppenname."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, SESSION_WRONGSESSIONID, "SESSION_WRONGSESSIONID"));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, SESSION_SESSIONIDNOTFOUND, "SESSION_SESSIONIDNOTFOUND"));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, SESSION_HASEXPIRED, "SESSION_HASEXPIRED"));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, SESSION_WRONGHOSTORIP, "SESSION_WRONG_HOST_OR_IP"));

        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, SERVICE_WRONGSERVICE, "SERVICE_WRONGSERVICE"));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, SERVICE_METHODNOTPERMITTED, "SERVICE_METHODNOTPERMITTED"));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, SERVICE_WRONGREQUESTOBJECT, "SERVICE_WRONGREQUESTOBJECT"));

        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, DB_WRONGSQLCOMMAND, "DB_WRONGSQLCOMMAND", "Datenbankfehler: Der Befehl konnte nicht ausgeführt werden.(DB)"));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, DB_DBMISMATCH, "DB_DBMISMATCH", "Allgemeiner Datenbankfehler."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, DB_NOCONNECTION, "DB_NOCONNECTION", "Datenbankfehler: Keine Verbindung."));

        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, MYSQL_WATCHDOG_ACTIVATED, "MYSQL_WATCHDOG_ACTIVATED", ""));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, MYSQL_WATCHDOG_ALREADYACTIVATED, "MYSQL_WATCHDOG_ALREADYACTIVATED", ""));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, MYSQL_WATCHDOG_DEACTIVATED, "MYSQL_WATCHDOG_DEACTIVATED", ""));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, MYSQL_WATCHDOG_ALREADYDEACTIVATED, "MYSQL_WATCHDOG_ALREADYDEACTIVATED", ""));

        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, MYSQL_POOL_EMPTYDRIVER, "MYSQL_POOL_EMPTYDRIVER", "Treibername ist leer  setze Default."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, MYSQL_POOL_EMPTYSERVERURL, "MYSQL_POOL_EMPTYSERVERURL", "ServerURL ist leer  setze Default."));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, MYSQL_POOL_POOLSIZEUNDERSIZED, "MYSQL_POOL_POOLSIZEUNDERSIZED", "Poolgroeße darf nicht kleiner als '0' sein!"));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, MYSQL_POOL_MAXPOOLSIZEUNDERSIZED, "MYSQL_POOL_MAXPOOLSIZEUNDERSIZED", "Maximale Poolgroeße darf nicht kleiner als '0' sein!"));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, MYSQL_POOL_POOLSIZEOVERSIZED, "MYSQL_POOL_POOLSIZEOVERSIZED", "Poolgroeße darf nicht groeßer der maximalen Poolgroeße sein!"));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, MYSQL_POOL_UNKNOWNMODE, "MYSQL_POOL_UNKNOWNMODE", "Unbekannter Modus!"));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, MYSQL_POOL_EMPTYUSERNAME, "MYSQL_POOL_EMPTYUSERNAME", "MySQLBenutzername ist leer!"));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, MYSQL_POOL_EMPTYPASSWORD, "MYSQL_POOL_EMPTYPASSWORD", "MySQLPasswort ist leer!"));
        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, MYSQL_POOL_EMPTYDRIVERSERVERURL, "MYSQL_POOL_EMPTYDRIVERSERVERURL", "Treiber und ServerURL wurden auf Default gesetzt.!"));

        ExceptionCodes.add(new ExceptionCode(ExceptionLevel.INFO, RMI_WRONGREQUESTOBJECT, "RMI_WRONGREQUESTOBJECT","Ungueltiges RemoteObjekt."));

    }

    public String getName(int index) {
        if (index < 0) {
            return null;
        }
        for (ExceptionCode em : ExceptionCodes) {
            if (em.getIndex() == index) {
                return em.getName();
            }
        }
        return null;
    }

    public String getInfo(int index) {
        if (index < 0) {
            return null;
        }
        for (ExceptionCode em : ExceptionCodes) {
            if (em.getIndex() == index) {
                return em.getInfo();
            }
        }
        return null;
    }

    public ExceptionCode getExceptionCode(int index) {
        if (index < 0) {
            return null;
        }
        for (ExceptionCode em : ExceptionCodes) {
            if (em.getIndex() == index) {
                return em;
            }
        }
        return null;
    }

    public void setInfo(int index, String info) {
        if (index < 0 || info == null || info.isEmpty()) {
            return;
        }
        for (ExceptionCode em : ExceptionCodes) {
            if (em.getIndex() == index) {
                em.setInfo(info);
                return;
            }
        }
    }

    /*public void store() {
        if (ExceptionCodes.isEmpty()) {
            return;
        }
        Properties properties = new Properties();
        for (ExceptionCode em : ExceptionCodes) {
            String value = em.getInfo().replaceAll(lineSeparator, "|");
            properties.put(em.getName(), value);
        }
        try {
            //SystemSettings ss = new SystemSettings();
            FileOutputStream fos = new FileOutputStream(SystemSettings.ERRORMESSAGE_PROPERTYFILENAME);
            properties.store(fos, "");
            fos.close();
        } catch (IOException e) {
        }
    }*/

    /*public void load() {
        if (ExceptionCodes.isEmpty()) {
            return;
        }
        Properties properties = new Properties();
        try {
            //SystemSettings ss = new SystemSettings();
            FileInputStream fis = new FileInputStream(SystemSettings.ERRORMESSAGE_PROPERTYFILENAME);
            properties.load(fis);
            fis.close();
            for (ExceptionCode em : ExceptionCodes) {
                String value = properties.getProperty(em.getName());
                em.setInfo(value.replaceAll("|", lineSeparator));
            }
        } catch (IOException e) {
        }
    }*/

    public class ExceptionCode{
        protected ExceptionLevel level;
        protected int index;
        protected String name;
        protected String info;
        

        public ExceptionCode(ExceptionLevel level, int index, String name) {
            this.level=level;
            this.index = index;
            this.name = name;
        }

        public ExceptionCode(ExceptionLevel level,int index, String name, String info) {
            this.level = level;
            this.index = index;
            this.name = name;
            this.info = info;
        }

        public String getDetailedMessage() {
            StringBuilder sb = new StringBuilder();
            sb.append("Fehler: ");
            sb.append(name);
            sb.append("(");
            sb.append(index);
            sb.append(")");
            sb.append(lineSeparator);
            sb.append("Info: ");
            if (info != null) {
                sb.append(info);
            }
            return sb.toString();
        }

        public String getInfo() {
            return info;
        }

        public int getIndex() {
            return index;
        }

        public String getName() {
            return name;
        }

        public void setInfo(String info) {
            if (info == null) {
                return;
            }
            this.info = info;
        }
    }
}
