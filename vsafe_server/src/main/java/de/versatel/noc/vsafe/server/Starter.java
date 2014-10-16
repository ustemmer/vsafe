package de.versatel.noc.vsafe.server;

import de.versatel.noc.vsafe.server.core.system.SystemManagerImpl;
import de.versatel.noc.vsafe.shared.core.settings.SystemSettings;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author ulrich.stemmer
 */
public class Starter {

    /**
     * @param args
     */
    public static void main(String[] args) {
        PropertyConfigurator.configure(SystemSettings.SYSTEMLOGGING_PROPERTYFILENAME);
        Logger logger = Logger.getLogger("NotificationLogger");
        
        // Java SDK-Version prüfen
        logger.info("Starte Applikation.");
        checkJDK(logger);

        // Look and Feel OS-abhängig setzen
        setLookNFeel(logger);

        // Applikation starten
        SystemManagerImpl systemManager = new SystemManagerImpl();
        systemManager.systemStart();
    }

    private static void checkJDK(Logger logger) {
        String sVersion = java.lang.System.getProperties().getProperty("java.version");
        Float f = Float.valueOf(sVersion.substring(0, 3));
        if (f.floatValue() < (float) 1.5) {
            String s = "Java-Version zu niedrig:" + f.floatValue() + ", erwarte mind. 1.5";
            JOptionPane.showMessageDialog(new JFrame(), s , "Fehler !", JOptionPane.ERROR_MESSAGE);
            logger.info("Beende Applikation (" + s + ")");
            System.exit(1);
        }

    }

    private static void setLookNFeel(Logger logger) {
        if (isWindows()) {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } catch (Exception e) {
                String s = "Windows Look&Feel konnte nicht gesetzt werden, setze Default";
                JOptionPane.showMessageDialog(new JFrame(), s , "Fehler !", JOptionPane.ERROR_MESSAGE);
                logger.info("Beende Applikation (" + s + ")");
            }
        } else if (isMac() || isUnix()) {
        } else {
            String s = "Diese Betriebssystem wird nicht unterstützt";
            JOptionPane.showMessageDialog(new JFrame(), s, "Fehler !", JOptionPane.ERROR_MESSAGE);
            logger.info("Beende Applikation (" + s + ")");
            System.exit(1);
        }

    }

    private static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        /* Windows */
        return (os.indexOf("win") >= 0);
    }

    private static boolean isMac() {
        String os = System.getProperty("os.name").toLowerCase();
        /* Mac */
        return (os.indexOf("mac") >= 0);
    }

    private static boolean isUnix() {
        String os = System.getProperty("os.name").toLowerCase();
        /* Linux or Unix */
        return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);
    }
}
