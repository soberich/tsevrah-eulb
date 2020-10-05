package com.example.transaction.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * In first PR we assume transaction only when non-empty account created.\nE.i. no other transactions going on in this design.\nFor example, there is no currency included in this PR.
 */
@Entity
@Table(name = "transactions")
public class MonetaryTransaction implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "recipient_id")
    private Long recipientID;

    @Column(name = "sender_id")
    private Long senderID;

    @NotNull
    @Column(name = "amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal amount;

    /**
     * Issued transaction
     */
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "transactions", allowSetters = true)
    private AccountState account;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecipientID() {
        return recipientID;
    }

    public MonetaryTransaction recipientID(Long recipientID) {
        this.recipientID = recipientID;
        return this;
    }

    public void setRecipientID(Long recipientID) {
        this.recipientID = recipientID;
    }

    public Long getSenderID() {
        return senderID;
    }

    public MonetaryTransaction senderID(Long senderID) {
        this.senderID = senderID;
        return this;
    }

    public void setSenderID(Long senderID) {
        this.senderID = senderID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public MonetaryTransaction amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public AccountState getAccount() {
        return account;
    }

    public MonetaryTransaction account(AccountState accountState) {
        this.account = accountState;
        return this;
    }

    public void setAccount(AccountState accountState) {
        this.account = accountState;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MonetaryTransaction)) {
            return false;
        }
        return id != null && id.equals(((MonetaryTransaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MonetaryTransaction{" +
            "id=" + getId() +
            ", recipientID=" + getRecipientID() +
            ", senderID=" + getSenderID() +
            ", amount=" + getAmount() +
            "}";
    }
}
