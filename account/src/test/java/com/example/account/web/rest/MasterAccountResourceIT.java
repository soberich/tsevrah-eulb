package com.example.account.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.account.AccountApp;
import com.example.account.domain.MasterAccount;
import com.example.account.repository.MasterAccountRepository;
import com.example.account.service.MasterAccountService;
import com.example.account.service.dto.MasterAccountDTO;
import com.example.account.service.mapper.MasterAccountMapper;
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
 * Integration tests for the {@link MasterAccountResource} REST controller.
 */
@SpringBootTest(classes = AccountApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MasterAccountResourceIT {
    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    private static final BigDecimal DEFAULT_INITIAL_CREDIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_INITIAL_CREDIT = new BigDecimal(2);

    @Autowired
    private MasterAccountRepository masterAccountRepository;

    @Autowired
    private MasterAccountMapper masterAccountMapper;

    @Autowired
    private MasterAccountService masterAccountService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMasterAccountMockMvc;

    private MasterAccount masterAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MasterAccount createEntity(EntityManager em) {
        MasterAccount masterAccount = new MasterAccount().customerID(DEFAULT_CUSTOMER_ID).initialCredit(DEFAULT_INITIAL_CREDIT);
        return masterAccount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MasterAccount createUpdatedEntity(EntityManager em) {
        MasterAccount masterAccount = new MasterAccount().customerID(UPDATED_CUSTOMER_ID).initialCredit(UPDATED_INITIAL_CREDIT);
        return masterAccount;
    }

    @BeforeEach
    public void initTest() {
        masterAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createMasterAccount() throws Exception {
        int databaseSizeBeforeCreate = masterAccountRepository.findAll().size();
        // Create the MasterAccount
        MasterAccountDTO masterAccountDTO = masterAccountMapper.toDto(masterAccount);
        restMasterAccountMockMvc
            .perform(
                post("/api/master-accounts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(masterAccountDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MasterAccount in the database
        List<MasterAccount> masterAccountList = masterAccountRepository.findAll();
        assertThat(masterAccountList).hasSize(databaseSizeBeforeCreate + 1);
        MasterAccount testMasterAccount = masterAccountList.get(masterAccountList.size() - 1);
        assertThat(testMasterAccount.getCustomerID()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testMasterAccount.getInitialCredit()).isEqualTo(DEFAULT_INITIAL_CREDIT);
    }

    @Test
    @Transactional
    public void createMasterAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = masterAccountRepository.findAll().size();

        // Create the MasterAccount with an existing ID
        masterAccount.setId(1L);
        MasterAccountDTO masterAccountDTO = masterAccountMapper.toDto(masterAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMasterAccountMockMvc
            .perform(
                post("/api/master-accounts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(masterAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MasterAccount in the database
        List<MasterAccount> masterAccountList = masterAccountRepository.findAll();
        assertThat(masterAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCustomerIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = masterAccountRepository.findAll().size();
        // set the field null
        masterAccount.setCustomerID(null);

        // Create the MasterAccount, which fails.
        MasterAccountDTO masterAccountDTO = masterAccountMapper.toDto(masterAccount);

        restMasterAccountMockMvc
            .perform(
                post("/api/master-accounts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(masterAccountDTO))
            )
            .andExpect(status().isBadRequest());

        List<MasterAccount> masterAccountList = masterAccountRepository.findAll();
        assertThat(masterAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMasterAccounts() throws Exception {
        // Initialize the database
        masterAccountRepository.saveAndFlush(masterAccount);

        // Get all the masterAccountList
        restMasterAccountMockMvc
            .perform(get("/api/master-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(masterAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerID").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].initialCredit").value(hasItem(DEFAULT_INITIAL_CREDIT.intValue())));
    }

    @Test
    @Transactional
    public void getMasterAccount() throws Exception {
        // Initialize the database
        masterAccountRepository.saveAndFlush(masterAccount);

        // Get the masterAccount
        restMasterAccountMockMvc
            .perform(get("/api/master-accounts/{id}", masterAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(masterAccount.getId().intValue()))
            .andExpect(jsonPath("$.customerID").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.initialCredit").value(DEFAULT_INITIAL_CREDIT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMasterAccount() throws Exception {
        // Get the masterAccount
        restMasterAccountMockMvc.perform(get("/api/master-accounts/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMasterAccount() throws Exception {
        // Initialize the database
        masterAccountRepository.saveAndFlush(masterAccount);

        int databaseSizeBeforeUpdate = masterAccountRepository.findAll().size();

        // Update the masterAccount
        MasterAccount updatedMasterAccount = masterAccountRepository.findById(masterAccount.getId()).get();
        // Disconnect from session so that the updates on updatedMasterAccount are not directly saved in db
        em.detach(updatedMasterAccount);
        updatedMasterAccount.customerID(UPDATED_CUSTOMER_ID).initialCredit(UPDATED_INITIAL_CREDIT);
        MasterAccountDTO masterAccountDTO = masterAccountMapper.toDto(updatedMasterAccount);

        restMasterAccountMockMvc
            .perform(
                put("/api/master-accounts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(masterAccountDTO))
            )
            .andExpect(status().isOk());

        // Validate the MasterAccount in the database
        List<MasterAccount> masterAccountList = masterAccountRepository.findAll();
        assertThat(masterAccountList).hasSize(databaseSizeBeforeUpdate);
        MasterAccount testMasterAccount = masterAccountList.get(masterAccountList.size() - 1);
        assertThat(testMasterAccount.getCustomerID()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testMasterAccount.getInitialCredit()).isEqualTo(UPDATED_INITIAL_CREDIT);
    }

    @Test
    @Transactional
    public void updateNonExistingMasterAccount() throws Exception {
        int databaseSizeBeforeUpdate = masterAccountRepository.findAll().size();

        // Create the MasterAccount
        MasterAccountDTO masterAccountDTO = masterAccountMapper.toDto(masterAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMasterAccountMockMvc
            .perform(
                put("/api/master-accounts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(masterAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MasterAccount in the database
        List<MasterAccount> masterAccountList = masterAccountRepository.findAll();
        assertThat(masterAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMasterAccount() throws Exception {
        // Initialize the database
        masterAccountRepository.saveAndFlush(masterAccount);

        int databaseSizeBeforeDelete = masterAccountRepository.findAll().size();

        // Delete the masterAccount
        restMasterAccountMockMvc
            .perform(delete("/api/master-accounts/{id}", masterAccount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MasterAccount> masterAccountList = masterAccountRepository.findAll();
        assertThat(masterAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
