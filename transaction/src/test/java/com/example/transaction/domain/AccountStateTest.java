package com.example.transaction.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.transaction.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class AccountStateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountState.class);
        AccountState accountState1 = new AccountState();
        accountState1.setId(1L);
        AccountState accountState2 = new AccountState();
        accountState2.setId(accountState1.getId());
        assertThat(accountState1).isEqualTo(accountState2);
        accountState2.setId(2L);
        assertThat(accountState1).isNotEqualTo(accountState2);
        accountState1.setId(null);
        assertThat(accountState1).isNotEqualTo(accountState2);
    }
}
