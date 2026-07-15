package com.nithin.aopApp.service;

public interface ShipmentService {

    void orderPackage(Long orderId);

    void trackPackage(Long orderId);
}
