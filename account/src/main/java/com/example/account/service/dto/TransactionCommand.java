package com.example.account.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class TransactionCommand {

    @NotNull
    private final Long customerID;
    @NotNull
    @Positive
    private final BigDecimal amount;

    public TransactionCommand(Long customerID, BigDecimal amount) {
        this.customerID = customerID;
        this.amount = amount;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
