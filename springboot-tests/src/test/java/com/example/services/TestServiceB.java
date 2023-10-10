package com.example.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestServiceB {

	@Test
	public void testMultiplicar() {
		ServiceB serviceB= new ServiceBimpl();
		assertEquals(serviceB.multiplicar(2, 3), 6);
	}
	
	@Test
	public void testMultiplicarSuma() {
		// return a + b + 1; en servicesAimpl por eso falla
		ServiceA serviceA = new ServiceAimpl();
		ServiceB serviceB= new ServiceBimpl();
		
		serviceB.setServicioA(serviceA);
		assertEquals(serviceB.multiplicarSuma(2, 3, 2), 12); //12 beacuse in ServiceAImpl return a + b + 1
	}
	
	@Test
	public void testMultiplicarSumaMockito() {
		//eliminamos la dependencia y similamos con mockito
		ServiceA serviceA = Mockito.mock(ServiceA.class);
		when(serviceA.sumar(2, 3)).thenReturn(5);
		
		ServiceB serviceB= new ServiceBimpl();
		serviceB.setServicioA(serviceA);
		assertEquals(serviceB.multiplicarSuma(2, 3, 2), 10);
		
	}
}
