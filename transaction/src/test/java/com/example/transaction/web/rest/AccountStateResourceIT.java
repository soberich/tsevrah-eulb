package com.example.transaction.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.transaction.TransactionApp;
import com.example.transaction.domain.AccountState;
import com.example.transaction.repository.AccountStateRepository;
import com.example.transaction.service.AccountStateService;
import com.example.transaction.service.dto.AccountStateDTO;
import com.example.transaction.service.mapper.AccountStateMapper;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AccountStateResource} REST controller.
 */
@SpringBootTest(classes = TransactionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AccountStateResourceIT {
    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    @Autowired
    private AccountStateRepository accountStateRepository;

    @Autowired
    private AccountStateMapper accountStateMapper;

    @Autowired
    private AccountStateService accountStateService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountStateMockMvc;

    private AccountState accountState;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountState createEntity(EntityManager em) {
        AccountState accountState = new AccountState().customerID(DEFAULT_CUSTOMER_ID).balance(DEFAULT_BALANCE);
        return accountState;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountState createUpdatedEntity(EntityManager em) {
        AccountState accountState = new AccountState().customerID(UPDATED_CUSTOMER_ID).balance(UPDATED_BALANCE);
        return accountState;
    }

    @BeforeEach
    public void initTest() {
        accountState = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllAccountStates() throws Exception {
        // Initialize the database
        accountStateRepository.saveAndFlush(accountState);

        // Get all the accountStateList
        restAccountStateMockMvc
            .perform(get("/api/account-states?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountState.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerID").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())));
    }

    @Test
    @Transactional
    public void getAccountState() throws Exception {
        // Initialize the database
        accountStateRepository.saveAndFlush(accountState);

        // Get the accountState
        restAccountStateMockMvc
            .perform(get("/api/account-states/{id}", accountState.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accountState.getId().intValue()))
            .andExpect(jsonPath("$.customerID").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAccountState() throws Exception {
        // Get the accountState
        restAccountStateMockMvc.perform(get("/api/account-states/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
