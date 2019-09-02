package com.monser;

import com.monser.controllers.AccountController;
import com.monser.controllers.ClientController;
import com.monser.model.Account;
import com.monser.model.Client;
import com.monser.services.DataBaseService;
import com.monser.services.DataSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;



//рефактор нужен
public class App {

    public static void main(String[] args) {
        int i;
        ClientController clientController = new ClientController();
        AccountController accountController = new AccountController();
        DataSource dataSource = new DataSource();

        System.out.println("1. создать клиента \n" +
                "2. редактирование клиента \n" +
                "3. открытие счета \n" +
                "4. закрытие счета \n" +
                "5. выписка по счету клиента \n" +
                "6. список счетов клиента \n");
        try (Connection connection = dataSource.getConnection();
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            start();

            while (true) {
                i = Integer.parseInt(reader.readLine());
                switch (i) {
                    case 1:
                        createClient(clientController, reader);
                        continue;
                    case 2:
                        changeClient(clientController, reader);
                        continue;
                    case 3:
                        openAccount(clientController, accountController, reader);
                        continue;
                    case 4:
                        closeAccount(accountController, reader);
                        continue;
                    case 5:
                        accountStatement(accountController, reader);
                        continue;
                    case 6:
                        getClientAccounts(clientController, reader);
                        continue;
                    default:
                        break;

                }
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void start() {
        DataBaseService.createClientTable();
        DataBaseService.createBankAccount();
    }


    // ---------------------------------------------------------------------------------------------------------
    // Client service
    // закинуть в клиент сервис
    // рефактор нужен
    public static void createClient(ClientController clientController, BufferedReader reader) throws IOException {

        Client client = new Client();
        System.out.print("Введите имя: ");
        String firstName = reader.readLine();
        System.out.print("Введите фамилию: ");
        String lastName = reader.readLine();
        System.out.print("Пасспортные данные: ");
        int passportId = Integer.parseInt(reader.readLine());

        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setPassportId(passportId);

        clientController.createClient(client);
    }

    public static void changeClient(ClientController clientController, BufferedReader reader) throws IOException {
        System.out.println("1. изменить имя \n" +
                "2. изменить фамилию \n" +
                "3. изменить номер паспорта \n" +
                "0. назад \n");
        int i;

        while (true) {
            i = Integer.parseInt(reader.readLine());
            switch (i) {
                case 1:
                    changeFirstName(clientController, reader);
                    continue;
                case 2:
                    changeLastName(clientController, reader);
                    continue;
                case 3:
                    changePassportId(clientController, reader);
                    continue;
                case 0:
                    break;
            }
        }
    }

    public static void changeFirstName(ClientController clientController, BufferedReader reader) throws IOException {
        Client client = new Client();
        System.out.print("Введите новое имя ");
        String firstName = reader.readLine();
        System.out.print("Введите паспортные данные ");
        int passportId = Integer.parseInt(reader.readLine());
        client.setFirstName(firstName);
        client.setPassportId(passportId);
        clientController.changeFirstName(client);
    }

    public static void changeLastName(ClientController clientController, BufferedReader reader) throws IOException {
        Client client = new Client();
        System.out.print("Введите фамилию ");
        String lastName = reader.readLine();
        System.out.print("Введите паспортные данные ");
        int passportId = Integer.parseInt(reader.readLine());
        client.setLastName(lastName);
        client.setPassportId(passportId);
        clientController.changeLastName(client);
    }

    public static void changePassportId(ClientController clientController, BufferedReader reader) throws IOException {

        Client clientUpdate = new Client();
        Client clientOld = new Client();

        System.out.print("Введите паспортные данные ");
        int passportIdNew = Integer.parseInt(reader.readLine());
        System.out.print("Введите новые паспортные данные ");
        int passportId = Integer.parseInt(reader.readLine());

        clientUpdate.setPassportId(passportIdNew);
        clientOld.setPassportId(passportId);
        clientController.changePassportId(clientUpdate, clientOld);

    }

    public static void getClientAccounts(ClientController clientController, BufferedReader reader) throws IOException {

        System.out.println("введите паспортные данные");
        int passportId = Integer.parseInt(reader.readLine());
        Client client = new Client();
        client.setPassportId(passportId);

        Client clientFound = clientController.getClientAccounts(client);

        if (clientFound == null) {
            System.out.println("клиент не найден");
            return;
        }

        System.out.println(clientFound.toString());

    }


    //
    // -------------------------------------------------------------------------------------------------------


    // --------------------------------------------------------------------------------------------------------
    // Account service

    public static void openAccount(ClientController clientController, AccountController accountController, BufferedReader reader) throws IOException {

        Client clientFind = new Client();
        Client clientFound = null;
        System.out.println("Введите пасспортные данные");
        int passportID = Integer.parseInt(reader.readLine());
        clientFind.setPassportId(passportID);
        clientFound = clientController.getClient(clientFind);

        if (clientFound == null) {
            System.out.println("клиент не найден");
            return;
        }


        accountController.openAccount(clientFound.getId());

    }

    public static void closeAccount(AccountController accountController, BufferedReader reader) throws IOException {

        System.out.println("введите номер счета");
        long accountNumber = Long.parseLong(reader.readLine());

        Account account = new Account();
        Account accountFound;

        account.setNumberAccount(accountNumber);

        accountFound = accountController.getAccount(account);

        if (accountFound == null) {
            System.out.println("счет не найден");
            return;
        }

        account.setClient(accountFound.getClient());

        accountController.closeAccount(account);

    }

    public static void transferMoneyToAccount(AccountController accountController, BufferedReader reader) throws IOException {

        System.out.println("введите номер счета");
        long accountNumber = Long.parseLong(reader.readLine());

        Account account = new Account();

        account.setNumberAccount(accountNumber);

    }


    public static void accountStatement(AccountController accountController, BufferedReader reader) throws IOException {

        System.out.println("введите номер счета");
        long accountNumber = Long.parseLong(reader.readLine());

        Account account = new Account();
        Account accountFound;

        account.setNumberAccount(accountNumber);

        accountFound = accountController.getAccount(account);

        if (accountFound == null) {
            System.out.println("счет не найден");
            return;
        }

        System.out.println(accountFound.toString());

    }

    //------------------------------------------------------------------------------------------------------

}
