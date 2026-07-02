package com.nithin.TestingApp.service.impl;

import com.nithin.TestingApp.TestContainerConfiguration;
import com.nithin.TestingApp.dto.EmployeeDTO;
import com.nithin.TestingApp.entity.Employee;
import com.nithin.TestingApp.exception.ResourceNotFoundException;
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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Import(TestContainerConfiguration.class)
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

    @Test
    void testGetEmployeeById_whenEmployeeIsNotPresent_thenThrowException(){
//        Arrange
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
//        act and assert

        assertThatThrownBy(() -> employeeService.getEmployeeById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with Id: 1");

        verify(employeeRepository).findById(anyLong());
    }


    @Test
    void testCreateEmployee_whenAttemptedWithExistingEmail_thenThrowException(){
//        arrange

        when(employeeRepository.findByEmail(mockEmployee.getEmail())).thenReturn(List.of(mockEmployee));
//        act and assert

        assertThatThrownBy(() -> employeeService.createEmployee(mockEmployeeDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee with email already exists: "+mockEmployee.getEmail());

        verify(employeeRepository).findByEmail(mockEmployee.getEmail());
        verify(employeeRepository,never()).save(any());
    }


    @Test
    void testUpdateEmployee_whenEmployeeIsPresent_thenReturnUpdatedEmployee(){
//        arrange


        when(employeeRepository.findById(mockEmployee.getId())).thenReturn(Optional.of(mockEmployee));
        mockEmployeeDTO.setName("Kalyan");
        mockEmployeeDTO.setSalary(199L);

        Employee updatedEmployee = modelMapper.map(mockEmployeeDTO, Employee.class);
        when(employeeRepository.save(any())).thenReturn(updatedEmployee);
//        act
        EmployeeDTO employeeDTO = employeeService.updateEmployee(mockEmployee.getId(),mockEmployeeDTO);
//        assert

        assertThat(employeeDTO).isNotNull();
        assertThat(mockEmployeeDTO).isEqualTo(employeeDTO);

        verify(employeeRepository).findById(anyLong());
        verify(employeeRepository).save(any());

    }

    @Test
    void testUpdateEmployee_whenEmployeeIsNotPresent_thenThrowException(){
//        arrange
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
//        act and assert

        assertThatThrownBy(() -> employeeService.updateEmployee(mockEmployeeDTO.getId(),mockEmployeeDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with Id: "+mockEmployeeDTO.getId());

        verify(employeeRepository).findById(anyLong());
        verify(employeeRepository,never()).save(any());
    }

    @Test
    void testUpdateEmployee_whenAttemptedToUpdateEmailOfEmployee_thenThrowException(){
//        arrange
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(mockEmployee));
        mockEmployeeDTO.setEmail("monu@gmail.com");
        mockEmployeeDTO.setSalary(2433L);
//        act and assert

        assertThatThrownBy(() -> employeeService.updateEmployee(mockEmployeeDTO.getId(),mockEmployeeDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User should not try to update email");

        verify(employeeRepository).findById(anyLong());
        verify(employeeRepository,never()).save(any());
    }


    @Test
    void testDeleteEmployee_whenEmployeeIsPresent_theDeleteEmployee(){
//        arrange
        when(employeeRepository.existsById(anyLong())).thenReturn(true);
//        act

//        employeeService.deleteEmployee(mockEmployeeDTO.getId());
//        assert

        assertThatCode(() -> employeeService.deleteEmployee(mockEmployeeDTO.getId()))
                .doesNotThrowAnyException();
        verify(employeeRepository).deleteById(anyLong());
    }

    @Test
    void testDeleteEmployee_whenEmployeeIsNotPresent_thenThrowException(){
//        arrange
        when(employeeRepository.existsById(anyLong())).thenReturn(false);
//        act and assert

        assertThatThrownBy(() -> employeeService.deleteEmployee(mockEmployeeDTO.getId()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with Id: "+mockEmployeeDTO.getId());

        verify(employeeRepository).existsById(anyLong());
        verify(employeeRepository,never()).deleteById(anyLong());
    }

}