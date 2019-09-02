package com.monser.controllers;

import com.monser.dao.ClientDAO;
import com.monser.model.Client;


// обработка ошибок и исключений
public class ClientController {

    private ClientDAO clientDAO;

    public ClientController() {
        clientDAO = new ClientDAO();
    }

    public void createClient(Client client) {
        clientDAO.createClient(client);
    }

    public void changeFirstName(Client client) {
        clientDAO.changeFirstName(client);
    }

    public void changeLastName(Client client) {
        clientDAO.changeLastName(client);
    }

    public void changePassportId(Client clientUpdate, Client clientOld) {
        clientDAO.changePassportId(clientUpdate, clientOld);
    }

    public Client getClient(Client client) {
        return clientDAO.findByPassportId(client);
    }

    public Client getClientAccounts(Client client) {
        return clientDAO.getClientAccounts(client);
    }


}
