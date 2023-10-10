package com.example.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestServiceA {

	@Test
	void testSumar() {
		ServiceA serviceA = new ServiceAimpl();
		assertEquals(serviceA.sumar(2, 2), 5); //5 beacuse in ServiceAImpl return a + b + 1
	}

}
