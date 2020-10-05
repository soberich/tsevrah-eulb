package com.example.account.web.rest;

import com.example.account.client.TransactionClient;
import com.example.account.service.AccountApplicationService;
import com.example.account.service.dto.AccountApplicationDTO;
import com.example.account.web.rest.errors.BadRequestAlertException;
import com.example.account.web.rest.vm.SimpleTransaction;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    public AccountApplicationResource(AccountApplicationService accountApplicationService, TransactionClient transactionClient) {
        this.accountApplicationService = accountApplicationService;
        this.transactionClient = transactionClient;
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
        if (accountApplicationDTO.getInitialCredit() != null && !BigDecimal.ZERO.equals(accountApplicationDTO.getInitialCredit())) {
            //fire-and-forget
            CompletableFuture.runAsync(
                () -> transactionClient.processTransaction(new SimpleTransaction(result.getCustomerID(), accountApplicationDTO.getInitialCredit())),
                executor
            );
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
