package de.versatel.noc.vsafe.server.mvc.models;

import de.versatel.noc.vSafe.mvc.AbstractModel;

/**
 *
 * @author ulrich.stemmer
 */
public class SystemSettingsModel  extends AbstractModel{
    public static final String SYSTEMPROPERTIES = "sytem_properties";

    public SystemSettingsModel() {
        addProperty(new MVCProperty(SYSTEMPROPERTIES, Integer.valueOf(0)));
    }

}
