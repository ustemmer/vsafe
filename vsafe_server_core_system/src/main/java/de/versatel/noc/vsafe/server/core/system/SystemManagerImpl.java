package de.versatel.noc.vsafe.server.core.system;

import de.versatel.noc.vsafe.server.common.coreinterfaces.SystemManager;



/**
 *
 * @author ulrich.stemmer
 */
public class SystemManagerImpl implements SystemManager  {

    protected SystemSettings systemSettings;
    protected SystemProperties systemProperties;

    public SystemManagerImpl() {
        systemSettings = new SystemSettings();
        systemProperties = new SystemProperties(
                SystemSettings.SYSTEMPROPERTY_FILENAME,
                SystemSettings.SYSTEMPROPERTY_CRYPTALGORYTHM,
                SystemSettings.SYSTEMPROPERTY_CRYPTKEY_16BYTE);
    }

    public SystemSettings getSystemSettings() {
        return systemSettings;
    }

    public SystemProperties getSystemProperties() {
        return systemProperties;
    }

    public void writeProperties() {
        try {
            getSystemProperties().store();
        } catch (Exception e) {
        }
    }
}
