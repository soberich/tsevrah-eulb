package com.example.transaction.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.transaction.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class MonetaryTransactionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonetaryTransaction.class);
        MonetaryTransaction monetaryTransaction1 = new MonetaryTransaction();
        monetaryTransaction1.setId(1L);
        MonetaryTransaction monetaryTransaction2 = new MonetaryTransaction();
        monetaryTransaction2.setId(monetaryTransaction1.getId());
        assertThat(monetaryTransaction1).isEqualTo(monetaryTransaction2);
        monetaryTransaction2.setId(2L);
        assertThat(monetaryTransaction1).isNotEqualTo(monetaryTransaction2);
        monetaryTransaction1.setId(null);
        assertThat(monetaryTransaction1).isNotEqualTo(monetaryTransaction2);
    }
}
