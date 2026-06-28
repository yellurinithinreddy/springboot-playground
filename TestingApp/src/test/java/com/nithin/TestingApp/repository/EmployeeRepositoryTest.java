package com.nithin.TestingApp.repository;

import com.nithin.TestingApp.TestContainerConfiguration;
import com.nithin.TestingApp.entity.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


//@SpringBootTest// this is used for integration and testing because it loads all the app context but for unit testing iots not needed.
@Import(TestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest// this is used to test only persistence layer methods. This by default runs test in transactional context and roll backs after completing the next
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp(){
        employee = Employee.builder()
                .name("Nithin")
                .email("nithin@gmail.com")
                .salary(1000L)
                .build();
    }

    @Test
    void testFindByEmail_whenEmailIsPresent_thenReturnEmployee() {
//        Arrange or Given
        employeeRepository.save(employee);
//        Act     or when

        List<Employee> employees = employeeRepository.findByEmail(employee.getEmail());
//        Assert  or then

        assertThat(employees).isNotNull();
        assertThat(employees).isNotEmpty();
        assertThat(employees.get(0).getEmail()).isEqualTo(employee.getEmail());
    }

    @Test
    void testFindByEmail_whenEmailIsNotFound_thenReturnEmptyEmployeeList(){
        //        Arrange or Given
        String email = "notPresent/123@gmail.com";
//        Act     or when
        List<Employee> employees = employeeRepository.findByEmail(email);


//        Assert  or then

        assertThat(employees).isNotNull();
        assertThat(employees).isEmpty();
    }
}