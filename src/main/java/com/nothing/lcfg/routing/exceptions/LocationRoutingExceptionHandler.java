package com.nothing.lcfg.routing.exceptions;

import java.time.ZonedDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;

import com.nothing.lcfg.RootResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class LocationRoutingExceptionHandler {
	
	
	
	@ExceptionHandler(ServiceUnavailableException.class)
	public ResponseEntity<RootResponse<String>> handleServiceUnavailableException(ServerWebExchange exchange, ServiceUnavailableException e){
		
		log.info("@@  handleServiceUnavailableException handler");
		
		log.error("ServiceUnavailableException",e);
		
		RootResponse<String> exceptionResponse = new RootResponse<>();
		
		HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
		exceptionResponse.setCode(HttpStatus.NOT_ACCEPTABLE.value());
		exceptionResponse.setMessage(e.getMessage());
		exceptionResponse.setStatus("error");
		exceptionResponse.setTimestamp(ZonedDateTime.now());
		exceptionResponse.setResponse(e.getServiceStatus());
		exceptionResponse.setRequestId(String.valueOf(requestHeaders.get("X-REQUEST_ID")));
		return new ResponseEntity<RootResponse<String>>(exceptionResponse,HttpStatus.NOT_ACCEPTABLE);
	}

}
