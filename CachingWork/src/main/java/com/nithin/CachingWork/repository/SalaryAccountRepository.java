package com.nithin.CachingWork.repository;

import com.nithin.CachingWork.entity.SalaryAccount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SalaryAccountRepository extends JpaRepository<SalaryAccount,Long> {

//    @Override
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    Optional<SalaryAccount> findById(Long accountId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from SalaryAccount s where s.id = :id")
    Optional<SalaryAccount> findByIdForUpdate(@Param("id") Long id);
}
