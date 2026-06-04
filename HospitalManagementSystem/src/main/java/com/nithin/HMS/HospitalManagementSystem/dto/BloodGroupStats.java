package com.nithin.HMS.HospitalManagementSystem.dto;

import com.nithin.HMS.HospitalManagementSystem.entities.type.BloodGroupType;
import lombok.Data;

@Data
public class BloodGroupStats {

    private final BloodGroupType bloodGroupType;
    private final Long count;
}
