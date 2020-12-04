package jm.task.core.jdbc.util;

import java.sql.Connection;

import java.sql.DriverManager;

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
}



