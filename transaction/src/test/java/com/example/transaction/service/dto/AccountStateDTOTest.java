package com.example.transaction.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.transaction.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class AccountStateDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountStateDTO.class);
        AccountStateDTO accountStateDTO1 = new AccountStateDTO();
        accountStateDTO1.setId(1L);
        AccountStateDTO accountStateDTO2 = new AccountStateDTO();
        assertThat(accountStateDTO1).isNotEqualTo(accountStateDTO2);
        accountStateDTO2.setId(accountStateDTO1.getId());
        assertThat(accountStateDTO1).isEqualTo(accountStateDTO2);
        accountStateDTO2.setId(2L);
        assertThat(accountStateDTO1).isNotEqualTo(accountStateDTO2);
        accountStateDTO1.setId(null);
        assertThat(accountStateDTO1).isNotEqualTo(accountStateDTO2);
    }
}
