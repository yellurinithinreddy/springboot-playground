package com.nithin.spring_ai_demo;

import com.nithin.spring_ai_demo.service.AIService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringAiDemoApplicationTests {

	@Autowired
	private AIService aiService;

	@Test
	void getJokeTest() {
		String res = aiService.getJoke("Cats");
		System.out.println(res);
	}

	@Test
	void getEmbedding() {
		float[] embed = aiService.embedding("Mango is king of fruits");
		System.out.println(embed.length);
		for(float e:embed){
			System.out.println(e);
		}
	}


	@Test
	void saveInVectorDbTest() {
		aiService.save();
	}

	@Test
	void similaritySearch() {

		var doc = aiService.similaritySearch("science fiction movie about wormhole");
		for(var d:doc){
			System.out.println(d);
		}
	}

}
