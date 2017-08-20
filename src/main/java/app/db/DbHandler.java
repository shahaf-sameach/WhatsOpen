package app.db;

import app.config.DatabaseConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHandler {

    public static Connection connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(DatabaseConfiguration.getUrl(), DatabaseConfiguration.getUsername(), DatabaseConfiguration.getPassword());
        } catch (Exception e) {
            System.err.println("Error connecting to the database:");
            System.err.println(e);
            return null;
        }
    }

}
