package com.example.transaction.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.example.transaction.domain.MonetaryTransaction} entity.
 */
@ApiModel(
    description = "In first PR we assume transaction only when non-empty account created.\nE.i. no other transactions going on in this design.\nFor example, there is no currency included in this PR."
)
public class MonetaryTransactionDTO implements Serializable {
    private Long id;

    @NotNull
    private Long recipientID;

    @NotNull
    private Long senderID;

    @NotNull
    private BigDecimal amount;

    /**
     * Issued transaction
     */
    @ApiModelProperty(value = "Issued transaction")
    private Long accountId;

    private String accountCustomerID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecipientID() {
        return recipientID;
    }

    public void setRecipientID(Long recipientID) {
        this.recipientID = recipientID;
    }

    public Long getSenderID() {
        return senderID;
    }

    public void setSenderID(Long senderID) {
        this.senderID = senderID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountStateId) {
        this.accountId = accountStateId;
    }

    public String getAccountCustomerID() {
        return accountCustomerID;
    }

    public void setAccountCustomerID(String accountStateCustomerID) {
        this.accountCustomerID = accountStateCustomerID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MonetaryTransactionDTO)) {
            return false;
        }

        return id != null && id.equals(((MonetaryTransactionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MonetaryTransactionDTO{" +
            "id=" + getId() +
            ", recipientID=" + getRecipientID() +
            ", senderID=" + getSenderID() +
            ", amount=" + getAmount() +
            ", accountId=" + getAccountId() +
            ", accountCustomerID='" + getAccountCustomerID() + "'" +
            "}";
    }
}