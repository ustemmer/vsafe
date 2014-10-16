package de.versatel.noc.vsafe.server.mvc.models;

import de.versatel.noc.vSafe.mvc.AbstractModel;
import de.versatel.noc.vSafe_Server.system.SystemUncaughtExceptionHandler;

/**
 *
 * @author ulrich.stemmer
 */
public class DebuggingModel extends AbstractModel {

    public static final String THROWABLE_THROWN = "throwable_thrown";
    private final SystemUncaughtExceptionHandler topLevelExceptionHandler;
    
    public DebuggingModel(SystemUncaughtExceptionHandler tleh) {
        this.topLevelExceptionHandler = tleh;
    }

}
