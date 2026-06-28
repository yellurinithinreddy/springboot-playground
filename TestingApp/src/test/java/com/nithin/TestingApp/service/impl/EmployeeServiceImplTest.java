package com.nithin.TestingApp.service.impl;

import com.nithin.TestingApp.TestContainerConfiguration;
import com.nithin.TestingApp.dto.EmployeeDTO;
import com.nithin.TestingApp.entity.Employee;
import com.nithin.TestingApp.repository.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestContainerConfiguration.class)
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee mockEmployee;

    private EmployeeDTO mockEmployeeDTO;

    @BeforeEach
    void setUp(){
        mockEmployee = Employee.builder()
                .id(1L)
                .name("Nithin")
                .email("nithin@gmail.com")
                .salary(1000L)
                .build();

        mockEmployeeDTO = modelMapper.map(mockEmployee,EmployeeDTO.class);
    }


    @Test
    void testGetEmployeeById_whenIdIsPresent_thenReturnEmployee(){
//        Arrange
        Long id = 1L;
        when(employeeRepository.findById(id)).thenReturn(Optional.of(mockEmployee));

//        Act
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);

//        Assert
        assertThat(employeeDTO).isNotNull();
        assertThat(employeeDTO.getEmail()).isEqualTo(mockEmployee.getEmail());
        verify(employeeRepository).findById(id);

    }

    @Test
    void testCreateEmployee_whenNotPresent_returnEmployeeDto(){
//        Arrange

        when(employeeRepository.findByEmail(anyString())).thenReturn(List.of());
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);

//        Act


       EmployeeDTO employeeDTO = employeeService.createEmployee(mockEmployeeDTO);

//        Assert

        assertThat(employeeDTO).isNotNull();
        assertThat(employeeDTO.getEmail()).isEqualTo(mockEmployeeDTO.getEmail());
        ArgumentCaptor<Employee> argumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository,times(1)).save(argumentCaptor.capture());
        Employee employee = argumentCaptor.getValue();
        assertThat(employee.getEmail()).isEqualTo(mockEmployee.getEmail());
//        verify(employeeRepository),atLeastOnce()).save(any());
//        verify(employeeRepository),atLeast(3)).save(any());
//        verify(employeeRepository),atMost(1)).save(any());
//        verify(employeeRepository),never()).save(any());

    }

}