package com.example.account.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.account.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class AccountApplicationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountApplicationDTO.class);
        AccountApplicationDTO accountApplicationDTO1 = new AccountApplicationDTO();
        accountApplicationDTO1.setId(1L);
        AccountApplicationDTO accountApplicationDTO2 = new AccountApplicationDTO();
        assertThat(accountApplicationDTO1).isNotEqualTo(accountApplicationDTO2);
        accountApplicationDTO2.setId(accountApplicationDTO1.getId());
        assertThat(accountApplicationDTO1).isEqualTo(accountApplicationDTO2);
        accountApplicationDTO2.setId(2L);
        assertThat(accountApplicationDTO1).isNotEqualTo(accountApplicationDTO2);
        accountApplicationDTO1.setId(null);
        assertThat(accountApplicationDTO1).isNotEqualTo(accountApplicationDTO2);
    }
}
