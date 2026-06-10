package com.nithin.HMS.HospitalManagementSystem.services;

import com.nithin.HMS.HospitalManagementSystem.entities.Insurance;
import com.nithin.HMS.HospitalManagementSystem.entities.PatientEntity;
import com.nithin.HMS.HospitalManagementSystem.repositories.InsuranceRepository;
import com.nithin.HMS.HospitalManagementSystem.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;

    private final PatientRepository patientRepository;

    @Transactional
    public void deleteInsurance(Long insuranceId){
        insuranceRepository.findById(insuranceId);
        insuranceRepository.deleteById(insuranceId);

        //it will not delete the insurance because the patient is still pointing to this insurance
    }


    @Transactional
    public PatientEntity removeInsuranceForPatient(Long patientId){
        PatientEntity patient = patientRepository.findById(patientId).orElseThrow();

        patient.setInsurance(null);
        return patient;
    }


    @Transactional
    public Insurance updateInsuranceForPatient(Insurance insurance,Long patientId){
        PatientEntity patient = patientRepository.findById(patientId).orElseThrow();
        patient.setInsurance(insurance);


        insurance.setPatient(patient); //optional

        return insurance;

    }
}
