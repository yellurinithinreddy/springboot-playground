package com.nithin.HMS.HospitalManagementSystem.services;

import com.nithin.HMS.HospitalManagementSystem.repositories.InsuranceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;

    @Transactional
    public void deleteInsurance(Long insuranceId){
        insuranceRepository.findById(insuranceId);
        insuranceRepository.deleteById(insuranceId);

        //it will not delete the insurance because the patient is still pointing to this insurance
    }
}
