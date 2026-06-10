package com.nithin.HMS.HospitalManagementSystem;

import com.nithin.HMS.HospitalManagementSystem.dto.BloodGroupStats;
import com.nithin.HMS.HospitalManagementSystem.dto.CPatientInfo;
import com.nithin.HMS.HospitalManagementSystem.dto.IPatientInfo;
import com.nithin.HMS.HospitalManagementSystem.entities.Appointment;
import com.nithin.HMS.HospitalManagementSystem.entities.Department;
import com.nithin.HMS.HospitalManagementSystem.entities.Insurance;
import com.nithin.HMS.HospitalManagementSystem.entities.PatientEntity;
import com.nithin.HMS.HospitalManagementSystem.repositories.PatientRepository;
import com.nithin.HMS.HospitalManagementSystem.services.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PatientServiceTest {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DoctorService doctorService;

    @Test
    public void testPatient(){
//        List<IPatientInfo> patientInfo = patientRepository.getAllPatientInfo();

//        List<CPatientInfo> patientInfo = patientRepository.getAllPatientInfoConcrete();

//        List<BloodGroupStats> bloodGroupStats = patientRepository.getBloodGroupStats();
//        for(var p:bloodGroupStats){
//            System.out.println(p);
//        }
//        int rowsAffected = patientRepository.updatePatientName("Sita Shankar",4L);
//        System.out.println(rowsAffected);
//        List<PatientEntity> patientEntities = patientRepository.findAll();
//
//        for(PatientEntity p:patientEntities) System.out.println(p);

        List<PatientEntity> patientEntities = patientRepository.getAllPatientInfoWithAppointments();

        for(PatientEntity p:patientEntities) System.out.println(p);



    }

    @Test
    public void tests(){
        Insurance insurance = Insurance.builder()
                .provider("HDFC Ergo")
                .policyNumber("HDFC_234")
                .validUntil(LocalDate.of(2030, 1,1))
                .build();

        Insurance savedInsurance = patientService.savePatient(insurance,1L);
        System.out.println(savedInsurance);

//        insuranceService.deleteInsurance(savedInsurance.getId());
        Insurance insurance1 = Insurance.builder()
                .provider("Kotak Mico")
                .policyNumber("KOTAK_143")
                .validUntil(LocalDate.of(2050, 1,1))
                .build();

        Insurance savedInsurance1 = patientService.savePatient(insurance1,1L);
        System.out.println(savedInsurance1);


    }

    @Test
    public void testForSavingAppointmentandDeletingDoctor(){
        Appointment appointment = Appointment.builder()
                .status("some what healed")
                .reason("bike accident leg fracture")
                .appointmentTime(LocalDateTime.of(2024,3,3,0,0,0))
                .build();


        Appointment savedAppointment = appointmentService.createAppointment(appointment,1L,1L);
        System.out.println(savedAppointment);

        doctorService.deleteDoctor(1L);
    }


    @Test
    public void addDepartment(){
        Department department = Department.builder()
                .name("cardiology")
                .build();

        List<Long> doctorIds = List.of(1L,2L);
        Department savedDepartment = departmentService.addDepartment(department,2L,doctorIds);
        System.out.println(savedDepartment);

        departmentService.deleteDepartment(1L);
    }
}
