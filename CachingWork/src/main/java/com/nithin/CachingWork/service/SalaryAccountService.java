package com.nithin.CachingWork.service;

import com.nithin.CachingWork.entity.Employee;
import com.nithin.CachingWork.entity.SalaryAccount;
import com.nithin.CachingWork.repository.SalaryAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SalaryAccountService {

    private final SalaryAccountRepository salaryAccountRepository;

    public SalaryAccount create(Employee savedEmployee) {

        return salaryAccountRepository.save(SalaryAccount.builder()
                .balance(BigDecimal.ZERO)
                .employee(savedEmployee)
                .build());
    }


    @Transactional
    public SalaryAccount increment(Long accountId) {
        System.out.println("Transaction: "+TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        SalaryAccount salaryAccount = salaryAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("account not found"));
        BigDecimal old = salaryAccount.getBalance();
        BigDecimal newB = old.add(BigDecimal.valueOf(1L));
        salaryAccount.setBalance(newB);

        return salaryAccountRepository.save(salaryAccount);
    }
}
