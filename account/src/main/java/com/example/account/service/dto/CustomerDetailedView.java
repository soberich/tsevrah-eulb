package com.example.account.service.dto;

import java.math.BigDecimal;
import java.util.*;

public class CustomerDetailedView {

    private final Long customerID;
    private final String name;
    private final String surname;
    private final BigDecimal balance;
    private final List<TransactionView> transactions;

    public CustomerDetailedView(Long customerID, String name, String surname, BigDecimal balance, List<TransactionView> transactions) {
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

    public List<TransactionView> getTransactions() {
        return transactions;
    }
}
