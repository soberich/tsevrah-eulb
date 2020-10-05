package com.example.account.service.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.math.BigDecimal;

public class AccountTransaction {

    @JsonAlias("accountCustomerID")
    private final Long customerID;
    @JsonAlias("accountStateID")
    private final Long accountID;
    private final BigDecimal amount;

    public AccountTransaction(Long customerID, Long accountID, BigDecimal amount) {
        this.customerID = customerID;
        this.accountID = accountID;
        this.amount = amount;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public Long getAccountID() {
        return accountID;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
