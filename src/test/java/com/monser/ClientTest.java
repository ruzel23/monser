package com.monser;

import com.monser.controllers.ClientController;
import com.monser.model.Client;
import com.monser.services.DataBaseService;
import com.monser.services.DataSource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class ClientTest {

    private DataSource dataSource;
    private ClientController clientController;


    @Before
    public void init() {

        dataSource = new DataSource();
        clientController = new ClientController();



    }

    @Test
    public void createClientTest() {

        String sql = "SELECT first_name, LAST_NAME, PASSPORT_ID FROM CLIENT WHERE PASSPORT_ID = 123";
        Client expected = new Client();
        Client actual = null;
        expected.setFirstName("John");
        expected.setLastName("Smith");
        expected.setPassportId(123);

        try (Connection connection = dataSource.getConnection()) {

            DataBaseService.createClientTable();
            DataBaseService.createBankAccount();

            clientController.createClient(expected);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                actual = new Client();
                actual.setFirstName(rs.getString(1));
                actual.setLastName(rs.getString(2));
                actual.setPassportId(rs.getInt(3));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(expected, actual);

    }

}
