package com.example.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A MasterAccount.
 */
@Entity
@Table(name = "accounts")
public class MasterAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Long customerID;

    @Column(name = "initial_credit", precision = 21, scale = 2)
    private BigDecimal initialCredit;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public MasterAccount customerID(Long customerID) {
        this.customerID = customerID;
        return this;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public BigDecimal getInitialCredit() {
        return initialCredit;
    }

    public MasterAccount initialCredit(BigDecimal initialCredit) {
        this.initialCredit = initialCredit;
        return this;
    }

    public void setInitialCredit(BigDecimal initialCredit) {
        this.initialCredit = initialCredit;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MasterAccount)) {
            return false;
        }
        return id != null && id.equals(((MasterAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MasterAccount{" +
            "id=" + getId() +
            ", customerID=" + getCustomerID() +
            ", initialCredit=" + getInitialCredit() +
            "}";
    }
}
