package com.example.account.service;

import com.example.account.domain.AccountApplication;
import com.example.account.repository.AccountApplicationRepository;
import com.example.account.service.dto.AccountApplyCommand;
import com.example.account.service.mapper.AccountApplicationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AccountApplication}.
 */
@Service
@Transactional
public class AccountApplicationService {
    private final Logger log = LoggerFactory.getLogger(AccountApplicationService.class);

    private final AccountApplicationRepository accountApplicationRepository;

    private final AccountApplicationMapper accountApplicationMapper;

    public AccountApplicationService(
        AccountApplicationRepository accountApplicationRepository,
        AccountApplicationMapper accountApplicationMapper
    ) {
        this.accountApplicationRepository = accountApplicationRepository;
        this.accountApplicationMapper = accountApplicationMapper;
    }

    /**
     * Save a accountApplication.
     *
     * @param accountApplicationDTO the entity to save.
     * @return the persisted entity.
     */
    public AccountApplyCommand save(AccountApplyCommand accountApplicationDTO) {
        log.debug("Request to save AccountApplication : {}", accountApplicationDTO);
        AccountApplication accountApplication = accountApplicationMapper.toEntity(accountApplicationDTO);
        accountApplication = accountApplicationRepository.save(accountApplication);
        return accountApplicationMapper.toDto(accountApplication);
    }

    /**
     * Get all the accountApplications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AccountApplyCommand> findAll(Pageable pageable) {
        log.debug("Request to get all AccountApplications");
        return accountApplicationRepository.findAll(pageable).map(accountApplicationMapper::toDto);
    }

    /**
     * Get one accountApplication by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AccountApplyCommand> findOne(Long id) {
        log.debug("Request to get AccountApplication : {}", id);
        return accountApplicationRepository.findById(id).map(accountApplicationMapper::toDto);
    }

    /**
     * Delete the accountApplication by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AccountApplication : {}", id);
        accountApplicationRepository.deleteById(id);
    }
}
