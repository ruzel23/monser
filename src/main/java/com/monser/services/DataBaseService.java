package com.monser.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


// рефактор трай с ресурсами
public class DataBaseService {

    private static DataSource dataSource = new DataSource();

    private static final String CREATE_CLIENT_TABLE =
            "CREATE TABLE client (" +
                    "id LONG AUTO_INCREMENT, " +
                    "first_name VARCHAR NOT NULL, " +
                    "last_name VARCHAR NOT NULL, " +
                    "passport_id INT NOT NULL UNIQUE , " +
                    "PRIMARY KEY (id))";

    private static final String CREATE_ACCOUNT_TABLE =
            "CREATE TABLE account (" +
                    "id LONG AUTO_INCREMENT, " +
                    "number_account LONG AUTO_INCREMENT, " +
                    "date_open DATE NOT NULL, " +
                    "date_close DATE default null, " +
                    "money DECIMAL default 0, " +
                    "id_client LONG NOT NULL, " +
                    "PRIMARY KEY (id), " +
                    "FOREIGN KEY (id_client) REFERENCES client(id) ON DELETE CASCADE)";

    public static void createClientTable() {

        try(Connection connection = dataSource.getConnection()) {

            Statement statement = connection.createStatement();
            statement.execute(CREATE_CLIENT_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createBankAccount() {
        try(Connection connection = dataSource.getConnection()) {

            Statement statement = connection.createStatement();
            statement.execute(CREATE_ACCOUNT_TABLE);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
