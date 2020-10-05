package com.example.transaction.service.mapper;

import com.example.transaction.domain.*;
import com.example.transaction.service.dto.MonetaryTransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MonetaryTransaction} and its DTO {@link MonetaryTransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = { AccountStateMapper.class })
public interface MonetaryTransactionMapper extends EntityMapper<MonetaryTransactionDTO, MonetaryTransaction> {
    @Mapping(source = "account.id", target = "accountStateID")
    @Mapping(source = "account.customerID", target = "accountCustomerID")
    MonetaryTransactionDTO toDto(MonetaryTransaction monetaryTransaction);

    @Mapping(source = "accountStateID", target = "account")
    MonetaryTransaction toEntity(MonetaryTransactionDTO monetaryTransactionDTO);

    default MonetaryTransaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        MonetaryTransaction monetaryTransaction = new MonetaryTransaction();
        monetaryTransaction.setId(id);
        return monetaryTransaction;
    }
}
