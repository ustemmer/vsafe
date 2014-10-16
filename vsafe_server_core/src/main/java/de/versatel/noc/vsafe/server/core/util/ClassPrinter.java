package de.versatel.noc.vsafe.server.core.util;

//import de.versatel.noc.vSafe_Server.database.MySQL_PoolHandling;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 *
 * @author ulrich.stemmer
 */
public class ClassPrinter {

    public static void main(String[] arguments) {
        print();
    }

    private static void print() {
        StringBuilder sb;
        // Auslesen der Attribute
        for (Field publicField : ClassPrinter.class.getFields()) {
            sb = new StringBuilder();
            sb.append(Modifier.toString(publicField.getModifiers()));
            sb.append(" ");
            sb.append(publicField.getType().getSimpleName());
            sb.append(" ");
            sb.append(publicField.getName());
            sb.append(";");
            System.out.println(sb.toString());

        }
        System.out.println();
        for (Method method : ClassPrinter.class.getDeclaredMethods()) {
            sb = new StringBuilder();
            sb.append(Modifier.toString(method.getModifiers()));
            sb.append(" ");
            sb.append(method.getReturnType().getSimpleName());
            sb.append(" ");
            sb.append(method.getName());
            sb.append("(");
            int i = 0;
            for (Class c : method.getParameterTypes()) {
                if (i > 0) {
                    sb.append(",");
                    sb.append(" ");
                }

                sb.append(c.getSimpleName());
                sb.append(" ");
                sb.append(c.getSimpleName().substring(0, 1).toLowerCase());

                i++;
            }
            sb.append(");");
            System.out.println(sb.toString());
        }
    }
}
