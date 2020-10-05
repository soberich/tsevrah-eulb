package com.example.account.web.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class SimpleTransaction {

    @NotNull
    private final Long customerID;
    @NotNull
    @Positive
    private final BigDecimal initialCredit;

    public SimpleTransaction(Long customerID, BigDecimal initialCredit) {
        this.customerID = customerID;
        this.initialCredit = initialCredit;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public BigDecimal getInitialCredit() {
        return initialCredit;
    }
}
