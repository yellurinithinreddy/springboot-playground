package com.nithin.TestingApp;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
@Slf4j
class TestingAppApplicationTests {
	@Test
	@DisplayName("displaysTheGivenName")
	void contextLoads() {
	}

	@BeforeAll
    static void executeOnceBeforeAllTestCases(){
		log.info("executes before starting all the test cases");
	}

	@AfterAll
	static void executeOnceAfterAllTestCases(){
		log.info("executes after executing all the test cases");
	}

	@Test
	@Disabled
	void disabledTestCase(){
		log.info("disabled");
	}



	@BeforeEach
	void executeBeforeEachTestCase(){
		log.info("executes before starting each test case");
	}

	@AfterEach
	void executeAfterEachTestCase(){
		log.info("executes after starting each test case");
	}

	@Test
	void testingAddNumbers(){
		int a = 5;
		int b = 4;
		int res = addTwoNumbers(a,b);
//		Assertions.assertEquals(9,res);

//		Assertions.assertThat(res)
//				.isCloseTo(8, Offset.offset(1))
//				.isEqualTo(7);

		Assertions.assertThat("Apple")
				.isEqualTo("Apple")
				.startsWith("App")
				.endsWith("le")
				.hasSize(5);
	}

	@Test
	void testDivideTwoNumbers_WhenOneNumberIsZero_ArithmeticException(){
			int a = 5;
			int b = 0;

			Assertions.assertThatThrownBy(() -> divideTwoNumbers(a,b))
					.isInstanceOf(ArithmeticException.class)
					.hasMessage("Cannot divide number with zero");
	}




	int addTwoNumbers(int a,int b){
		return a+b;
	}

	int divideTwoNumbers(int a,int b){
		try{
			return a/b;
		}catch(ArithmeticException e){
			log.info("Arithmetic Exception occurred"+e.getMessage());
			throw new ArithmeticException("Cannot divide number with zero");
		}
	}

}
