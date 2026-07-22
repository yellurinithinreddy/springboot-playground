package com.nithin.CachingApp.service;

import com.nithin.CachingApp.entity.Employee;
import com.nithin.CachingApp.entity.SalaryAccount;

public interface SalaryAccountService {

    SalaryAccount create(Employee employee);

    SalaryAccount incrementBalance(Long accountId);
}
