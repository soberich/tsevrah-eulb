package com.example.account.service.mapper;

import com.example.account.domain.*;
import com.example.account.service.dto.AccountApplyCommand;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountApplication} and its DTO {@link AccountApplyCommand}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AccountApplicationMapper extends EntityMapper<AccountApplyCommand, AccountApplication> {
    default AccountApplication fromId(Long id) {
        if (id == null) {
            return null;
        }
        AccountApplication accountApplication = new AccountApplication();
        accountApplication.setId(id);
        return accountApplication;
    }
}
