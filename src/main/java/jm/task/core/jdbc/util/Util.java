package jm.task.core.jdbc.util;

import org.hibernate.cfg.*;
import jm.task.core.jdbc.model.User;
import java.sql.Connection;

import java.sql.DriverManager;
import java.util.Properties;

public class Util {
    private static String URL_STRING = "jdbc:mysql://localhost:3306/test?useUnicode=true&serverTimezone=UTC&useSSL=false";
    private static String LOGIN = "root";
    private static String PASS = "123321";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            return DriverManager.getConnection(URL_STRING, LOGIN, PASS);
        } catch (Exception e) {
            System.out.println("Unable to load class.");
            e.printStackTrace();
        }
        return null;
    }

    public static Configuration getConfig() {
        Properties properties = new Properties();
        properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.setProperty(Environment.HBM2DDL_AUTO,"update");
        properties.setProperty(Environment.DRIVER, "com.mysql.jdbc.Driver");
        properties.setProperty(Environment.USER, LOGIN);
        properties.setProperty(Environment.PASS, PASS);
        properties.setProperty(Environment.URL, URL_STRING);
        Configuration cfg = new Configuration();
        cfg.setProperties(properties);
        cfg.addAnnotatedClass(User.class);
        return cfg;
    }
}



