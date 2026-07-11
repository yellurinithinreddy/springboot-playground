package com.nithin.playlist;

import com.nithin.playlist.service.AIService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VibeMatcherApplicationTests {

	@Autowired
	AIService aiService;
	@Test
	void contextLoads() {
	}

	@Test
	void ingestData() {
		aiService.ingestSongsDataIntoDB();
	}

}
