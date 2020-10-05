package com.example.account.web.rest;

import com.example.account.AccountApp;
import com.example.account.client.TransactionClient;
import com.example.account.client.UserClient;
import com.example.account.domain.AccountApplication;
import com.example.account.repository.AccountApplicationRepository;
import com.example.account.service.AccountApplicationService;
import com.example.account.service.dto.AccountApplyCommand;
import com.example.account.service.dto.TransactionCommand;
import com.example.account.service.dto.TransactionView;
import com.example.account.service.dto.UserDetailedView;
import com.example.account.service.mapper.AccountApplicationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AccountApplicationResource} REST controller.
 */
@SpringBootTest(classes = AccountApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AccountApplicationResourceIT {
    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    private static final Long DEFAULT_ACCOUNT_ID = 1L;
    private static final Long UPDATED_ACCOUNT_ID = 2L;

    private static final BigDecimal DEFAULT_INITIAL_CREDIT = BigDecimal.ONE;
    private static final BigDecimal UPDATED_INITIAL_CREDIT = BigDecimal.valueOf(2L);

    @Autowired
    private AccountApplicationRepository accountApplicationRepository;

    @Autowired
    private AccountApplicationMapper accountApplicationMapper;

    @Autowired
    private AccountApplicationService accountApplicationService;

    @MockBean
    private TransactionClient transactionClient;

    @MockBean
    private UserClient userClient;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountApplicationMockMvc;

    private AccountApplication accountApplication;

    /**
     * Create an entity for this test.
     * <p>
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
     * <p>
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
        doReturn(new TransactionView(DEFAULT_CUSTOMER_ID, DEFAULT_ACCOUNT_ID, DEFAULT_INITIAL_CREDIT))
            .when(transactionClient).processTransaction(any(TransactionCommand.class));

        doReturn(
            new UserDetailedView()
                .setId(DEFAULT_CUSTOMER_ID)
                .setFirstName("admin")
                .setLastName("admin")
        ).when(userClient).getUserDetails(DEFAULT_CUSTOMER_ID);

        doReturn(
            List.of(
                new TransactionView(DEFAULT_CUSTOMER_ID, DEFAULT_ACCOUNT_ID, BigDecimal.TEN),
                new TransactionView(DEFAULT_CUSTOMER_ID, DEFAULT_ACCOUNT_ID, BigDecimal.TEN)
            )
        ).when(transactionClient).getAllTransactionsForCustomer(DEFAULT_CUSTOMER_ID);

        accountApplication = createEntity(em);
    }

    @Test
    @Transactional
    @DisplayName("+ The API will expose an endpoint which accepts the user information (customerID, initialCredit).\n" +
                 "+ Also, if initialCredit is not 0, a transaction will be sent to the new account.")
    public void createAccountApplication() throws Exception {
        int databaseSizeBeforeCreate = accountApplicationRepository.findAll().size();
        // Create the AccountApplication
        AccountApplyCommand accountApplicationDTO = accountApplicationMapper.toDto(accountApplication);
        accountApplicationDTO.setInitialCredit(DEFAULT_INITIAL_CREDIT);
        restAccountApplicationMockMvc
            .perform(
                post("/api/account-applications")
                    .contentType(APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountApplicationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AccountApplication in the database
        List<AccountApplication> accountApplicationList = accountApplicationRepository.findAll();
        assertThat(accountApplicationList).hasSize(databaseSizeBeforeCreate + 1);
        AccountApplication testAccountApplication = accountApplicationList.get(accountApplicationList.size() - 1);
        assertThat(testAccountApplication.getCustomerID()).isEqualTo(DEFAULT_CUSTOMER_ID);
        verify(transactionClient).processTransaction(notNull());
    }

    @Test
    @Transactional
    @DisplayName("+ Once the endpoint is called, a new account will be opened connected to the user whose ID is customerID.")
    public void createAccountApplicationAndConnectToUserWithCustomerID() throws Exception {
        int databaseSizeBeforeCreate = accountApplicationRepository.findAll().size();
        // Create the AccountApplication
        AccountApplyCommand accountApplicationDTO = accountApplicationMapper.toDto(accountApplication);
        accountApplicationDTO.setInitialCredit(DEFAULT_INITIAL_CREDIT);
        UserDetailedView userDetails = userClient.getUserDetails(DEFAULT_CUSTOMER_ID);
        restAccountApplicationMockMvc
            .perform(
                post("/api/account-applications")
                    .contentType(APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountApplicationDTO))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.customerID").value(userDetails.getId()));
    }

    @Test
    @Transactional
    public void createAccountApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountApplicationRepository.findAll().size();

        // Create the AccountApplication with an existing ID
        accountApplication.setId(1L);
        AccountApplyCommand accountApplicationDTO = accountApplicationMapper.toDto(accountApplication);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountApplicationMockMvc
            .perform(
                post("/api/account-applications")
                    .contentType(APPLICATION_JSON)
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
        AccountApplyCommand accountApplicationDTO = accountApplicationMapper.toDto(accountApplication);

        restAccountApplicationMockMvc
            .perform(
                post("/api/account-applications")
                    .contentType(APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountApplicationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AccountApplication> accountApplicationList = accountApplicationRepository.findAll();
        assertThat(accountApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    @DisplayName("Another Endpoint will output the user information showing Name, Surname, balance, and transactions of the accounts.")
    public void outputUserInformation() throws Exception {
        // Initialize the database
        accountApplicationRepository.saveAndFlush(accountApplication);

        restAccountApplicationMockMvc
            .perform(get("/api/my-account-applications/{customerID}", DEFAULT_CUSTOMER_ID))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.customerID").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.name").value("admin"))
            .andExpect(jsonPath("$.surname").value("admin"))
            .andExpect(jsonPath("$.balance").value(BigDecimal.valueOf(20L)))
            .andExpect(jsonPath("$.transactions").value(hasSize(2)));
    }

    @Test
    @Transactional
    @DisplayName("Another Endpoint will output the user information showing Name, Surname, balance, and transactions of the accounts.")
    public void getAllAccountApplications() throws Exception {
        // Initialize the database
        accountApplicationRepository.saveAndFlush(accountApplication);

        // Get all the accountApplicationList
        restAccountApplicationMockMvc
            .perform(get("/api/account-applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
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
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
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
        AccountApplyCommand accountApplicationDTO = accountApplicationMapper.toDto(updatedAccountApplication);

        restAccountApplicationMockMvc
            .perform(
                put("/api/account-applications")
                    .contentType(APPLICATION_JSON)
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
        AccountApplyCommand accountApplicationDTO = accountApplicationMapper.toDto(accountApplication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountApplicationMockMvc
            .perform(
                put("/api/account-applications")
                    .contentType(APPLICATION_JSON)
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
            .perform(delete("/api/account-applications/{id}", accountApplication.getId()).accept(APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountApplication> accountApplicationList = accountApplicationRepository.findAll();
        assertThat(accountApplicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
