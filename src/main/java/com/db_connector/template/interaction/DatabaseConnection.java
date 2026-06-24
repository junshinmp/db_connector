package com.db_connector.template.interaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String CONNECTION_PATH = "jdbc:sqlite:database/template.db";

    public Connection getConnection() throws SQLException{
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC Driver missing from classpath.", e);
        }

        return DriverManager.getConnection(CONNECTION_PATH);
    }
}
