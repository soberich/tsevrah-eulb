package com.example.account.service;

import com.example.account.domain.MasterAccount;
import com.example.account.repository.MasterAccountRepository;
import com.example.account.service.dto.MasterAccountDTO;
import com.example.account.service.mapper.MasterAccountMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MasterAccount}.
 */
@Service
@Transactional
public class MasterAccountService {
    private final Logger log = LoggerFactory.getLogger(MasterAccountService.class);

    private final MasterAccountRepository masterAccountRepository;

    private final MasterAccountMapper masterAccountMapper;

    public MasterAccountService(MasterAccountRepository masterAccountRepository, MasterAccountMapper masterAccountMapper) {
        this.masterAccountRepository = masterAccountRepository;
        this.masterAccountMapper = masterAccountMapper;
    }

    /**
     * Save a masterAccount.
     *
     * @param masterAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public MasterAccountDTO save(MasterAccountDTO masterAccountDTO) {
        log.debug("Request to save MasterAccount : {}", masterAccountDTO);
        MasterAccount masterAccount = masterAccountMapper.toEntity(masterAccountDTO);
        masterAccount = masterAccountRepository.save(masterAccount);
        return masterAccountMapper.toDto(masterAccount);
    }

    /**
     * Get all the masterAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MasterAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MasterAccounts");
        return masterAccountRepository.findAll(pageable).map(masterAccountMapper::toDto);
    }

    /**
     * Get one masterAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MasterAccountDTO> findOne(Long id) {
        log.debug("Request to get MasterAccount : {}", id);
        return masterAccountRepository.findById(id).map(masterAccountMapper::toDto);
    }

    /**
     * Delete the masterAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MasterAccount : {}", id);
        masterAccountRepository.deleteById(id);
    }
}
