package com.example.transaction.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountStateMapperTest {
    private AccountStateMapper accountStateMapper;

    @BeforeEach
    public void setUp() {
        accountStateMapper = new AccountStateMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(accountStateMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(accountStateMapper.fromId(null)).isNull();
    }
}
