package com.example.transaction.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.transaction.TransactionApp;
import com.example.transaction.domain.AccountState;
import com.example.transaction.domain.MonetaryTransaction;
import com.example.transaction.repository.MonetaryTransactionRepository;
import com.example.transaction.service.MonetaryTransactionService;
import com.example.transaction.service.dto.MonetaryTransactionDTO;
import com.example.transaction.service.mapper.MonetaryTransactionMapper;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MonetaryTransactionResource} REST controller.
 */
@SpringBootTest(classes = TransactionApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MonetaryTransactionResourceIT {
    private static final Long DEFAULT_RECIPIENT_ID = 1L;
    private static final Long UPDATED_RECIPIENT_ID = 2L;

    private static final Long DEFAULT_SENDER_ID = 1L;
    private static final Long UPDATED_SENDER_ID = 2L;

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Autowired
    private MonetaryTransactionRepository monetaryTransactionRepository;

    @Autowired
    private MonetaryTransactionMapper monetaryTransactionMapper;

    @Autowired
    private MonetaryTransactionService monetaryTransactionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMonetaryTransactionMockMvc;

    private MonetaryTransaction monetaryTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MonetaryTransaction createEntity(EntityManager em) {
        MonetaryTransaction monetaryTransaction = new MonetaryTransaction()
            .recipientID(DEFAULT_RECIPIENT_ID)
            .senderID(DEFAULT_SENDER_ID)
            .amount(DEFAULT_AMOUNT);
        // Add required entity
        AccountState accountState;
        if (TestUtil.findAll(em, AccountState.class).isEmpty()) {
            accountState = AccountStateResourceIT.createEntity(em);
            em.persist(accountState);
            em.flush();
        } else {
            accountState = TestUtil.findAll(em, AccountState.class).get(0);
        }
        monetaryTransaction.setAccount(accountState);
        return monetaryTransaction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MonetaryTransaction createUpdatedEntity(EntityManager em) {
        MonetaryTransaction monetaryTransaction = new MonetaryTransaction()
            .recipientID(UPDATED_RECIPIENT_ID)
            .senderID(UPDATED_SENDER_ID)
            .amount(UPDATED_AMOUNT);
        // Add required entity
        AccountState accountState;
        if (TestUtil.findAll(em, AccountState.class).isEmpty()) {
            accountState = AccountStateResourceIT.createUpdatedEntity(em);
            em.persist(accountState);
            em.flush();
        } else {
            accountState = TestUtil.findAll(em, AccountState.class).get(0);
        }
        monetaryTransaction.setAccount(accountState);
        return monetaryTransaction;
    }

    @BeforeEach
    public void initTest() {
        monetaryTransaction = createEntity(em);
    }

    @Test
    @Transactional
    @Disabled
    public void createMonetaryTransaction() throws Exception {
        int databaseSizeBeforeCreate = monetaryTransactionRepository.findAll().size();
        // Create the MonetaryTransaction
        MonetaryTransactionDTO monetaryTransactionDTO = monetaryTransactionMapper.toDto(monetaryTransaction);
        restMonetaryTransactionMockMvc
            .perform(
                post("/api/monetary-transactions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monetaryTransactionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MonetaryTransaction in the database
        List<MonetaryTransaction> monetaryTransactionList = monetaryTransactionRepository.findAll();
        assertThat(monetaryTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        MonetaryTransaction testMonetaryTransaction = monetaryTransactionList.get(monetaryTransactionList.size() - 1);
        assertThat(testMonetaryTransaction.getRecipientID()).isEqualTo(DEFAULT_RECIPIENT_ID);
        assertThat(testMonetaryTransaction.getSenderID()).isEqualTo(DEFAULT_SENDER_ID);
        assertThat(testMonetaryTransaction.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createMonetaryTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = monetaryTransactionRepository.findAll().size();

        // Create the MonetaryTransaction with an existing ID
        monetaryTransaction.setId(1L);
        MonetaryTransactionDTO monetaryTransactionDTO = monetaryTransactionMapper.toDto(monetaryTransaction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonetaryTransactionMockMvc
            .perform(
                post("/api/monetary-transactions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monetaryTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonetaryTransaction in the database
        List<MonetaryTransaction> monetaryTransactionList = monetaryTransactionRepository.findAll();
        assertThat(monetaryTransactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    @Disabled
    public void checkRecipientIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = monetaryTransactionRepository.findAll().size();
        // set the field null
        monetaryTransaction.setRecipientID(null);

        // Create the MonetaryTransaction, which fails.
        MonetaryTransactionDTO monetaryTransactionDTO = monetaryTransactionMapper.toDto(monetaryTransaction);

        restMonetaryTransactionMockMvc
            .perform(
                post("/api/monetary-transactions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monetaryTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        List<MonetaryTransaction> monetaryTransactionList = monetaryTransactionRepository.findAll();
        assertThat(monetaryTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    @Disabled
    public void checkSenderIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = monetaryTransactionRepository.findAll().size();
        // set the field null
        monetaryTransaction.setSenderID(null);

        // Create the MonetaryTransaction, which fails.
        MonetaryTransactionDTO monetaryTransactionDTO = monetaryTransactionMapper.toDto(monetaryTransaction);

        restMonetaryTransactionMockMvc
            .perform(
                post("/api/monetary-transactions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monetaryTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        List<MonetaryTransaction> monetaryTransactionList = monetaryTransactionRepository.findAll();
        assertThat(monetaryTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = monetaryTransactionRepository.findAll().size();
        // set the field null
        monetaryTransaction.setAmount(null);

        // Create the MonetaryTransaction, which fails.
        MonetaryTransactionDTO monetaryTransactionDTO = monetaryTransactionMapper.toDto(monetaryTransaction);

        restMonetaryTransactionMockMvc
            .perform(
                post("/api/monetary-transactions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monetaryTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        List<MonetaryTransaction> monetaryTransactionList = monetaryTransactionRepository.findAll();
        assertThat(monetaryTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMonetaryTransactions() throws Exception {
        // Initialize the database
        monetaryTransactionRepository.saveAndFlush(monetaryTransaction);

        // Get all the monetaryTransactionList
        restMonetaryTransactionMockMvc
            .perform(get("/api/monetary-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monetaryTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].recipientID").value(hasItem(DEFAULT_RECIPIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].senderID").value(hasItem(DEFAULT_SENDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getMonetaryTransaction() throws Exception {
        // Initialize the database
        monetaryTransactionRepository.saveAndFlush(monetaryTransaction);

        // Get the monetaryTransaction
        restMonetaryTransactionMockMvc
            .perform(get("/api/monetary-transactions/{id}", monetaryTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(monetaryTransaction.getId().intValue()))
            .andExpect(jsonPath("$.recipientID").value(DEFAULT_RECIPIENT_ID.intValue()))
            .andExpect(jsonPath("$.senderID").value(DEFAULT_SENDER_ID.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMonetaryTransaction() throws Exception {
        // Get the monetaryTransaction
        restMonetaryTransactionMockMvc.perform(get("/api/monetary-transactions/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMonetaryTransaction() throws Exception {
        // Initialize the database
        monetaryTransactionRepository.saveAndFlush(monetaryTransaction);

        int databaseSizeBeforeUpdate = monetaryTransactionRepository.findAll().size();

        // Update the monetaryTransaction
        MonetaryTransaction updatedMonetaryTransaction = monetaryTransactionRepository.findById(monetaryTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedMonetaryTransaction are not directly saved in db
        em.detach(updatedMonetaryTransaction);
        updatedMonetaryTransaction.recipientID(UPDATED_RECIPIENT_ID).senderID(UPDATED_SENDER_ID).amount(UPDATED_AMOUNT);
        MonetaryTransactionDTO monetaryTransactionDTO = monetaryTransactionMapper.toDto(updatedMonetaryTransaction);

        restMonetaryTransactionMockMvc
            .perform(
                put("/api/monetary-transactions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monetaryTransactionDTO))
            )
            .andExpect(status().isOk());

        // Validate the MonetaryTransaction in the database
        List<MonetaryTransaction> monetaryTransactionList = monetaryTransactionRepository.findAll();
        assertThat(monetaryTransactionList).hasSize(databaseSizeBeforeUpdate);
        MonetaryTransaction testMonetaryTransaction = monetaryTransactionList.get(monetaryTransactionList.size() - 1);
        assertThat(testMonetaryTransaction.getRecipientID()).isEqualTo(UPDATED_RECIPIENT_ID);
        assertThat(testMonetaryTransaction.getSenderID()).isEqualTo(UPDATED_SENDER_ID);
        assertThat(testMonetaryTransaction.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingMonetaryTransaction() throws Exception {
        int databaseSizeBeforeUpdate = monetaryTransactionRepository.findAll().size();

        // Create the MonetaryTransaction
        MonetaryTransactionDTO monetaryTransactionDTO = monetaryTransactionMapper.toDto(monetaryTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonetaryTransactionMockMvc
            .perform(
                put("/api/monetary-transactions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monetaryTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonetaryTransaction in the database
        List<MonetaryTransaction> monetaryTransactionList = monetaryTransactionRepository.findAll();
        assertThat(monetaryTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMonetaryTransaction() throws Exception {
        // Initialize the database
        monetaryTransactionRepository.saveAndFlush(monetaryTransaction);

        int databaseSizeBeforeDelete = monetaryTransactionRepository.findAll().size();

        // Delete the monetaryTransaction
        restMonetaryTransactionMockMvc
            .perform(delete("/api/monetary-transactions/{id}", monetaryTransaction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MonetaryTransaction> monetaryTransactionList = monetaryTransactionRepository.findAll();
        assertThat(monetaryTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
