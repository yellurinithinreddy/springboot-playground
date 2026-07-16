package com.nithin.aopApp.service.impl;

import com.nithin.aopApp.aspect.MyLogging;
import com.nithin.aopApp.service.ShipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShipmentServiceImpl implements ShipmentService {


    @Override
    public String orderPackage(Long orderId) {
        try{
            log.info("Processing order Package");
            Thread.sleep(1000);
        }
        catch(Exception ex){
            log.error("Exception occured");
        }
        return "Order package with order: "+orderId;
    }

    @Override
    @MyLogging
    public String trackPackage(Long orderId) {
        try{
            log.info("Tracking order Package");
            throw new RuntimeException("package could not be tracked");
        }
        catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
