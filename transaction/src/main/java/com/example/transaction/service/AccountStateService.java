package com.example.transaction.service;

import com.example.transaction.domain.AccountState;
import com.example.transaction.repository.AccountStateRepository;
import com.example.transaction.service.dto.AccountStateDTO;
import com.example.transaction.service.mapper.AccountStateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AccountState}.
 */
@Service
@Transactional
public class AccountStateService {
    private final Logger log = LoggerFactory.getLogger(AccountStateService.class);

    private final AccountStateRepository accountStateRepository;

    private final AccountStateMapper accountStateMapper;

    public AccountStateService(AccountStateRepository accountStateRepository, AccountStateMapper accountStateMapper) {
        this.accountStateRepository = accountStateRepository;
        this.accountStateMapper = accountStateMapper;
    }

    /**
     * Save a accountState.
     *
     * @param accountStateDTO the entity to save.
     * @return the persisted entity.
     */
    public AccountStateDTO save(AccountStateDTO accountStateDTO) {
        log.debug("Request to save AccountState : {}", accountStateDTO);
        AccountState accountState = accountStateMapper.toEntity(accountStateDTO);
        accountState = accountStateRepository.save(accountState);
        return accountStateMapper.toDto(accountState);
    }

    /**
     * Get all the accountStates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AccountStateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AccountStates");
        return accountStateRepository.findAll(pageable).map(accountStateMapper::toDto);
    }

    /**
     * Get one accountState by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AccountStateDTO> findOne(Long id) {
        log.debug("Request to get AccountState : {}", id);
        return accountStateRepository.findById(id).map(accountStateMapper::toDto);
    }

    /**
     * Delete the accountState by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AccountState : {}", id);
        accountStateRepository.deleteById(id);
    }
}
