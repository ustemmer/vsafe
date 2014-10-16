package de.versatel.noc.vsafe.server.core;

import de.versatel.noc.vsafe.server.gui.GuiHandling;
import de.versatel.noc.vsafe.shared.core.exception.ExceptionCodes;
import de.versatel.noc.vsafe.shared.core.exception.JOptionPaneExceptionHandler;
import de.versatel.noc.vsafe.shared.core.exception.VSafeException;
import de.versatel.noc.vsafe.shared.core.settings.SystemProperties;
import de.versatel.noc.vsafe.shared.core.settings.SystemSettings;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author ulrich.stemmer
 */
public class CoreManagerImpl {

    public GuiHandling getGuiHandling() {
        return guiHandling;
    }

    public Image getIconImage(String iconFileName) {
        Image image;
        File file;

        if (iconFileName == null || iconFileName.isEmpty()) {
            return null;
        }

        try {
            file = new File(iconFileName);
            image = ImageIO.read(file);
            return image;
        } catch (NullPointerException e) {
            handle(new VSafeException("SystemManager.getIconImage", ExceptionCodes.GENERAL_NULLPOINTEREXCEPTION, "Kann Datei nicht lesen.", e));
        } catch (IllegalArgumentException e) {
            handle(new VSafeException("SystemManager.getIconImage", ExceptionCodes.GENERAL_ILLEGALARGUMENTREXCEPTION, "Falsches Argument.", e));
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Fehler !", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            handle(new VSafeException("SystemManager.getIconImage", ExceptionCodes.GENERAL_IOEXCEPTION, "Kann Datei nicht lesen.", e));
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Fehler !", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            handle(new VSafeException("SystemManager.getIconImage", ExceptionCodes.GENERAL_NULLPOINTEREXCEPTION, "Kann Datei nicht lesen.", e));
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Fehler !", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public MySQL_PoolHandling getMySQL_PoolHandling() {
        return mySQL_PoolHandling;
    }

    public String getNow() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formater = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return formater.format(cal.getTime());
    }

    public RMIServerHandling getRMIServerHandling() {
        return rMIServerHandling;
    }

    public ServiceHandling getServiceHandling() {
        return serviceHandling;
    }

    public SystemExceptionHandler getSystemExceptionHandler() {
        return systemExceptionHandler;
    }

    public SystemLogging getSystemLogging() {
        return systemLogging;
    }

    public SystemSettings getSystemSettings() {
        return systemSettings;
    }

    public SystemProperties getSystemProperties() {
        return systemProperties;
    }

    public ExceptionCodes getExceptionCodes() {
        return exceptionCodes;
    }

    public SystemUncaughtExceptionHandler getSystemUncaughtExceptionHandler() {
        return systemUncaughtExceptionHandler;
    }

    public UserHandling getUserHandling() {
        return userHandling;
    }

    public void handle(Throwable t) {
        exceptionHandling.handle(t);
    }

    public void handle(Throwable t, String errorMessage) {
        exceptionHandling.handle(t, errorMessage);
    }

    public void handle(Throwable t, String errorMessage, String errorContext, int exceptionCode, String errorText) {
        exceptionHandling.handle(t, errorMessage, errorContext, exceptionCode, errorText);
    }

    public void raise(String errorContext, int exceptionCode, String errorText) {
        exceptionHandling.raise(errorContext, exceptionCode, errorText);
    }

    public void setGuiHandler(GuiHandling guiHandling) {
        this.guiHandling = guiHandling;
    }

    public void setMySQL_PoolHandling(MySQL_PoolHandling mySQL_PoolHandling) {
        this.mySQL_PoolHandling = mySQL_PoolHandling;
    }

    public void setRMIServerHandling(RMIServerHandling rMIServerHandling) {
        this.rMIServerHandling = rMIServerHandling;
    }

    public void setServiceHandling(ServiceHandling serviceHandling) {
        this.serviceHandling = serviceHandling;
    }

    public void setSystemLogging(SystemLogging systemLogging) {
        this.systemLogging = systemLogging;
    }

    public void setExceptionCodes(ExceptionCodes exceptionCodes) {
        this.exceptionCodes = exceptionCodes;
    }

    public void setSystemProperties(SystemProperties systemProperties) {
        this.systemProperties = systemProperties;
    }

    public void setSystemSettings(SystemSettings systemSettings) {
        this.systemSettings = systemSettings;
    }

    public void setUserHandling(UserHandling userHandling) {
        this.userHandling = userHandling;
    }

    public void shutdown() {
        try {
            getSystemProperties().store();
        } catch (Exception e) {
        }
        System.exit(0);
    }

    public void systemStart() {
        try {
            guiHandling = new GuiHandling(this);
            guiHandling.setApplicationIcon(systemProperties.getApplicationIcon());
            guiHandling.setCurrentFrame(guiHandling.getFrameStart());
            exceptionHandling.add(new JOptionPaneExceptionHandler(guiHandling.getFrameStart().getContentPane()));

            guiHandling.setStartMessage("SQL-Pool wird erzeugt.");
            mySQL_PoolHandling = new MySQL_PoolHandling(this);

            guiHandling.setStartMessage("SQL-Pool wird initialisiert.");
            mySQL_PoolHandling.init(getSystemProperties().getSQLServerDriver(),
                    getSystemProperties().getSQLServerURL(),
                    getSystemProperties().getSQLPoolsize(),
                    getSystemProperties().getSQLMaxPoolsize(),
                    MySQL_PoolHandling.PoolRequestMode.valueOf(getSystemProperties().getSQLPoolRequestMode()),
                    getSystemProperties().getSQLServerUser(),
                    getSystemProperties().getSQLServerPW(),
                    getSystemProperties().isSQLServerWatchdogActive(),
                    getSystemProperties().isAutomaticSQLStart());
            if (getSystemProperties().isAutomaticSQLStart()) {
                mySQL_PoolHandling.connect();
            }

            // neuer RMI-Server
            guiHandling.setStartMessage("Server wird initialisiert.");
            rMIServerHandling = new RMIServerHandling(this);

            // Dienste laden
            guiHandling.setStartMessage("Dienste werden initialisiert.");
            serviceHandling = new ServiceHandling(this);


            // TODO neues UserHandling
            guiHandling.setStartMessage("Benutzerumgebung wird geladen.");
            userHandling = new UserHandling(this);
            userHandling.init();

            if (getSystemProperties().isAutomaticRMIStart()) {
                guiHandling.setStartMessage("Server wird gestartet.");
                rMIServerHandling.start();
            }

            // Übergabe ans Hauptfenster
            guiHandling.startingFinished();
        } catch (VSafeException vse) {
            handle(vse);
        }
    }
}
