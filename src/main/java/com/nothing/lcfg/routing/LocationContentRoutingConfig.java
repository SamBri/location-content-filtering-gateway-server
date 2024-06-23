package com.nothing.lcfg.routing;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocationContentRoutingConfig {
	
	
	@Value("")
	String hostApplication;
	
	
	@Bean  	
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
	    return builder.routes()
	            .route(r -> 
	             r.host(hostApplication)
	             .filters(null)
	                .uri("http://httpbin.org:80")
	            )
	            .build();
	}
	
	
	
	
	
	public static void main(String[] args) {
		
	
	}
	

}
