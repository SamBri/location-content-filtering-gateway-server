package com.nothing.lcfg;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.Show;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class LocationContentFilteringGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocationContentFilteringGatewayServiceApplication.class, args);
	}

	@RequestMapping("/")
	public String index() {
		return "api.cars.gateway";
	}

	// 1. for all incoming traffic coming to host service name.
	// 2. go to the location filter to make a location query of the incoming ip
	// 3. if the tcp connection established is travelling from an ip in the ranges
	// of blocked locations.
	// 4. return the response of blocked.
	// 5. else allow the connection to the server.

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
