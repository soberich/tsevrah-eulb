package com.example.account.service.dto;

import java.math.BigDecimal;
import java.util.*;

public class CustomerDetails {

    private final Long customerID;
    private final String name;
    private final String surname;
    private final BigDecimal balance;
    private final List<AccountTransaction> transactions;

    public CustomerDetails(Long customerID, String name, String surname, BigDecimal balance, List<AccountTransaction> transactions) {
        this.customerID = customerID;
        this.name = name;
        this.surname = surname;
        this.balance = balance;
        this.transactions = transactions;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<AccountTransaction> getTransactions() {
        return transactions;
    }
}
