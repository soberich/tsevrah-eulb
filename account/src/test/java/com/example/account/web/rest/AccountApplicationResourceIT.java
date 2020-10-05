package com.example.account.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.account.AccountApp;
import com.example.account.domain.AccountApplication;
import com.example.account.repository.AccountApplicationRepository;
import com.example.account.service.AccountApplicationService;
import com.example.account.service.dto.AccountApplicationDTO;
import com.example.account.service.mapper.AccountApplicationMapper;
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
 * Integration tests for the {@link AccountApplicationResource} REST controller.
 */
@SpringBootTest(classes = AccountApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AccountApplicationResourceIT {
    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    private static final BigDecimal DEFAULT_INITIAL_CREDIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_INITIAL_CREDIT = new BigDecimal(2);

    @Autowired
    private AccountApplicationRepository accountApplicationRepository;

    @Autowired
    private AccountApplicationMapper accountApplicationMapper;

    @Autowired
    private AccountApplicationService accountApplicationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountApplicationMockMvc;

    private AccountApplication accountApplication;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountApplication createEntity(EntityManager em) {
        AccountApplication accountApplication = new AccountApplication()
            .customerID(DEFAULT_CUSTOMER_ID);
        return accountApplication;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountApplication createUpdatedEntity(EntityManager em) {
        AccountApplication accountApplication = new AccountApplication()
            .customerID(UPDATED_CUSTOMER_ID);
        return accountApplication;
    }

    @BeforeEach
    public void initTest() {
        accountApplication = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountApplication() throws Exception {
        int databaseSizeBeforeCreate = accountApplicationRepository.findAll().size();
        // Create the AccountApplication
        AccountApplicationDTO accountApplicationDTO = accountApplicationMapper.toDto(accountApplication);
        restAccountApplicationMockMvc
            .perform(
                post("/api/account-applications")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountApplicationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AccountApplication in the database
        List<AccountApplication> accountApplicationList = accountApplicationRepository.findAll();
        assertThat(accountApplicationList).hasSize(databaseSizeBeforeCreate + 1);
        AccountApplication testAccountApplication = accountApplicationList.get(accountApplicationList.size() - 1);
        assertThat(testAccountApplication.getCustomerID()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    public void createAccountApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountApplicationRepository.findAll().size();

        // Create the AccountApplication with an existing ID
        accountApplication.setId(1L);
        AccountApplicationDTO accountApplicationDTO = accountApplicationMapper.toDto(accountApplication);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountApplicationMockMvc
            .perform(
                post("/api/account-applications")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountApplicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountApplication in the database
        List<AccountApplication> accountApplicationList = accountApplicationRepository.findAll();
        assertThat(accountApplicationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCustomerIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountApplicationRepository.findAll().size();
        // set the field null
        accountApplication.setCustomerID(null);

        // Create the AccountApplication, which fails.
        AccountApplicationDTO accountApplicationDTO = accountApplicationMapper.toDto(accountApplication);

        restAccountApplicationMockMvc
            .perform(
                post("/api/account-applications")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountApplicationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AccountApplication> accountApplicationList = accountApplicationRepository.findAll();
        assertThat(accountApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAccountApplications() throws Exception {
        // Initialize the database
        accountApplicationRepository.saveAndFlush(accountApplication);

        // Get all the accountApplicationList
        restAccountApplicationMockMvc
            .perform(get("/api/account-applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerID").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getAccountApplication() throws Exception {
        // Initialize the database
        accountApplicationRepository.saveAndFlush(accountApplication);

        // Get the accountApplication
        restAccountApplicationMockMvc
            .perform(get("/api/account-applications/{id}", accountApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accountApplication.getId().intValue()))
            .andExpect(jsonPath("$.customerID").value(DEFAULT_CUSTOMER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAccountApplication() throws Exception {
        // Get the accountApplication
        restAccountApplicationMockMvc.perform(get("/api/account-applications/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountApplication() throws Exception {
        // Initialize the database
        accountApplicationRepository.saveAndFlush(accountApplication);

        int databaseSizeBeforeUpdate = accountApplicationRepository.findAll().size();

        // Update the accountApplication
        AccountApplication updatedAccountApplication = accountApplicationRepository.findById(accountApplication.getId()).get();
        // Disconnect from session so that the updates on updatedAccountApplication are not directly saved in db
        em.detach(updatedAccountApplication);
        updatedAccountApplication.customerID(UPDATED_CUSTOMER_ID);
        AccountApplicationDTO accountApplicationDTO = accountApplicationMapper.toDto(updatedAccountApplication);

        restAccountApplicationMockMvc
            .perform(
                put("/api/account-applications")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountApplicationDTO))
            )
            .andExpect(status().isOk());

        // Validate the AccountApplication in the database
        List<AccountApplication> accountApplicationList = accountApplicationRepository.findAll();
        assertThat(accountApplicationList).hasSize(databaseSizeBeforeUpdate);
        AccountApplication testAccountApplication = accountApplicationList.get(accountApplicationList.size() - 1);
        assertThat(testAccountApplication.getCustomerID()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountApplication() throws Exception {
        int databaseSizeBeforeUpdate = accountApplicationRepository.findAll().size();

        // Create the AccountApplication
        AccountApplicationDTO accountApplicationDTO = accountApplicationMapper.toDto(accountApplication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountApplicationMockMvc
            .perform(
                put("/api/account-applications")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountApplicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountApplication in the database
        List<AccountApplication> accountApplicationList = accountApplicationRepository.findAll();
        assertThat(accountApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccountApplication() throws Exception {
        // Initialize the database
        accountApplicationRepository.saveAndFlush(accountApplication);

        int databaseSizeBeforeDelete = accountApplicationRepository.findAll().size();

        // Delete the accountApplication
        restAccountApplicationMockMvc
            .perform(delete("/api/account-applications/{id}", accountApplication.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountApplication> accountApplicationList = accountApplicationRepository.findAll();
        assertThat(accountApplicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
