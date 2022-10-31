package net.javaguides.todoapp.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateXmlUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable throwable) {
            throw new ExceptionInInitializerError(throwable);
        }
    }


    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
