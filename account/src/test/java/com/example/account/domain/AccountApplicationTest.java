package com.example.account.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.account.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class AccountApplicationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountApplication.class);
        AccountApplication accountApplication1 = new AccountApplication();
        accountApplication1.setId(1L);
        AccountApplication accountApplication2 = new AccountApplication();
        accountApplication2.setId(accountApplication1.getId());
        assertThat(accountApplication1).isEqualTo(accountApplication2);
        accountApplication2.setId(2L);
        assertThat(accountApplication1).isNotEqualTo(accountApplication2);
        accountApplication1.setId(null);
        assertThat(accountApplication1).isNotEqualTo(accountApplication2);
    }
}
