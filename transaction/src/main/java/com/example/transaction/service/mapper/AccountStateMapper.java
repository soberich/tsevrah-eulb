package com.example.transaction.service.mapper;

import com.example.transaction.domain.*;
import com.example.transaction.service.dto.AccountStateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountState} and its DTO {@link AccountStateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AccountStateMapper extends EntityMapper<AccountStateDTO, AccountState> {
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "removeTransactions", ignore = true)
    AccountState toEntity(AccountStateDTO accountStateDTO);

    default AccountState fromId(Long id) {
        if (id == null) {
            return null;
        }
        AccountState accountState = new AccountState();
        accountState.setId(id);
        return accountState;
    }
}
