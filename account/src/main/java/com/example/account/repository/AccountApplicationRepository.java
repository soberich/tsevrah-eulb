package com.example.account.repository;

import com.example.account.domain.AccountApplication;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AccountApplication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountApplicationRepository extends JpaRepository<AccountApplication, Long> {}
