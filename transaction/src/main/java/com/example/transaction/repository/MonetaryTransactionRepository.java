package com.example.transaction.repository;

import com.example.transaction.domain.MonetaryTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.stream.*;

/**
 * Spring Data  repository for the MonetaryTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonetaryTransactionRepository extends JpaRepository<MonetaryTransaction, Long> {

    Stream<MonetaryTransaction> findAllByAccount_CustomerID(Long customerID);
}
