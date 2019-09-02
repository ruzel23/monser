package com.monser.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:mem:inmemory");
    }

    public void closeConnection(Connection connection) {
        if (connection == null) return;
        try {
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
