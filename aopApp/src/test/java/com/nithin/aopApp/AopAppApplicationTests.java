package com.nithin.aopApp;

import com.nithin.aopApp.service.ShipmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AopAppApplicationTests {
	@Autowired
	private ShipmentService shipmentService;


	@Test
	void textOrderPackage(){
		shipmentService.orderPackage(1L);
	}


	@Test
	void textTrackingPackage(){
		shipmentService.trackPackage(4L);
	}

}
