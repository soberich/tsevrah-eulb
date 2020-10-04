package com.example.account.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A AccountApplication.
 */
@Entity
@Table(name = "accounts")
public class AccountApplication implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Long customerID;

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

    public AccountApplication customerID(Long customerID) {
        this.customerID = customerID;
        return this;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountApplication)) {
            return false;
        }
        return id != null && id.equals(((AccountApplication) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountApplication{" +
            "id=" + getId() +
            ", customerID=" + getCustomerID() +
            "}";
    }
}
