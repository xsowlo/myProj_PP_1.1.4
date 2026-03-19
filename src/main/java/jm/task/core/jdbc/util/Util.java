package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class Util {

    public Util() {

    }
    private static SessionFactory sessionFactory;

    static {
        try {
            Properties properties = new Properties();


            properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            properties.put(Environment.URL, "jdbc:mysql://localhost:3306/mynewdbtest");
            properties.put(Environment.USER, "root");
            properties.put(Environment.PASS, "Myrootsqlpass22");
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
            properties.put(Environment.SHOW_SQL, "false");
            properties.put(Environment.FORMAT_SQL, "true");
            properties.put(Environment.HBM2DDL_AUTO, "update");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            properties.put("hibernate.hbm2ddl.auto", "update");
            properties.put("hibernate.hbm2ddl.create_namespaces", "true");

            java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);

            sessionFactory = new Configuration()
                    .setProperties(properties)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Ошибка создания сессии");
        }


    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }


}

    //private static final String URL = "jdbc:mysql://localhost:3306/mynewdbtest";
    //private static final String USERNAME = "root";
    //private static final String PASSWORD = "Myrootsqlpass22";



    //public static Connection getConnection() {
       // Connection connection = null;
        //try{
         //   connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        //} catch (SQLException e) {
          //  e.printStackTrace();
        //}
        //return connection;
    //}

