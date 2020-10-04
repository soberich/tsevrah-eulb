package com.example.account.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountApplicationMapperTest {
    private AccountApplicationMapper accountApplicationMapper;

    @BeforeEach
    public void setUp() {
        accountApplicationMapper = new AccountApplicationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(accountApplicationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(accountApplicationMapper.fromId(null)).isNull();
    }
}
