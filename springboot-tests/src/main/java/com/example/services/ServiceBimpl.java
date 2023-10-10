package com.example.services;

public class ServiceBimpl implements ServiceB {

	private ServiceA serviceA;

	@Override
	public ServiceA getServiceA() {
		return serviceA;
	}

	@Override
	public void setServicioA(ServiceA serviceA) {
		this.serviceA = serviceA;

	}

	@Override
	public int multiplicarSuma(int a, int b, int multiplicador) {
		return serviceA.sumar(a, b) * multiplicador;
	}

	@Override
	public int multiplicar(int a, int b) {
		return a * b;
	}

}
