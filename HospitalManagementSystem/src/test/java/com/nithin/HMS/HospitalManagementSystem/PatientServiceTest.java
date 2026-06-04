package com.nithin.HMS.HospitalManagementSystem;

import com.nithin.HMS.HospitalManagementSystem.dto.IPatientInfo;
import com.nithin.HMS.HospitalManagementSystem.entities.PatientEntity;
import com.nithin.HMS.HospitalManagementSystem.repositories.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PatientServiceTest {
    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void testPatient(){
        List<IPatientInfo> patientInfo = patientRepository.getAllPatientInfo();
        for(IPatientInfo p:patientInfo){
            System.out.println(p);
        }
    }
}
