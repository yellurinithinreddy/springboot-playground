package com.nithin.CMS.CollegeManagementSystem.advice;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApiError {

    private String message;

    private List<String> suberrors;
}
