package com.example.account.web.rest;

import com.example.account.service.MasterAccountService;
import com.example.account.service.dto.MasterAccountDTO;
import com.example.account.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.example.account.domain.MasterAccount}.
 */
@RestController
@RequestMapping("/api")
public class MasterAccountResource {
    private final Logger log = LoggerFactory.getLogger(MasterAccountResource.class);

    private static final String ENTITY_NAME = "accountMasterAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MasterAccountService masterAccountService;

    public MasterAccountResource(MasterAccountService masterAccountService) {
        this.masterAccountService = masterAccountService;
    }

    /**
     * {@code POST  /master-accounts} : Create a new masterAccount.
     *
     * @param masterAccountDTO the masterAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new masterAccountDTO, or with status {@code 400 (Bad Request)} if the masterAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/master-accounts")
    public ResponseEntity<MasterAccountDTO> createMasterAccount(@Valid @RequestBody MasterAccountDTO masterAccountDTO)
        throws URISyntaxException {
        log.debug("REST request to save MasterAccount : {}", masterAccountDTO);
        if (masterAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new masterAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MasterAccountDTO result = masterAccountService.save(masterAccountDTO);
        return ResponseEntity
            .created(new URI("/api/master-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /master-accounts} : Updates an existing masterAccount.
     *
     * @param masterAccountDTO the masterAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated masterAccountDTO,
     * or with status {@code 400 (Bad Request)} if the masterAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the masterAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/master-accounts")
    public ResponseEntity<MasterAccountDTO> updateMasterAccount(@Valid @RequestBody MasterAccountDTO masterAccountDTO)
        throws URISyntaxException {
        log.debug("REST request to update MasterAccount : {}", masterAccountDTO);
        if (masterAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MasterAccountDTO result = masterAccountService.save(masterAccountDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, masterAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /master-accounts} : get all the masterAccounts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of masterAccounts in body.
     */
    @GetMapping("/master-accounts")
    public ResponseEntity<List<MasterAccountDTO>> getAllMasterAccounts(Pageable pageable) {
        log.debug("REST request to get a page of MasterAccounts");
        Page<MasterAccountDTO> page = masterAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /master-accounts/:id} : get the "id" masterAccount.
     *
     * @param id the id of the masterAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the masterAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/master-accounts/{id}")
    public ResponseEntity<MasterAccountDTO> getMasterAccount(@PathVariable Long id) {
        log.debug("REST request to get MasterAccount : {}", id);
        Optional<MasterAccountDTO> masterAccountDTO = masterAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(masterAccountDTO);
    }

    /**
     * {@code DELETE  /master-accounts/:id} : delete the "id" masterAccount.
     *
     * @param id the id of the masterAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/master-accounts/{id}")
    public ResponseEntity<Void> deleteMasterAccount(@PathVariable Long id) {
        log.debug("REST request to delete MasterAccount : {}", id);
        masterAccountService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
