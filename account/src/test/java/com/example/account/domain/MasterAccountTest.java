package com.example.account.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.account.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class MasterAccountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MasterAccount.class);
        MasterAccount masterAccount1 = new MasterAccount();
        masterAccount1.setId(1L);
        MasterAccount masterAccount2 = new MasterAccount();
        masterAccount2.setId(masterAccount1.getId());
        assertThat(masterAccount1).isEqualTo(masterAccount2);
        masterAccount2.setId(2L);
        assertThat(masterAccount1).isNotEqualTo(masterAccount2);
        masterAccount1.setId(null);
        assertThat(masterAccount1).isNotEqualTo(masterAccount2);
    }
}
