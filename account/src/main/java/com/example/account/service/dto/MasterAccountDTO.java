package com.example.account.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.example.account.domain.MasterAccount} entity.
 */
public class MasterAccountDTO implements Serializable {
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
        if (!(o instanceof MasterAccountDTO)) {
            return false;
        }

        return id != null && id.equals(((MasterAccountDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MasterAccountDTO{" +
            "id=" + getId() +
            ", customerID=" + getCustomerID() +
            ", initialCredit=" + getInitialCredit() +
            "}";
    }
}
