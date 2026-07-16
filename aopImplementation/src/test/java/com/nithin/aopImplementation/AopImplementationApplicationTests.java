package com.nithin.aopImplementation;

import com.nithin.aopImplementation.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AopImplementationApplicationTests {

	@Autowired
	private OrderService orderService;

	@Test
	void testOrderItem() {
		orderService.orderItem(-1L);
	}

	@Test
	void testReturnItem() {
		orderService.orderItem(1L);
	}

}
