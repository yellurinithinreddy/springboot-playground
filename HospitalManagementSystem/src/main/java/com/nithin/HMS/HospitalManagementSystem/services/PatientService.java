package com.nithin.HMS.HospitalManagementSystem.services;

import com.nithin.HMS.HospitalManagementSystem.entities.PatientEntity;
import com.nithin.HMS.HospitalManagementSystem.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    @Transactional
    public void testPatient(){
        // The moment we use transactional hibernate will only fire select query only once, because it checks the persistance context and if
        //it is not there then it will fire the query
        // so the data we save and the data we get from db will be in its own persistance context.
        PatientEntity p1 = patientRepository.findById(1L).orElseThrow();
        PatientEntity p2 = patientRepository.findById(1L).orElseThrow();

        System.out.println(p1+" "+p2);
        System.out.println(p1 == p2);
    }
}
