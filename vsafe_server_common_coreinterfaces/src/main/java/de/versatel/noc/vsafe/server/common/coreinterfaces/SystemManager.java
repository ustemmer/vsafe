package de.versatel.noc.vsafe.server.common.coreinterfaces;

/**
 *
 * @author ulrich.stemmer
 */
public interface SystemManager {
    public SystemSettings getSystemSettings();
    public SystemProperties getSystemProperties();
    public void writeProperties() ;
}
