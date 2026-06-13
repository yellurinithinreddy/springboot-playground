package com.nithin.prod_ready_features.clients.impl;

import com.nithin.prod_ready_features.advice.ApiResponse;
import com.nithin.prod_ready_features.clients.EmployeeClient;
import com.nithin.prod_ready_features.dto.EmployeeDTO;
import com.nithin.prod_ready_features.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;

    Logger log = LoggerFactory.getLogger(EmployeeClientImpl.class);

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        log.trace("Trying to retrieve all employees in getAllEmployees");
        try{
            log.info("started retrieving employees data");
            ApiResponse<List<EmployeeDTO>> employeeDTOS = restClient.get()
                    .uri("employees")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            log.debug("successfully retrieved all employees data");
            log.trace("The retrieved data of all employees is: {},{},{}",employeeDTOS.getData(),"just to tell we can concatenate like this",10);
            return employeeDTOS.getData();
        }
        catch(Exception e){
            log.error("Exception occurred in getAllEmployees",e);
            throw new RuntimeException(e);
        }


    }

    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) {
        log.trace("Trying to get Employee by id in getEmployeeById for id: {}",employeeId);
        try{
            log.info("started retrieving employee with id : {}",employeeId);
            ApiResponse<EmployeeDTO> employeeDTO = restClient.get()
                .uri("employees/{employeeId}",employeeId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,(req,res) ->{
                    log.error(new String(res.getBody().readAllBytes()));
                    throw new ResourceNotFoundException("employee not found with id: "+employeeId);
                })
                .body(new ParameterizedTypeReference<>() {
                });
            log.debug("successfully retrieved the employee by id");
            log.trace("The retrieved data of all employees is: {}",employeeDTO.getData());
            return employeeDTO.getData();
        }
        catch(Exception e){
            log.error("Exception occurred in getEmployeeById",e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        log.trace("Trying to create Employee with information: {}",employeeDTO);
        try{
            log.info("Started creating employee");
            ApiResponse<EmployeeDTO> employeeDTOResponse = restClient.post()
                    .uri("employees")
                    .body(employeeDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,(req,res) ->{
                        log.debug("4xxClient error occured during createEmployee");
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("resource not found");
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });
            log.trace("successfully created new Employee data: {}",employeeDTOResponse.getData());

            return employeeDTOResponse.getData();
        } catch(Exception e){
            log.error("exception occurred in createEmployee",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO updateEmployee(Long employeeId,EmployeeDTO employeeDTO) {
        ResponseEntity<ApiResponse<EmployeeDTO>> employeeDTOResponse = restClient.put()
                .uri("employees/{employeeId}",employeeId)
                .body(employeeDTO)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,(req,res) ->{
                    System.out.println(new String(res.getBody().readAllBytes()));
                })
                .toEntity(new ParameterizedTypeReference<>() {
                });
//                .body(new ParameterizedTypeReference<>() {
//                });

        System.out.println(employeeDTOResponse.getHeaders());
        System.out.println(employeeDTOResponse.getStatusCode());
        return employeeDTOResponse.getBody().getData();
    }
}
