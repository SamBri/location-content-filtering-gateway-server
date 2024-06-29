package com.nothing.lcfg.routing;

import java.util.ResourceBundle;
import java.util.UUID;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nothing.lcfg.services.location.CountryLocationFilter;
import com.nothing.lcfg.services.location.ICountryLocationService;

@Configuration
public class LocationContentRoutingConfig {
	
	
	@Value("${my.host.domain}")
	private String hostApplication;
	
	@Value("${ms.service.one}")
	private String serviceOne;
	
	

	
	public static void main(String[] args) {

//		System.out.println("Main");
//	    Logger logger = LoggerFactory.getLogger("chapters.introduction.HelloWorld1");
//	    logger.info("Hello world.".concat(System.getProperty("os.name")));	
//	    
 }
	
	
	@Bean
	ResourceBundle isoCountryCodeBundle() {
		return   ResourceBundle.getBundle("iso_country_code");
	}

	
	@Bean  	
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
	    return builder.routes()
	             .route(r -> 
	              r.host(hostApplication)
	             .and()
	             .path("/cars/luxury-cars/**")
	             .filters(
	            		 f->f.rewritePath("/cars/?(?<segment>.*)", "/${segment}")
	            		 .addRequestHeadersIfNotPresent("X-REQUEST_ID:{}".replace("{}", UUID.randomUUID().toString()))
	            		 )
	             .uri(serviceOne)
	            )
//	             .route(r -> 
//	              r.host(hostApplication)
//	             .and()
//	             .path("/cars/mainstream-cars/**")
//	             .filters(f->f.rewritePath("/cars/(?<segment>.*)", "/${segment}"))
//	             .uri("http://localhost:8086")
//	            )
	            .build();
	    
	  //  /red/?(?<segment>.*), /$\{segment}
	}

	
//	@Bean  	
//	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//	    return builder.routes()
//	            .route(r -> 
//	             r.host(hostApplication)
////	             .and()
////	             .path("/")
//////	             .filters(f->f.rewritePath("/api/?(?<segment>.*)", "/${segment}"))
//             .uri("httpy://localhost:8085")
//	            )
//	            .build();
//	    
//	  //  /red/?(?<segment>.*), /$\{segment}
//	}
	
	
	
	
	

	

}
