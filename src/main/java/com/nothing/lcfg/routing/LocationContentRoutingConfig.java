package com.nothing.lcfg.routing;

import java.util.ResourceBundle;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.thymeleaf.messageresolver.StandardMessageResolver;

@Configuration
public class LocationContentRoutingConfig {

	@Value("${my.host.domain}")
	private String hostApplication;

	@Value("${ms.luxury.cars}")
	private String luxuryCarsInterface;

	@Value("${ms.mainstream.cars}")
	private String mainstreamCarsInterface;

	@Value("${ms.sports.cars}")
	private String sportsCarsInterface;

	@Value("${ms.micro.cars}")
	private String microCarsInterface;

	// Route
	@Value("${cars.luxury.route.config.path}")
	private String luxuryCarsRoute;

	@Value("${cars.mainstream.route.config.path}")
	private String microCarsRoute;

	@Value("${cars.sports.route.config.path}")
	private String sportsCarsRoute;

	@Value("${cars.mainstream.route.config.path}")
	private String mainstreamCarsRoute;

	@Value("${gateway.base-context-id}")
	private String gatewayBaseContextId;


//	@Bean
//	StandardMessageResolver myMessageResolver() {
//		
//		return new StandardMessageResolver().
//	}

	public static void main(String[] args) {

		// System.err.println(welcomePageResourceBundle().getString("luxury.url"));

//		System.out.println("Main");
//	    Logger logger = LoggerFactory.getLogger("chapters.introduction.HelloWorld1");
//	    logger.info("Hello world.".concat(System.getProperty("os.name")));	
//	    
	}

	@Bean
	ResourceBundle isoCountryCodeBundle() {
		return ResourceBundle.getBundle("iso_country_code");
	}

	@Bean
	ResourceBundle routingMessagesBundle() {
		return ResourceBundle.getBundle("routing-messages");
	}

	@Bean
	ResourceBundle pathConfiguration() {
		return ResourceBundle.getBundle("path-boundary-configuration");
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes().route(r -> r.host(hostApplication).and().path("/" + luxuryCarsRoute.concat("/**"))
				.filters(f -> f.rewritePath("/" + gatewayBaseContextId + "/?(?<segment>.*)", "/${segment}")
						.addRequestHeadersIfNotPresent("X-REQUEST-ID:{}".replace("{}", UUID.randomUUID().toString()))
						.addRequestHeadersIfNotPresent("X-Pinggy-No-Screen:{}".replace("{}", "foo")) // straight to the
																										// url
				).uri(luxuryCarsInterface))
				.route(r -> r.host(hostApplication).and().path("/" + mainstreamCarsRoute.concat("/**"))
						.filters(f -> f.rewritePath("/" + gatewayBaseContextId + "/?(?<segment>.*)", "/${segment}")
								.addRequestHeadersIfNotPresent(
										"X-REQUEST-ID:{}".replace("{}", UUID.randomUUID().toString()))
								.addRequestHeadersIfNotPresent("X-Pinggy-No-Screen:{}".replace("{}", "foo")) // straight
																												// to
																												// the
																												// url
						).uri(mainstreamCarsInterface))
				.route(r -> r.host(hostApplication).and().path("/" + sportsCarsRoute.concat("/**"))
						.filters(f -> f.rewritePath("/" + gatewayBaseContextId + "/?(?<segment>.*)", "/${segment}")
								.addRequestHeadersIfNotPresent(
										"X-REQUEST-ID:{}".replace("{}", UUID.randomUUID().toString()))
								.addRequestHeadersIfNotPresent("X-Pinggy-No-Screen:{}".replace("{}", "foo")) // straight
																												// to
																												// the
																												// url
						).uri(sportsCarsInterface))
				.route(r -> r.host(hostApplication).and().path("/" + microCarsRoute.concat("/**"))
						.filters(f -> f.rewritePath("/" + gatewayBaseContextId + "/?(?<segment>.*)", "/${segment}")
								.addRequestHeadersIfNotPresent(
										"X-REQUEST-ID:{}".replace("{}", UUID.randomUUID().toString()))
								.addRequestHeadersIfNotPresent("X-Pinggy-No-Screen:{}".replace("{}", "foo")) // straight
																												// to
																												// the
																												// url
						).uri(microCarsInterface)).build();

		// /red/?(?<segment>.*), /$\{segment}
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
