package com.example.transaction.service.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.example.transaction.domain.AccountState} entity.
 */
public class AccountStateDTO implements Serializable {
    private Long id;

    @NotNull
    private Long customerID;

    @NotNull
    private BigDecimal balance;

    /**
     * All transactions
     */
    @ApiModelProperty(value = "All transactions")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountStateDTO)) {
            return false;
        }

        return id != null && id.equals(((AccountStateDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountStateDTO{" +
            "id=" + getId() +
            ", customerID=" + getCustomerID() +
            ", balance=" + getBalance() +
            "}";
    }
}
