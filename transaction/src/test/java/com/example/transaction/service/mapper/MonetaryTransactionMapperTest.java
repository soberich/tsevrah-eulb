package com.example.transaction.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MonetaryTransactionMapperTest {
    private MonetaryTransactionMapper monetaryTransactionMapper;

    @BeforeEach
    public void setUp() {
        monetaryTransactionMapper = new MonetaryTransactionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(monetaryTransactionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(monetaryTransactionMapper.fromId(null)).isNull();
    }
}
