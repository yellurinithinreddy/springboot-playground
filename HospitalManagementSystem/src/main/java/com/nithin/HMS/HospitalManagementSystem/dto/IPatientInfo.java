package com.nithin.HMS.HospitalManagementSystem.dto;


public interface IPatientInfo {

    Long getId();
    String getName();
    String getEmail();

    default String toFormattedString(){
        return "PatientInfo{id="+getId()+" name="+getName()+" email="+getEmail()+"}";
    }

}
