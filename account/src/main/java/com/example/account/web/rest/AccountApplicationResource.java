package com.example.account.web.rest;

import com.example.account.client.TransactionClient;
import com.example.account.client.UserClient;
import com.example.account.service.AccountApplicationService;
import com.example.account.service.dto.AccountApplicationDTO;
import com.example.account.service.dto.CustomerDetails;
import com.example.account.service.dto.AccountTransaction;
import com.example.account.service.dto.SimpleTransaction;
import com.example.account.service.dto.UserDetails;
import com.example.account.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.*;

import static java.math.BigDecimal.*;

/**
 * REST controller for managing {@link com.example.account.domain.AccountApplication}.
 */
@RestController
@RequestMapping("/api")
public class AccountApplicationResource {
    private final Logger log = LoggerFactory.getLogger(AccountApplicationResource.class);

    private static final String ENTITY_NAME = "accountAccountApplication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Resource(name = "taskExecutor")
    Executor executor;

    private final AccountApplicationService accountApplicationService;
    private final TransactionClient transactionClient;
    private final UserClient userClient;

    public AccountApplicationResource(AccountApplicationService accountApplicationService, TransactionClient transactionClient, UserClient userClient) {
        this.accountApplicationService = accountApplicationService;
        this.transactionClient = transactionClient;
        this.userClient = userClient;
    }

    /**
     * {@code POST  /account-applications} : Create a new accountApplication.
     *
     * @param accountApplicationDTO the accountApplicationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountApplicationDTO, or with status {@code 400 (Bad Request)} if the accountApplication has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-applications")
    public ResponseEntity<AccountApplicationDTO> createAccountApplication(@Valid @RequestBody AccountApplicationDTO accountApplicationDTO)
        throws URISyntaxException {
        log.debug("REST request to save AccountApplication : {}", accountApplicationDTO);
        if (accountApplicationDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountApplication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountApplicationDTO result = accountApplicationService.save(accountApplicationDTO);

        if (accountApplicationDTO.getInitialCredit() != null && !ZERO.equals(accountApplicationDTO.getInitialCredit())) {
            transactionClient.processTransaction(new SimpleTransaction(result.getCustomerID(), accountApplicationDTO.getInitialCredit()));
        }

        return ResponseEntity
                   .created(new URI("/api/account-applications/" + result.getId()))
                   .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                   .body(result);

    }

    /**
     * {@code PUT  /account-applications} : Updates an existing accountApplication.
     *
     * @param accountApplicationDTO the accountApplicationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountApplicationDTO,
     * or with status {@code 400 (Bad Request)} if the accountApplicationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountApplicationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-applications")
    public ResponseEntity<AccountApplicationDTO> updateAccountApplication(@Valid @RequestBody AccountApplicationDTO accountApplicationDTO)
        throws URISyntaxException {
        log.debug("REST request to update AccountApplication : {}", accountApplicationDTO);
        if (accountApplicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountApplicationDTO result = accountApplicationService.save(accountApplicationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountApplicationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /account-applications} : get all the accountApplications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountApplications in body.
     */
    @GetMapping("/account-applications")
    public ResponseEntity<List<AccountApplicationDTO>> getAllAccountApplications(Pageable pageable) {
        log.debug("REST request to get a page of AccountApplications");
        Page<AccountApplicationDTO> page = accountApplicationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /my-account-applications} : get all the accountApplications.
     *
     * @param customerID the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountApplications in body.
     */
    @GetMapping("/my-account-applications/{customerID}")
    public ResponseEntity<CustomerDetails> getAccountDetails(@PathVariable Long customerID) throws URISyntaxException {
        log.debug("REST request to get a page of AccountApplications");
        UserDetails userDetails = userClient.getUserDetails(customerID);
        if (userDetails != null) {

            List<AccountTransaction> transactions = transactionClient.getAllTransactionsForCustomer(customerID);

            CustomerDetails result =
                new CustomerDetails(
                    customerID,
                    userDetails.getFirstName(),
                    userDetails.getLastName(),
                    transactions.stream().map(AccountTransaction::getAmount).reduce(ZERO, BigDecimal::add),
                    transactions);

            return ResponseEntity.ok(result);
        }
        throw new BadRequestAlertException("Invalid customerID, no user exist with such id", ENTITY_NAME, "idnull");
    }

    /**
     * {@code GET  /account-applications/:id} : get the "id" accountApplication.
     *
     * @param id the id of the accountApplicationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountApplicationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-applications/{id}")
    public ResponseEntity<AccountApplicationDTO> getAccountApplication(@PathVariable Long id) {
        log.debug("REST request to get AccountApplication : {}", id);
        Optional<AccountApplicationDTO> accountApplicationDTO = accountApplicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountApplicationDTO);
    }

    /**
     * {@code DELETE  /account-applications/:id} : delete the "id" accountApplication.
     *
     * @param id the id of the accountApplicationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-applications/{id}")
    public ResponseEntity<Void> deleteAccountApplication(@PathVariable Long id) {
        log.debug("REST request to delete AccountApplication : {}", id);
        accountApplicationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
