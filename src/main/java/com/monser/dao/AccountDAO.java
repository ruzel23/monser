package com.monser.dao;

import com.monser.model.Account;
import com.monser.model.Client;
import com.monser.services.DataSource;

import java.sql.*;

// продумать все проверки, исключения.
// сущ клиента, сущ счета
public class AccountDAO {

    public static final String SQL_OPEN_ACCOUNT =
            "INSERT INTO account (date_open, id_client) VALUES (?, ?)";

    public static final String SQL_CLOSE_ACCOUNT =
            "UPDATE account SET date_close = ? WHERE number_account = ?";

    public static final String SQL_FIND_BY_NUMBER_ACCOUNT =
            "SELECT *FROM account join client c on account.id_client = c.id and account.id = ?";

    private DataSource dataSource;

    public AccountDAO() {
        dataSource = new DataSource();
    }

    public void openAccount(long clientId) {
        java.util.Date date = new java.util.Date();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            PreparedStatement statement = connection.prepareStatement(AccountDAO.SQL_OPEN_ACCOUNT);
            java.sql.Date sqlDate = new Date(date.getTime());
            statement.setDate(1, sqlDate);
            statement.setLong(2, clientId);
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

    public void closeAccount(Account account) {
        java.util.Date date = new java.util.Date();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(AccountDAO.SQL_CLOSE_ACCOUNT);
            java.sql.Date sqlDate = new Date(date.getTime());
            statement.setDate(1, sqlDate);
            statement.setLong(2, account.getNumberAccount());
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

    public Account findByNumberAccount(Account account) {

        Client client = null;
        Account foundAccount = new Account();
        java.util.Date dateOpen = null;
        java.util.Date dateClose= null;
        try(Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_NUMBER_ACCOUNT);
            statement.setLong(1, account.getNumberAccount());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                dateOpen = new java.util.Date(rs.getDate("ACCOUNT.date_open").getTime());
                if (rs.getDate("ACCOUNT.date_close") != null) {
                    dateClose = new java.util.Date(rs.getDate("ACCOUNT.date_close").getTime());
                }

                if(rs.getLong("CLIENT.id") != 0) {
                    client = new Client();
                    client.setId(rs.getLong("CLIENT.id"));
                    client.setFirstName(rs.getString("CLIENT.first_name"));
                    client.setLastName(rs.getString("CLIENT.last_name"));
                    client.setPassportId(rs.getInt("CLIENT.passport_id"));
                }


                foundAccount.setId(rs.getLong("ACCOUNT.id"));
                foundAccount.setNumberAccount(rs.getLong("ACCOUNT.number_account"));
                foundAccount.setDateOpen(dateOpen);
                foundAccount.setDateClose(dateClose);
                foundAccount.setMoney(rs.getBigDecimal("ACCOUNT.money"));
                foundAccount.setClient(client);
                return foundAccount;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public void transferMoneyToAccount(Account account) {

        Connection connection = null;
        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            Account foundAccount = findByNumberAccount(account);

            if (foundAccount == null) {
                System.out.println("счет не найден");
                return;
            }



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

}
