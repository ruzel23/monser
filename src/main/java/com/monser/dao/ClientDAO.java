package com.monser.dao;

import com.monser.model.Account;
import com.monser.model.Client;
import com.monser.services.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


//продумать все проверки, исключения
public class ClientDAO {

    private static final String SQL_CREATE_CLIENT =
            "INSERT INTO client (first_name, last_name, passport_id) VALUES (?, ?, ?)";

    private static final String SQL_CHANGE_FIRST_NAME =
            "UPDATE client SET first_name = ? where passport_id = ?";

    public static final String SQL_CHANGE_LAST_NAME =
            "UPDATE client SET last_name = ? where passport_id = ?";

    public static final String SQL_CHANGE_PASSPORT_ID =
            "UPDATE client SET passport_id = ? where passport_id = ?";

    public static final String SQL_FIND_BY_PASSPORT_ID =
            "SELECT * FROM client WHERE passport_id = ?";

    public static final String SQL_CUSTOMER_ACCOUNTS =
            "SELECT * FROM client JOIN account WHERE CLIENT.id = id_client AND passport_id = ?";

    private DataSource dataSource;

    public ClientDAO() {
        dataSource = new DataSource();
    }

    //проверка на существующего клиента!!!
    public void createClient(Client client) {

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(ClientDAO.SQL_CREATE_CLIENT);
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setInt(3, client.getPassportId());
            statement.execute();

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            dataSource.closeConnection(connection);
        }
    }

    public void changeFirstName(Client client) {

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(ClientDAO.SQL_CHANGE_FIRST_NAME);
            statement.setString(1, client.getFirstName());
            statement.setInt(2, client.getPassportId());
            statement.execute();

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            dataSource.closeConnection(connection);
        }
    }

    public void changeLastName(Client client) {

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(ClientDAO.SQL_CHANGE_LAST_NAME);
            statement.setString(1, client.getLastName());
            statement.setInt(2, client.getPassportId());
            statement.execute();

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            dataSource.closeConnection(connection);
        }
    }

    public void changePassportId(Client clientUpdate, Client clientOld) {

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(ClientDAO.SQL_CHANGE_PASSPORT_ID);
            statement.setInt(1, clientUpdate.getPassportId());
            statement.setInt(2, clientOld.getPassportId());
            statement.execute();

            connection.commit();
        }  catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            dataSource.closeConnection(connection);
        }
    }

    public Client findByPassportId(Client client) {
        Client foundClient = null;
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_PASSPORT_ID);
            statement.setInt(1, client.getPassportId());

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                foundClient = new Client();
                foundClient.setId(rs.getLong("id"));
                foundClient.setFirstName(rs.getString("first_name"));
                foundClient.setLastName(rs.getString("last_name"));
                foundClient.setPassportId(rs.getInt("passport_id"));
                return foundClient;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Client getClientAccounts(Client client) {
        Client foundClient = null;
        try(Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(SQL_CUSTOMER_ACCOUNTS);
            statement.setInt(1, client.getPassportId());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                if (foundClient == null) {
                    foundClient = new Client();
                    foundClient.setId(rs.getLong("CLIENT.id"));
                    foundClient.setFirstName("");
                    foundClient.setFirstName(rs.getString("first_name"));
                    foundClient.setLastName(rs.getString("last_name"));
                    foundClient.setPassportId(rs.getInt("passport_id"));
                }
                Date dateOpen = null;
                Date dateClose= null;

                dateOpen = new Date(rs.getDate("ACCOUNT.date_open").getTime());
                if (rs.getDate("ACCOUNT.date_close") != null) {
                    dateClose = new Date(rs.getDate("ACCOUNT.date_close").getTime());
                }

                Account account = new Account();
                account.setId(rs.getLong("ACCOUNT.id"));
                account.setNumberAccount(rs.getLong("ACCOUNT.number_account"));
                account.setDateOpen(dateOpen);
                account.setDateClose(dateClose);
                account.setMoney(rs.getBigDecimal("ACCOUNT.money"));


                foundClient.getAccounts().add(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundClient;
    }

}
