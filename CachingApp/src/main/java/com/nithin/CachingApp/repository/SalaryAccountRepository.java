package com.nithin.CachingApp.repository;

import com.nithin.CachingApp.entity.SalaryAccount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface SalaryAccountRepository extends JpaRepository<SalaryAccount,Long> {

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<SalaryAccount> findById(Long accountId);
}
