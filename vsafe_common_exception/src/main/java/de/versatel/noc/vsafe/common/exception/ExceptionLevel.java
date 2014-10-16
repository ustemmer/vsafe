package de.versatel.noc.vsafe.common.exception;

/**
 *
 * @author ulrich.stemmer
 */
public class ExceptionLevel implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4537248870416521070L;
    public static final ExceptionLevel FATAL = new ExceptionLevel(0);
    public static final ExceptionLevel ERROR = new ExceptionLevel(10);
    public static final ExceptionLevel WARNING = new ExceptionLevel(20);
    public static final ExceptionLevel INFO = new ExceptionLevel(30);
    public static final ExceptionLevel DEBUG = new ExceptionLevel(40);
    private int level;

    private ExceptionLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
