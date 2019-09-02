package com.monser;

import com.monser.controllers.ClientController;
import com.monser.model.Client;
import com.monser.services.DataBaseService;
import com.monser.services.DataSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class test {

    private static final String CREATE_QUERY =
            "CREATE TABLE Client (GREETING VARCHAR(6), TARGET VARCHAR(6))";
    /**
     * Quaery that populates table with data.
     */
    private static final String DATA_QUERY =
            "INSERT INTO EXAMPLE VALUES('Hello','World')";

    /**
     * Do not construct me.
     */

    /**
     * Entry point.
     */
/*    public static void main(final String[] args) {
        try (Connection db = DriverManager.getConnection("jdbc:h2:mem:inmemory")) {
            try (Statement dataQuery = db.createStatement()) {
                dataQuery.execute(CREATE_QUERY);
                dataQuery.execute(DATA_QUERY);
            }

            try (PreparedStatement query =
                         db.prepareStatement("SELECT * FROM EXAMPLE")) {
                ResultSet rs = query.executeQuery();
                while (rs.next()) {
                    System.out.println(String.format("%s, %s!",
                            rs.getString(1),
                            rs.getString("TARGET")));
                }
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("Database connection failure: "
                    + ex.getMessage());
        }
    }*/
    private static Connection getH2Connection() throws SQLException {

        return DriverManager.getConnection("jdbc:h2:mem:inmemory");
    }

    public static String sqlCreate(String firstname, String lastname, int passport_id) {
        return "INSERT INTO client (firstname, lastname, passprot_id) " +
                "VALUES ('" + firstname + "','" + lastname + "', " + passport_id + ")";
    }

    public static void main(String[] args) throws IOException {

        DataSource dataSource = new DataSource();

        try {

            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            Connection connection1 = dataSource.getConnection();
            System.out.println(connection.getAutoCommit());
            System.out.println(connection1.getAutoCommit());


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}


/*
        try (Connection connection = new DataSource().getConnection()) {
            DataBaseService.createClientTable();
            Statement statement = connection.createStatement();
            statement.execute("SELECT *from client");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        ClientController clientController = new ClientController();
        Client client = new Client();
        client.setFirstName("f");
        client.setLastName("f");
        client.setPassportId(1);
        DataSource dataSource = new DataSource();



        while (true) {
            String str = bufferedReader.readLine();
            if (str.equals("a")) {
                Statement statement = null;
                try {
                    Connection connection = dataSource.getConnection();
                    statement = connection.createStatement();
                    DataBaseService.createClientTable();

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (str.equals("b")) {
                Statement statement = null;
                try {
                    statement = getH2Connection().createStatement();
                    statement.execute("SELECT *from client");
                    *//*   ResultSet rs = statement.executeQuery("SELECT id from client where id = 1");
                    while (rs.next()) {
                        System.out.println(rs.getLong("id"));
                    }*//*
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }*/