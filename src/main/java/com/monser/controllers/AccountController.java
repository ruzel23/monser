package com.monser.controllers;

import com.monser.dao.AccountDAO;
import com.monser.model.Account;

public class AccountController {

    private AccountDAO accountDAO;

    public AccountController() {
        accountDAO = new AccountDAO();
    }

    public void openAccount(long clientId) {
        accountDAO.openAccount(clientId);
    }

    public void closeAccount(Account account) {
        accountDAO.closeAccount(account);
    }

    public Account getAccount(Account account) {
        return accountDAO.findByNumberAccount(account);
    }


}
