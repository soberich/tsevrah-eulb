package com.example.account.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.example.account.domain.AccountApplication} entity.
 */
public class AccountApplicationDTO implements Serializable {
    private Long id;

    @NotNull
    private Long customerID;

    private BigDecimal initialCredit;

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

    public BigDecimal getInitialCredit() {
        return initialCredit;
    }

    public void setInitialCredit(BigDecimal initialCredit) {
        this.initialCredit = initialCredit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountApplicationDTO)) {
            return false;
        }

        return id != null && id.equals(((AccountApplicationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountApplicationDTO{" +
            "id=" + getId() +
            ", customerID=" + getCustomerID() +
            ", initialCredit=" + getInitialCredit() +
            "}";
    }
}
