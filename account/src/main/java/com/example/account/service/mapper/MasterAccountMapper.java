package com.example.account.service.mapper;

import com.example.account.domain.*;
import com.example.account.service.dto.MasterAccountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MasterAccount} and its DTO {@link MasterAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MasterAccountMapper extends EntityMapper<MasterAccountDTO, MasterAccount> {
    default MasterAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        MasterAccount masterAccount = new MasterAccount();
        masterAccount.setId(id);
        return masterAccount;
    }
}
