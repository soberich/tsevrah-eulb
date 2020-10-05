package com.example.transaction.service;

import com.example.transaction.domain.AccountState;
import com.example.transaction.domain.MonetaryTransaction;
import com.example.transaction.repository.AccountStateRepository;
import com.example.transaction.repository.MonetaryTransactionRepository;
import com.example.transaction.service.dto.MonetaryTransactionDTO;
import com.example.transaction.service.mapper.AccountStateMapper;
import com.example.transaction.service.mapper.MonetaryTransactionMapper;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.*;

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
    private final AccountStateRepository accountStateRepository;
    private final MonetaryTransactionMapper monetaryTransactionMapper;
    private final AccountStateMapper accountStateMapper;

    public MonetaryTransactionService(
        MonetaryTransactionRepository monetaryTransactionRepository,
        AccountStateRepository accountStateRepository,
        MonetaryTransactionMapper monetaryTransactionMapper,
        AccountStateMapper accountStateMapper) {
        this.monetaryTransactionRepository = monetaryTransactionRepository;
        this.accountStateRepository = accountStateRepository;
        this.monetaryTransactionMapper = monetaryTransactionMapper;
        this.accountStateMapper = accountStateMapper;
    }

    /**
     * Save a monetaryTransaction.
     *
     * @param monetaryTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    public MonetaryTransactionDTO save(MonetaryTransactionDTO monetaryTransactionDTO) {
        log.debug("Request to save MonetaryTransaction : {}", monetaryTransactionDTO);

        AccountState accountState =
            Optional.ofNullable(monetaryTransactionDTO.getAccountStateID())
                .flatMap(accountStateRepository::findById)
                .orElseGet(() ->
                               new AccountState()
                                   .customerID(monetaryTransactionDTO.getAccountCustomerID()));

        MonetaryTransaction monetaryTransaction = monetaryTransactionMapper.toEntity(monetaryTransactionDTO);

        accountState =
            accountState
                .addTransactions(monetaryTransaction)
                .balance(
                    (accountState.getBalance() != null ? accountState.getBalance() : BigDecimal.ZERO)
                        .add(monetaryTransactionDTO.getAmount())
                );

        accountState = accountStateRepository.save(accountState);

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
    public List<MonetaryTransactionDTO> findByAccount(Long id) {
        log.debug("Request to get MonetaryTransaction : {}", id);
        return monetaryTransactionRepository.findAllByAccount_CustomerID(id).map(monetaryTransactionMapper::toDto).collect(Collectors.toList());
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
