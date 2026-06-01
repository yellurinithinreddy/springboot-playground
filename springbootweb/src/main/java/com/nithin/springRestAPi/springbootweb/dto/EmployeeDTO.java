package com.nithin.springRestAPi.springbootweb.dto;


import com.nithin.springRestAPi.springbootweb.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;

    @Size(min = 3,max = 18,message = "Name length should be in the range : [3,18]")
//    @NotNull(message = "name cannot be null")
    @NotEmpty(message = "name length should be greater than 0")
    @NotBlank(message = "name length should be greater than after trimming all the spaces in it, it handles the name which contains only spaces in it")
    private String name;

    @Email(message = "email is not valid, please enter valid email")
    private String email;

    @Max(value = 70 , message = "age should be less than 70")
    @Min(value = 18,message = "age should be greater than 18")
    private Integer age;

//    @Pattern(regexp = "^(ADMIN|USER)$")
    @EmployeeRoleValidation
    private String role;

    @PastOrPresent(message = "joining date must be in past or present not in future")
    private LocalDate joiningDate;

    @NotNull(message = "salary cannot be null")
    @Positive(message = "salary must be greater than zero")
    @Digits(integer = 6,fraction = 2,message = "salary should be in this format not more digits than that XXXXXX.YY")
    @DecimalMax(value = "100000.55")
    @DecimalMin(value = "100.10")
    private Double salary;

    @AssertTrue(message = "active field must be true")
    private Boolean active;


}
