package com.nothing.lcfg.routing.exceptions;

public class ServiceBlockedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2855946979421071859L;

	private String serviceStatus;

	public String getServiceStatus() {
		return serviceStatus;
	}

	public ServiceBlockedException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ServiceBlockedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ServiceBlockedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ServiceBlockedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ServiceBlockedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ServiceBlockedException(String routingMessage, String countryServiceStatus) {
		super(routingMessage);
		this.serviceStatus = countryServiceStatus;
	}

}
