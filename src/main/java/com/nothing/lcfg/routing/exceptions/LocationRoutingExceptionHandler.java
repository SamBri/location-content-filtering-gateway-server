package com.nothing.lcfg.routing.exceptions;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;

import com.nothing.lcfg.RootResponse;

import io.netty.handler.codec.http.HttpHeaders.Values;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class LocationRoutingExceptionHandler {

	@ExceptionHandler(ServiceUnavailableException.class)
	public String handleServiceUnavailableException(ServerWebExchange exchange,
			Model theModel, ServiceUnavailableException e) {

		log.info("@@  handleServiceUnavailableException handler");

		log.error("ServiceUnavailableException", e);

		RootResponse<String> exceptionResponse = new RootResponse<>();

		HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
		exceptionResponse.setCode(HttpStatus.NOT_ACCEPTABLE.value());
		exceptionResponse.setMessage(e.getMessage());
		exceptionResponse.setStatus("error");
		exceptionResponse.setTimestamp(ZonedDateTime.now());
		exceptionResponse.setResponse(e.getServiceStatus());

		log.error("ServiceUnavailableException", e);

		theModel.addAttribute("response", e.getServiceStatus());
		theModel.addAttribute("message", e.getMessage());
		theModel.addAttribute("statusCode", HttpStatus.NOT_ACCEPTABLE);

		exceptionResponse.setRequestId(String.valueOf(requestHeaders.get("X-REQUEST-ID").iterator().next()));
		return "unavailable";
	}

	@ExceptionHandler(ServiceBlockedException.class)
	public String handleServiceBlockedException(ServerWebExchange exchange, ServiceBlockedException e, Model theModel) {

		log.info("@@  handleServiceBlockedException handler");

		log.error("ServiceUnavailableException", e);

		theModel.addAttribute("response", e.getServiceStatus());
		theModel.addAttribute("message", e.getMessage());
		theModel.addAttribute("statusCode", HttpStatus.NOT_ACCEPTABLE);

		// go to blocked html template
		return "blocked";
	}

}
