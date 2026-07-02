package com.nithin.TestingApp.controller;

import com.nithin.TestingApp.TestContainerConfiguration;
import com.nithin.TestingApp.dto.EmployeeDTO;
import com.nithin.TestingApp.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
@AutoConfigureWebTestClient
public class AbstractIntegrationTest {

    @Autowired
    WebTestClient webTestClient;


}
