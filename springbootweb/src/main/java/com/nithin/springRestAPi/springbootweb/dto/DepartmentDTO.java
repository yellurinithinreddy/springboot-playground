package com.nithin.springRestAPi.springbootweb.dto;

import com.nithin.springRestAPi.springbootweb.annotations.PrimeNumber;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {

    private Long id;

    @NotBlank(message = "title cannot be empty after trimming the spaces")
    private String title;

    @AssertTrue(message = "dept must be active(true)")
    private boolean active;

    @PrimeNumber
    private Integer deptStrength;

    @PastOrPresent
    private LocalDate createdAt;
}
