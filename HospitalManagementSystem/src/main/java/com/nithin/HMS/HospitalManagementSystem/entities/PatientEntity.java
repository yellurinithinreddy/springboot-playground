package com.nithin.HMS.HospitalManagementSystem.entities;

import com.nithin.HMS.HospitalManagementSystem.entities.type.BloodGroupType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patient")
@ToString
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 18)
    private String name;

    @Email
    private String email;

    @Past
    private LocalDate birthDate;

    @Enumerated(value = EnumType.STRING)
    private BloodGroupType bloodGroup;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "patient_insurance",unique = true)
    private Insurance insurance;


    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Appointment> appointments;
}
