package de.versatel.noc.vsafe.client;

//import de.versatel.noc.vsafe.client.core.SystemManager;

import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author ulrich.stemmer
 */
public class Starter extends JApplet {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // Java SDK-Version prüfen
        checkJDK();

        // Look and Feel OS-abhängig setzen
        setLookNFeel();

        // Applikation starten
        //SystemManager systemManager = new SystemManager();
        //systemManager.systemStart();
    }

    @Override
    public void init() {
    }

    private static void checkJDK() {
        String sVersion = java.lang.System.getProperties().getProperty("java.version");
        Float f = Float.valueOf(sVersion.substring(0, 3));
        if (f.floatValue() < (float) 1.5) {
            String s = "Java-Version zu niedrig:" + f.floatValue() + ", erwarte mind. 1.5";
            JOptionPane.showMessageDialog(new JFrame(), s, "Fehler !", JOptionPane.ERROR_MESSAGE);
            System.out.println("Beende Applikation (" + s + ")");
            System.exit(1);
        }

    }

    private static void setLookNFeel() {
        if (isWindows()) {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } catch (Exception e) {
                String s = "Windows Look&Feel konnte nicht gesetzt werden, nehme Default";
                JOptionPane.showMessageDialog(new JFrame(), s, "Fehler !", JOptionPane.ERROR_MESSAGE);
            }
        } else if (isMac() || isUnix()) {
        } else {
            String s = "Diese Betriebssystem wird nicht unterstützt";
            JOptionPane.showMessageDialog(new JFrame(), s, "Fehler !", JOptionPane.ERROR_MESSAGE);
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
