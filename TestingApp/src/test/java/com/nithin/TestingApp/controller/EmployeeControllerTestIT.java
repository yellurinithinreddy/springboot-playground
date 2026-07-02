package com.nithin.TestingApp.controller;

import com.nithin.TestingApp.TestContainerConfiguration;
import com.nithin.TestingApp.dto.EmployeeDTO;
import com.nithin.TestingApp.entity.Employee;
import com.nithin.TestingApp.repository.EmployeeRepository;
import com.nithin.TestingApp.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

////webenv is needed to start the tomcat server because we need server started to acept requests.
////only springbootTest does load the application contexts and wire the dependencies.
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
////@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Import(TestContainerConfiguration.class)
//@AutoConfigureWebTestClient
class EmployeeControllerTest extends AbstractIntegrationTest{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    private Employee employee;

    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp(){
        employee = Employee.builder()
                .name("Nithin")
                .email("nithin@gmail.com")
                .salary(1000L)
                .build();

        employeeDTO = modelMapper.map(employee,EmployeeDTO.class);
        employeeRepository.deleteAll();

    }

    @Test
    void testGetEmployeeById_whenSuccess(){
        Employee savedEmployee = employeeRepository.save(employee);

        webTestClient.get()
                .uri("/employee/{id}",savedEmployee.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDTO.class) // withput converting into dto we can use jsonPath to cgeck raw json
                .value(employeeDTO1 -> {
                    assertThat(employeeDTO1.getName()).isEqualTo(savedEmployee.getName());
                    assertThat(employeeDTO1.getSalary()).isEqualTo(savedEmployee.getSalary());
                    assertThat(employeeDTO1.getId()).isEqualTo(savedEmployee.getId());
                });
//                .jsonPath("$.id").isEqualTo(savedEmployee.getId())
//                .jsonPath("$.name").isEqualTo(savedEmployee.getName())
//                .jsonPath("$.email").isEqualTo(savedEmployee.getEmail());
    }

    @Test
    void testGetEmployeeById_Failure(){
        webTestClient.get()
                .uri("/employee/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreateEmployee_whenEmployeeExists_thenThrowException(){
        employeeRepository.save(employee);
        webTestClient.post()
                .uri("/employee")
                .bodyValue(employeeDTO)
                .exchange()
                .expectStatus().is5xxServerError();

    }

    @Test
    void testCreateEmployee_whenEmployeeNotExists_thenCreateEmployee(){
        webTestClient.post()
                .uri("/employee")
                .bodyValue(employeeDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.name").isEqualTo(employee.getName())
                .jsonPath("$.email").isEqualTo(employee.getEmail());
    }

    @Test
    void testUpdateEmployee_whenEmployeeIsPresent_thenReturnUpdatedEmployee(){
        employeeRepository.save(employee);
        employeeDTO.setSalary(3456L);
        employeeDTO.setName("Mango");
        webTestClient.put()
                .uri("/employee/{id}",employee.getId())
                .bodyValue(employeeDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.salary").isEqualTo(employeeDTO.getSalary())
                .jsonPath("$.name").isEqualTo(employeeDTO.getName());
    }

    @Test
    void testUpdateEmployee_whenEmployeeIsNotPresent_thenThrowException(){
        webTestClient.put()
                .uri("/employee/{id}",employee.getId())
                .bodyValue(employeeDTO)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateEmployee_whenAttemptedToUpdateEmail_thenThrowException(){
        employeeRepository.save(employee);
        employeeDTO.setEmail("kdfj@gmail.com");
        employeeDTO.setSalary(5678L);
        webTestClient.put()
                .uri("employee/{id}",employee.getId())
                .bodyValue(employeeDTO)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testDeleteEmployee_whenEmployeeIsPresent_thenDeleteEmployee(){
        employeeRepository.save(employee);
        webTestClient.delete()
                .uri("/employee/{id}",employee.getId())
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testDeleteEmployee_whenEmployeeIsNotPresent_thenThrowException(){
        webTestClient.delete()
                .uri("/employee/",employee.getId())
                .exchange()
                .expectStatus().isNotFound();
    }
}