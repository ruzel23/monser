package com.monser.model;


import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Client {

    private long id;
    private String firstName;
    private String lastName;
    private int passportId;
    private Set<Account> accounts = new HashSet<>();

    public Client() {
    }

    public Client(Long id, String firstName, String lastName, int passportId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passportId = passportId;
    }
}
