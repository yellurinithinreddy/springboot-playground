package com.nithin.prod_ready_features;

import com.nithin.prod_ready_features.clients.EmployeeClient;
import com.nithin.prod_ready_features.dto.EmployeeDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProdReadyFeaturesApplicationTests {

	@Autowired
	private EmployeeClient employeeClient;



	@Test
	@Order(4)
	void testGetAllEmployees(){
		List<EmployeeDTO> employeeDTOS = employeeClient.getAllEmployees();
		System.out.println(employeeDTOS);
	}

	@Test
	@Order(3)
	void testGetEmployeeById(){


		EmployeeDTO employeeDTO = employeeClient.getEmployeeById(100L);
		System.out.println(employeeDTO);
	}

	@Test
	@Order(2)
	void createEmployee(){
		EmployeeDTO toBeCreated = new EmployeeDTO(null,"ginga","ginga@gmail.com",2,"ADMIN", LocalDate.of(2024,11,11),25790.4,true);
		System.out.println(employeeClient.createEmployee(toBeCreated));
	}

	@Test
	@Order(1)
	void updateEmployee(){
		EmployeeDTO toBeCreated = new EmployeeDTO(null,"sonja","sonja@gmail.com",25,"ADMIN", LocalDate.of(2024,11,11),25790.4,true);
		System.out.println(employeeClient.updateEmployee(252L,toBeCreated));
	}




}
