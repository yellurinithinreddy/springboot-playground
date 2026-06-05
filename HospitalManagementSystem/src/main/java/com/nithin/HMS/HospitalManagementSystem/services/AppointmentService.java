package com.nithin.HMS.HospitalManagementSystem.services;

import com.nithin.HMS.HospitalManagementSystem.entities.Appointment;
import com.nithin.HMS.HospitalManagementSystem.entities.Doctor;
import com.nithin.HMS.HospitalManagementSystem.entities.PatientEntity;
import com.nithin.HMS.HospitalManagementSystem.repositories.AppointmentRepository;
import com.nithin.HMS.HospitalManagementSystem.repositories.DoctorRepository;
import com.nithin.HMS.HospitalManagementSystem.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Transactional
    public Appointment createAppointment(Appointment appointment, Long patientId, Long doctorId){
        PatientEntity patient = patientRepository.findById(patientId).orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointmentRepository.save(appointment);
        return appointment;


    }
}
