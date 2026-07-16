package com.nithin.aopApp;

import com.nithin.aopApp.service.ShipmentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class AopAppApplicationTests {
	@Autowired
	private ShipmentService shipmentService;


	@Test
	void textOrderPackage(){
		String res = shipmentService.orderPackage(-1L);
		log.info(res);
	}


	@Test
	void textTrackingPackage(){
		shipmentService.trackPackage(4L);
	}

}
