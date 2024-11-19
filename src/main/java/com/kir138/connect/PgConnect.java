package com.kir138.connect;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class PgConnect {
    public static SessionFactory getSessionFactory() {
        return new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }
}
