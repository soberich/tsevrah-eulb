package com.example.transaction.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.transaction.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class MonetaryTransactionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonetaryTransactionDTO.class);
        MonetaryTransactionDTO monetaryTransactionDTO1 = new MonetaryTransactionDTO();
        monetaryTransactionDTO1.setId(1L);
        MonetaryTransactionDTO monetaryTransactionDTO2 = new MonetaryTransactionDTO();
        assertThat(monetaryTransactionDTO1).isNotEqualTo(monetaryTransactionDTO2);
        monetaryTransactionDTO2.setId(monetaryTransactionDTO1.getId());
        assertThat(monetaryTransactionDTO1).isEqualTo(monetaryTransactionDTO2);
        monetaryTransactionDTO2.setId(2L);
        assertThat(monetaryTransactionDTO1).isNotEqualTo(monetaryTransactionDTO2);
        monetaryTransactionDTO1.setId(null);
        assertThat(monetaryTransactionDTO1).isNotEqualTo(monetaryTransactionDTO2);
    }
}
