package com.example.transaction.repository;

import com.example.transaction.domain.AccountState;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AccountState entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountStateRepository extends JpaRepository<AccountState, Long> {}
