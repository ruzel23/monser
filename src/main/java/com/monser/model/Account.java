package com.monser.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Account {

    private long id;
    private long numberAccount;
    private Date dateOpen;
    private Date dateClose;
    private BigDecimal money;
    private Client client;

}
