package com.example.transaction.web.rest;

import com.example.transaction.service.AccountStateService;
import com.example.transaction.service.dto.AccountStateDTO;
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
 * REST controller for managing {@link com.example.transaction.domain.AccountState}.
 */
@RestController
@RequestMapping("/api")
public class AccountStateResource {
    private final Logger log = LoggerFactory.getLogger(AccountStateResource.class);

    private final AccountStateService accountStateService;

    public AccountStateResource(AccountStateService accountStateService) {
        this.accountStateService = accountStateService;
    }

    /**
     * {@code GET  /account-states} : get all the accountStates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountStates in body.
     */
    @GetMapping("/account-states")
    public ResponseEntity<List<AccountStateDTO>> getAllAccountStates(Pageable pageable) {
        log.debug("REST request to get a page of AccountStates");
        Page<AccountStateDTO> page = accountStateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /account-states/:id} : get the "id" accountState.
     *
     * @param id the id of the accountStateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountStateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-states/{id}")
    public ResponseEntity<AccountStateDTO> getAccountState(@PathVariable Long id) {
        log.debug("REST request to get AccountState : {}", id);
        Optional<AccountStateDTO> accountStateDTO = accountStateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountStateDTO);
    }
}
