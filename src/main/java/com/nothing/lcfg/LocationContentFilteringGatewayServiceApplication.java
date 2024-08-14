package com.nothing.lcfg;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
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

	public static void main(String[] args) {
	
//		System.out.println("Main");
//	    Logger logger = LoggerFactory.getLogger("chapters.introduction.HelloWorld1");
//	    logger.info("Hello world.".concat(System.getProperty("os.name")));	
//	  
//		
		SpringApplication.run(LocationContentFilteringGatewayServiceApplication.class, args);
	}
	
	
	@Autowired
	ResourceBundle  routeResourceBundle;
	

	@RequestMapping("/")
	public String index(Model theModel) {
		final String toLuxuryCarsWorld =routeResourceBundle.getString("cars.luxury.page.world");
		final String toMainStreamCarsWorld  =routeResourceBundle.getString("cars.mainstream.page.world");
		final String toMicroCarsWorld  =routeResourceBundle.getString("cars.micro.page.world");
		final String toSportsCarsWorld  =routeResourceBundle.getString("cars.sports.page.world");

		theModel.addAttribute("toLuxuryCarsWorld",  toLuxuryCarsWorld);
		theModel.addAttribute("toMainStreamCarsWorld",  toMainStreamCarsWorld);
		theModel.addAttribute("toMicroCarsWorld",  toMicroCarsWorld);
		theModel.addAttribute("toSportsCarsWorld",  toSportsCarsWorld);

		
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
