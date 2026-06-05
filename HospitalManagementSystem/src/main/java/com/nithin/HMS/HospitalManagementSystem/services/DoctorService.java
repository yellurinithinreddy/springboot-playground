package com.nithin.HMS.HospitalManagementSystem.services;

import com.nithin.HMS.HospitalManagementSystem.repositories.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Transactional
    public void deleteDoctor(Long doctorId){
        doctorRepository.findById(doctorId).orElseThrow();
        doctorRepository.deleteById(doctorId);
    }
}
