package com.example.transaction.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

import static javax.persistence.CascadeType.*;

/**
 * A AccountState.
 */
@Entity
@Table(name = "accounts")
public class AccountState implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Maps to Account ID, NOT User ID
     */
    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Long customerID;

    @NotNull
    @Column(name = "balance", precision = 21, scale = 2, nullable = false)
    private BigDecimal balance;

    /**
     * All transactions
     */
    @OneToMany(mappedBy = "account", cascade = ALL)
    private Set<MonetaryTransaction> transactions = new HashSet<>();

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

    public AccountState customerID(Long customerID) {
        this.customerID = customerID;
        return this;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public AccountState balance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Set<MonetaryTransaction> getTransactions() {
        return transactions;
    }

    public AccountState transactions(Set<MonetaryTransaction> monetaryTransactions) {
        this.transactions = monetaryTransactions;
        return this;
    }

    public AccountState addTransactions(MonetaryTransaction monetaryTransaction) {
        this.transactions.add(monetaryTransaction);
        monetaryTransaction.setAccount(this);
        return this;
    }

    public AccountState removeTransactions(MonetaryTransaction monetaryTransaction) {
        this.transactions.remove(monetaryTransaction);
        monetaryTransaction.setAccount(null);
        return this;
    }

    public void setTransactions(Set<MonetaryTransaction> monetaryTransactions) {
        this.transactions = monetaryTransactions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountState)) {
            return false;
        }
        return id != null && id.equals(((AccountState) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountState{" +
            "id=" + getId() +
            ", customerID=" + getCustomerID() +
            ", balance=" + getBalance() +
            "}";
    }
}
