/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.versatel.noc.vsafe.server.mvc.models;

//import de.versatel.noc.vSafe_Server.users.*;

import de.versatel.noc.vSafe.mvc.AbstractModel;


/**
 *
 * @author ulrich.stemmer
 */
public class UserSessionModel extends AbstractModel{
    public static final String SESSIONID = "us_sessionid";
            public static final String USER ="us_userid";
            public static final String WORKSTATION ="us_workstation";
            public static final String IPV4 ="us_ipv4";
            public static final String CREATED_AT ="us_createdat";
            public static final String LAST_TOUCH ="us_lasttouch";
            public static final String STATE ="us_state";


    public UserSessionModel() {
        addProperty (new MVCProperty(SESSIONID,""));
        addProperty (new MVCProperty(USER, -1));
        addProperty (new MVCProperty(WORKSTATION, ""));
        addProperty (new MVCProperty(IPV4, ""));
        addProperty (new MVCProperty(CREATED_AT, Long.valueOf(0)));
        addProperty (new MVCProperty(LAST_TOUCH, Long.valueOf(0)));
        addProperty (new MVCProperty(STATE, Integer.valueOf(0)));
    }
}
