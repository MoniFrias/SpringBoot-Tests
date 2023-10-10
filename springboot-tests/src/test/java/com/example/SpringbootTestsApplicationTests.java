package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootTestsApplicationTests {

	Calculator cal = new Calculator();
	
	@Test
	void testSumar() {
		//given
		int num1= 10;
		int num2 = 20;
		
		//when
		int result = cal.add(num1, num2);
		//then
		assertEquals(result, 30);;
	}

	class Calculator {
		int add(int a, int b) {
			return a + b;
		}
	}
}
