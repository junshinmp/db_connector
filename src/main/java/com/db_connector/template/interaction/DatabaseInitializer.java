package com.db_connector.template.interaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.StringBuilder;

public class DatabaseInitializer {
    DatabaseConnection dbConnection;

    public DatabaseInitializer(DatabaseConnection dbConnection){
        this.dbConnection = dbConnection;
    }

    public void createTable(String tableName, String[] varNames, String[] types) throws SQLException{
        // Simple checks to ensure that statement is correctly formatted for the table.
        if (varNames == null || types == null || varNames.length != types.length){
            throw new IllegalArgumentException("Statement is not correct format.");
        }

        // Create the statement from its parts.
        StringBuilder createString = new StringBuilder();

        // Attaches the correlating tableName variable.
        // createString will be attached as the following:
        // createString = "create table if not exists [TABLENAME] (""
        createString.append("create table if not exists ").append(tableName).append(" (");
        
        // now adding all the varNames and types attached to it, including all the constraints
        for (int i = 0; i < varNames.length; i++){
            // formatted like:
            // "fruit VARCHAR(25) PRIMARY KEY"
            createString.append(varNames[i]).append(" ").append(types[i]);
            
            // simple check to ensure that the last pairing doesn't insert a comma
            if (i < varNames.length - 1){
                createString.append(", ");
            }
        }

        // additionally add the closing ");"
        createString.append(");");

        // try, catch block for both statements as both can result in SQLException errors.
        try (statementExecutor(createString)) {
            System.out.println("Table '" + tableName + "' verified/created successfully.");
        } catch (SQLException e) {
            throw new SQLException("Table could not be created for " + tableName, e);
        }
    }

    public void dropTable(String tableName, boolean cascade) throws SQLException{
        // Create the statement from its parts.
        StringBuilder createString = new StringBuilder();
        createString.append("drop table if exists ").append(tableName);
        if (cascade){
            createString.append(" cascade");
        }
        createString.append(";");

        // try, catch block for both statements as both can result in SQLException errors.
        try (Connection database = dbConnection.getConnection(); Statement stmt = database.createStatement()) {
            // attempt to execute this string to the database file.
            stmt.executeUpdate(createString.toString());
            System.out.println("Table '" + tableName + "' verified/created successfully.");
        } catch (SQLException e) {
            throw new SQLException("Table could not be created for " + tableName, e);
        }
    }

    public void alterTable(String tableName, AlterTypes alterType, String[] items){
        // Create the statement from its parts
        StringBuilder createString = new StringBuilder();
        createString.append("alter table ").append(tableName).append(" ");

        // Could range from 
        createString.append(alterType.toString());
        
        for (int i = 0; i < items.length; i++){
            createString.append(items[i]);

            if (i < items.length - 1){
                createString.append(" ");
            }
        }

        createString.append(";");

    }

    private boolean statementExecutor(StringBuilder createString) throws SQLException{
        // try, catch block for both statements as both can result in SQLException errors.
        try (Connection database = dbConnection.getConnection(); Statement stmt = database.createStatement()) {
            // attempt to execute this string to the database file.
            stmt.executeUpdate(createString.toString());
            return true;
        } catch (SQLException e) { 
            throw new SQLException(e);
        }
    }
}
