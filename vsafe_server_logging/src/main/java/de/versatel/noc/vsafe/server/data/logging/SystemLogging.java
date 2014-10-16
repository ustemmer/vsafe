package de.versatel.noc.vsafe.server.data.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.text.SimpleDateFormat;

//import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author ulrich.stemmer
 */
public class SystemLogging {

    //neu
    final Logger exceptionLogger = Logger.getLogger("errorLogger");
    final Logger consoleLogger = Logger.getLogger("consoleLogger");
    final Logger actionLogger = Logger.getLogger("actionLogger");
    final Logger rootLogger = Logger.getRootLogger();
    //LoggingOutputStream los;
    // alt
    private File errorFile;
    FileWriter errorWriter;
    File logFile;
    FileWriter logWriter;
    private String lineSeperator;

    /**
     *
     * @param lineSeperator
     */
    public SystemLogging(String lineSeperator) {
//        this.stdout = System.out;
//        this.stderr = System.err;
        this.lineSeperator = lineSeperator;

        // make sure everything sent to System.err is logged
//        System.setErr(new PrintStream(new LoggingOutputStream(rootLogger,Level.WARN), true));

        // make sure everything sent to System.out is also logged
//        System.setOut(new PrintStream(new LoggingOutputStream(rootLogger,Level.INFO), true));
        //LogManager lm = new LogManager();

    }

    /**
     *
     * @param actionNumber
     * @param userName
     * @param workStation
     * @param title
     * @param attributes
     * @param oldValues
     * @param newValues
     */
    public void log(int actionNumber, String userName, String workStation, String title,
            List<String> attributes, List<String> oldValues, List<String> newValues) {
        LogThread logThread = new LogThread();
        logThread.init(actionNumber, userName, workStation, title,
                attributes, oldValues, newValues);
    }

    /**
     *
     * @param exception
     */
    public void log(Exception exception) {
        LogThread logThread = new LogThread();
        logThread.init(exception);
    }

    /*private synchronized void logAction(int actionNumber, String userName, String workStation, String title,
            List<String> attributes, List<String> oldValues, List<String> newValues) {
        if (openActionFile()) {
            GregorianCalendar cal = new GregorianCalendar();
            Date date = cal.getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

            StringBuilder row = new StringBuilder();
            row.append(simpleDateFormat.format(date));
            row.append(";");
            row.append(userName);
            row.append(";");
            row.append(workStation);
            row.append(";");

            switch (actionNumber) {
                case USER_LOGIN:
                    row.append("Benutzer '");
                    row.append(title);
                    row.append("': eingeloggt.");
                    break;
                case USER_LOGOUT:
                    row.append("Benutzer '");
                    row.append(title);
                    row.append("': ausgeloggt.");
                    break;
                case USER_ADDED:
                    row.append("Benutzer '");
                    row.append(title);
                    row.append("': hinzugefügt.");
                case USER_DELETED:
                    row.append("Benutzer '");
                    row.append(title);
                    row.append("': entfernt.");
                case USER_CHANGED:
                    row.append("Benutzer '");
                    row.append(title);
                    row.append("': Werte geändert ");
                    if (attributes == null || oldValues == null || newValues == null
                            || attributes.isEmpty() || oldValues.isEmpty() || newValues.isEmpty()
                            || attributes.size() != oldValues.size()
                            || attributes.size() != newValues.size()) {
                        row.append("sind fehlerhaft.");
                        break;
                    }
                    for (int i = 0; i < attributes.size(); i++) {
                        if (i > 0) {
                            row.append(", ");
                        }
                        row.append("('");
                        row.append(attributes.get(i));
                        row.append("', alt:'");
                        row.append(oldValues.get(i));
                        row.append("', neu:'");
                        row.append(newValues.get(i));
                        row.append("')");
                    }
                case DB_SELECT:
                case DB_INSERT:
                case DB_UPDATE:
                case DB_DROP:
            }
            row.append(lineSeperator);
            try {
                logWriter.write(row.toString());
            } catch (IOException e) {
                logException(e);
            }
        }
        //DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    }*/

    private void logException(Exception e) {
        if (openExceptionFile()) {
            GregorianCalendar cal = new GregorianCalendar();
            Date date = cal.getTime();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

            //Zeile zusammenbausen und ins Log schreiben.
            StringBuilder row = new StringBuilder();
            row.append(simpleDateFormat.format(date));
            row.append(";");
            row.append(e.toString());
            row.append(lineSeperator);
            try {
                errorWriter.write(row.toString());
            } catch (IOException ioe) {
            }


        }
    }

    private synchronized boolean openActionFile() {
        GregorianCalendar cal = new GregorianCalendar();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);

        // Dateiname erzeugen
        StringBuilder filename = new StringBuilder();
        filename.append(year);
        filename.append("-");
        filename.append(month);
        filename.append(".log");

        // Datei prüfen
        if (logFile == null) {
            logFile = new File(filename.toString());
        } else if (!logFile.getName().equals(filename.toString())) {
            if (logWriter != null) {
                try {

                    logWriter.close();
                } catch (IOException e) {
                    return false;
                }
            }
            logFile = new File(filename.toString());
            try {
                logWriter = new FileWriter(logFile, true);
            } catch (IOException e) {
                return false;
            }

        } else if (logWriter == null) {
            try {
                logWriter = new FileWriter(logFile, true);
            } catch (IOException e) {
                return false;
            }
        }
        return true;

    }

    private synchronized boolean openExceptionFile() {
        GregorianCalendar cal = new GregorianCalendar();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);

        // Dateiname erzeugen
        StringBuilder filename = new StringBuilder();
        filename.append(year);
        filename.append("-");
        filename.append(month);
        filename.append("exception.log");

        // Datei prüfen
        if (errorFile == null) {
            errorFile = new File(filename.toString());
        } else if (!errorFile.getName().equals(filename.toString())) {
            if (errorWriter != null) {
                try {

                    errorWriter.close();
                } catch (IOException e) {
                    return false;
                }
            }
            errorFile = new File(filename.toString());
            try {
                errorWriter = new FileWriter(errorFile, true);
            } catch (IOException e) {
                return false;
            }

        } else if (errorWriter == null) {
            try {
                errorWriter = new FileWriter(errorFile, true);
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    private class LogThread implements Runnable {

        Thread thread;
        String threadname = "Logger";
        int actionNumber;
        String userName;
        String workStation;
        String title;
        List<String> attributes;
        List<String> oldValues;
        List<String> newValues;
        Exception exception;

        public LogThread() {
            thread = new Thread(this, threadname);
        }

        public void run() {
            //logAction(actionNumber, userName, workStation, title, attributes, oldValues, newValues);
        }

        private void init(int actionNumber, String userName, String workStation, String title,
                List<String> attributes, List<String> oldValues, List<String> newValues) {
            this.actionNumber = actionNumber;
            this.userName = userName;
            this.workStation = workStation;
            this.title = title;
            this.attributes = attributes;
            this.oldValues = oldValues;
            this.newValues = newValues;
            this.thread.start();
        }

        private void init(Exception exception) {
            this.exception = exception;
            this.thread.start();
        }
    }
}
