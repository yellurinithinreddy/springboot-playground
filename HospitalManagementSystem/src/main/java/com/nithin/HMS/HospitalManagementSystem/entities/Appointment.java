package com.nithin.HMS.HospitalManagementSystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime appointmentTime;

    private String reason;

    private String status;

    @ManyToOne
    @JoinColumn(nullable = false)
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Doctor doctor;

}
