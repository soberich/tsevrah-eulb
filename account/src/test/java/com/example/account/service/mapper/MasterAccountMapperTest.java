package com.example.account.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterAccountMapperTest {
    private MasterAccountMapper masterAccountMapper;

    @BeforeEach
    public void setUp() {
        masterAccountMapper = new MasterAccountMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(masterAccountMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(masterAccountMapper.fromId(null)).isNull();
    }
}
