package com.nothing.lcfg;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Controller
public class LocationContentFilteringGatewayServiceApplication {

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

	public static void main(String[] args) {

//		System.out.println("Main");
//	    Logger logger = LoggerFactory.getLogger("chapters.introduction.HelloWorld1");
//	    logger.info("Hello world.".concat(System.getProperty("os.name")));	
//	  
//		
		SpringApplication.run(LocationContentFilteringGatewayServiceApplication.class, args);
	}

	@RequestMapping("/")
	public String index(Model theModel) {
		final String toLuxuryCarsWorld = luxuryCarsRoute;
		final String toMainStreamCarsWorld = mainstreamCarsRoute;
		final String toMicroCarsWorld = microCarsRoute;
		final String toSportsCarsWorld = sportsCarsRoute;

		theModel.addAttribute("toLuxuryCarsWorld", toLuxuryCarsWorld);
		theModel.addAttribute("toMainStreamCarsWorld", toMainStreamCarsWorld);
		theModel.addAttribute("toMicroCarsWorld", toMicroCarsWorld);
		theModel.addAttribute("toSportsCarsWorld", toSportsCarsWorld);

		System.err.println("inside gateway landing page.");
		System.err.println("toLuxuryCarsWorld :: " + toLuxuryCarsWorld);
		System.err.println("toMainStreamCarsWorld :: " + toMainStreamCarsWorld);
		System.err.println("toMicroCarsWorld :: " + toMicroCarsWorld);
		System.err.println("toSportsCarsWorld :: " + toSportsCarsWorld);

		return "welcome";
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
