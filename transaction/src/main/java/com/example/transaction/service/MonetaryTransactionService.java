package com.example.transaction.service;

import com.example.transaction.domain.MonetaryTransaction;
import com.example.transaction.repository.MonetaryTransactionRepository;
import com.example.transaction.service.dto.MonetaryTransactionDTO;
import com.example.transaction.service.mapper.MonetaryTransactionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MonetaryTransaction}.
 */
@Service
@Transactional
public class MonetaryTransactionService {
    private final Logger log = LoggerFactory.getLogger(MonetaryTransactionService.class);

    private final MonetaryTransactionRepository monetaryTransactionRepository;

    private final MonetaryTransactionMapper monetaryTransactionMapper;

    public MonetaryTransactionService(
        MonetaryTransactionRepository monetaryTransactionRepository,
        MonetaryTransactionMapper monetaryTransactionMapper
    ) {
        this.monetaryTransactionRepository = monetaryTransactionRepository;
        this.monetaryTransactionMapper = monetaryTransactionMapper;
    }

    /**
     * Save a monetaryTransaction.
     *
     * @param monetaryTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    public MonetaryTransactionDTO save(MonetaryTransactionDTO monetaryTransactionDTO) {
        log.debug("Request to save MonetaryTransaction : {}", monetaryTransactionDTO);
        MonetaryTransaction monetaryTransaction = monetaryTransactionMapper.toEntity(monetaryTransactionDTO);
        monetaryTransaction = monetaryTransactionRepository.save(monetaryTransaction);
        return monetaryTransactionMapper.toDto(monetaryTransaction);
    }

    /**
     * Get all the monetaryTransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MonetaryTransactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MonetaryTransactions");
        return monetaryTransactionRepository.findAll(pageable).map(monetaryTransactionMapper::toDto);
    }

    /**
     * Get one monetaryTransaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MonetaryTransactionDTO> findOne(Long id) {
        log.debug("Request to get MonetaryTransaction : {}", id);
        return monetaryTransactionRepository.findById(id).map(monetaryTransactionMapper::toDto);
    }

    /**
     * Delete the monetaryTransaction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MonetaryTransaction : {}", id);
        monetaryTransactionRepository.deleteById(id);
    }
}
