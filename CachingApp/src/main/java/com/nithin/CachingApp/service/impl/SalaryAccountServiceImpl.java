package com.nithin.CachingApp.service.impl;

import com.nithin.CachingApp.entity.Employee;
import com.nithin.CachingApp.entity.SalaryAccount;
import com.nithin.CachingApp.repository.SalaryAccountRepository;
import com.nithin.CachingApp.service.SalaryAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class SalaryAccountServiceImpl implements SalaryAccountService {
    private final SalaryAccountRepository salaryAccountRepository;

    @Override
    public SalaryAccount create(Employee employee) {

        SalaryAccount salaryAccount = SalaryAccount.builder()
                .balance(BigDecimal.ZERO)
                .employee(employee)
                .build();

        return salaryAccountRepository.save(salaryAccount);
    }

    @Override
//    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SalaryAccount incrementBalance(Long accountId){
        SalaryAccount salaryAccount = salaryAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Not found"));
        BigDecimal old = salaryAccount.getBalance();
        BigDecimal newB = old.add(BigDecimal.valueOf(1L));
        salaryAccount.setBalance(newB);
        return salaryAccountRepository.save(salaryAccount);
    }
}
