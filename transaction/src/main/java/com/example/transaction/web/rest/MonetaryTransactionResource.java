package com.example.transaction.web.rest;

import com.example.transaction.service.MonetaryTransactionService;
import com.example.transaction.service.dto.MonetaryTransactionDTO;
import com.example.transaction.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link com.example.transaction.domain.MonetaryTransaction}.
 */
@RestController
@RequestMapping("/api")
public class MonetaryTransactionResource {
    private final Logger log = LoggerFactory.getLogger(MonetaryTransactionResource.class);

    private static final String ENTITY_NAME = "transactionMonetaryTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MonetaryTransactionService monetaryTransactionService;

    public MonetaryTransactionResource(MonetaryTransactionService monetaryTransactionService) {
        this.monetaryTransactionService = monetaryTransactionService;
    }

    /**
     * {@code POST  /monetary-transactions} : Create a new monetaryTransaction.
     *
     * @param monetaryTransactionDTO the monetaryTransactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new monetaryTransactionDTO, or with status {@code 400 (Bad Request)} if the monetaryTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/monetary-transactions")
    public ResponseEntity<MonetaryTransactionDTO> createMonetaryTransaction(
        @Valid @RequestBody MonetaryTransactionDTO monetaryTransactionDTO
    )
        throws URISyntaxException {
        log.debug("REST request to save MonetaryTransaction : {}", monetaryTransactionDTO);
        if (monetaryTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new monetaryTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MonetaryTransactionDTO result = monetaryTransactionService.save(monetaryTransactionDTO);
        return ResponseEntity
            .created(new URI("/api/monetary-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /monetary-transactions} : Updates an existing monetaryTransaction.
     *
     * @param monetaryTransactionDTO the monetaryTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monetaryTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the monetaryTransactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the monetaryTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/monetary-transactions")
    public ResponseEntity<MonetaryTransactionDTO> updateMonetaryTransaction(
        @Valid @RequestBody MonetaryTransactionDTO monetaryTransactionDTO
    )
        throws URISyntaxException {
        log.debug("REST request to update MonetaryTransaction : {}", monetaryTransactionDTO);
        if (monetaryTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MonetaryTransactionDTO result = monetaryTransactionService.save(monetaryTransactionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, monetaryTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /monetary-transactions} : get all the monetaryTransactions.
     *
     * @param customerID customerID.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of monetaryTransactions in body.
     */
    @GetMapping("/my-monetary-transactions/{customerID}")
    public ResponseEntity<List<MonetaryTransactionDTO>> getAllMonetaryTransactionsForAccount(@PathVariable Long customerID) {
        log.debug("REST request to get a page of MonetaryTransactions");
        List<MonetaryTransactionDTO> page = monetaryTransactionService.findByAccount(customerID);
        return ResponseEntity.ok(page);
    }

    /**
     * {@code GET  /monetary-transactions} : get all the monetaryTransactions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of monetaryTransactions in body.
     */
    @GetMapping("/monetary-transactions")
    public ResponseEntity<List<MonetaryTransactionDTO>> getAllMonetaryTransactions(Pageable pageable) {
        log.debug("REST request to get a page of MonetaryTransactions");
        Page<MonetaryTransactionDTO> page = monetaryTransactionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /monetary-transactions/:id} : get the "id" monetaryTransaction.
     *
     * @param id the id of the monetaryTransactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the monetaryTransactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/monetary-transactions/{id}")
    public ResponseEntity<MonetaryTransactionDTO> getMonetaryTransaction(@PathVariable Long id) {
        log.debug("REST request to get MonetaryTransaction : {}", id);
        Optional<MonetaryTransactionDTO> monetaryTransactionDTO = monetaryTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(monetaryTransactionDTO);
    }

    /**
     * {@code DELETE  /monetary-transactions/:id} : delete the "id" monetaryTransaction.
     *
     * @param id the id of the monetaryTransactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/monetary-transactions/{id}")
    public ResponseEntity<Void> deleteMonetaryTransaction(@PathVariable Long id) {
        log.debug("REST request to delete MonetaryTransaction : {}", id);
        monetaryTransactionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
