package de.versatel.noc.vsafe.server.mvc.models;

import de.versatel.noc.vSafe.mvc.AbstractModel;
import java.util.BitSet;
//import de.versatel.noc.vSafe_Server.mvc.listeners.*;

/**
 *
 * @author ulrich.stemmer
 */
public class UserGroupModel extends AbstractModel {

    public static final String USERGROUPID = "ug_id";
    public static final String NAME = "ug_groupname";
    public static final String REMARK = "ug_remark";
    public static final String PERMISSIONBITS = "ug_permissionbits";
    public static final String PERMISSIONBYTES = "ug_permissionbytes";

    public UserGroupModel() {
        addProperty(new MVCProperty(USERGROUPID, Integer.valueOf(0)));
        addProperty(new MVCProperty(NAME, new String()));
        addProperty(new MVCProperty(REMARK, new String()));
        addProperty(new MVCProperty(PERMISSIONBITS, new BitSet()));
        addProperty(new MVCProperty(PERMISSIONBYTES, new byte[0]));
    }
}
