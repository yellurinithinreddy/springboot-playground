package com.nithin.prod_ready_features.dto;



import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDTO {

    private Long id;

    private String name;

    private String email;

    private Integer age;


    private String role;

    private LocalDate joiningDate;


    private Double salary;


    private Boolean active;


}
