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

        log.error("error occured");
        log.warn("warn occured");
        log.info("info occured");
        log.debug("debug occured");
        log.trace("trace occured");

        try{
            ApiResponse<List<EmployeeDTO>> employeeDTOS = restClient.get()
                    .uri("employees")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            return employeeDTOS.getData();
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }


    }

    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) {
        try{

            ApiResponse<EmployeeDTO> employeeDTO = restClient.get()
                .uri("employees/{employeeId}",employeeId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
            return employeeDTO.getData();
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {

        try{

            ApiResponse<EmployeeDTO> employeeDTOResponse = restClient.post()
                    .uri("employees")
                    .body(employeeDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,(req,res) ->{
                        System.out.println(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("resource not found");
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });

            return employeeDTOResponse.getData();
        } catch(Exception e){
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
