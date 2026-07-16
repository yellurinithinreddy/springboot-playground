package com.nithin.aopImplementation.service.impl;

import com.nithin.aopImplementation.annotation.MeasureExecutionTime;
import com.nithin.aopImplementation.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Override
    @MeasureExecutionTime
    public String orderItem(Long itemId) {
        if(itemId < 0) throw new RuntimeException("order id is negative");
        return "Item ordered with Id: "+itemId;
    }


    @Override
    @MeasureExecutionTime
    public String returnItem(Long itemId) {
        if(itemId < 0) throw new RuntimeException("order id is negative");
        return "Item returned with Id: "+itemId;
    }
}
