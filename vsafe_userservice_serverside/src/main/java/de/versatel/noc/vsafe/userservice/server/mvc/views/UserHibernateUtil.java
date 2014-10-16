package de.versatel.noc.vsafe.userservice.server.mvc.views;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 *
 * @author ulrich.stemmer
 */
public class UserHibernateUtil {

    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new AnnotationConfiguration()
                    .addPackage("de.bennyn.example.hibernate")
                    .addAnnotatedClass(VO_User.class)
                    .configure()
                    .buildSessionFactory();
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
