package com.nithin.CustomerSupportAgent;

import com.nithin.CustomerSupportAgent.service.CustomerAgentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerSupportAgentApplicationTests {


	@Autowired
	private CustomerAgentService customerAgentService;

	@Test
	void ingestData() {
		customerAgentService.ingestPdfData();
	}

}
