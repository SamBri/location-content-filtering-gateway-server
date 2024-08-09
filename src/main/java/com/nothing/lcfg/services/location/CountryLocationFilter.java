package com.nothing.lcfg.services.location;

import java.net.InetSocketAddress;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import com.nothing.lcfg.routing.LocationContentRoutingConfig;
import com.nothing.lcfg.routing.exceptions.IpGeoLocationNotFoundException;
import com.nothing.lcfg.routing.exceptions.ServiceUnavailableException;
import com.nothing.lcfg.wsresponses.ipwhois.IpWhoIsResponse;

import io.micrometer.core.instrument.binder.http.HttpServletRequestTagsProvider;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.client.HttpClientRequest;
import reactor.netty.http.server.HttpServerRequest;

@Order(1)
@Component
@Slf4j
public class CountryLocationFilter implements GlobalFilter {

	ICountryLocationService countryLocationService;

	@Autowired
	ResourceBundle isoCountryCodeBundle;

  

	@Autowired
	ResourceBundle routingMessagesBundle;


	
	
	
	@Autowired
	public CountryLocationFilter(ICountryLocationService countryLocationService) {
		this.countryLocationService = countryLocationService;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		log.info("@@@ inside filter ");
		log.info("The Pre Filter Incoming Request Headers :: {}", exchange.getRequest().getHeaders());

		String theUserRequestedService = exchange.getRequest().getPath().toString();
		InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
		
		String remoteAddressIp = remoteAddress.getAddress().toString();
		String localAddressIp = exchange.getRequest().getLocalAddress().getAddress().toString();

//        log.info("The Locale of the request : {}", exchange.getLocaleContext().getLocale().toString());
		log.info("The local address (sourceIp) : {}", localAddressIp);
		log.info("The remote addrsess  (destinatioIp): {}", remoteAddressIp);

		IpWhoIsResponse ipAddressLookupResponse = (IpWhoIsResponse) countryLocationService
				.getRequesterIpGeoLocationMetaData(remoteAddressIp);

		String sourceIpCountry = ipAddressLookupResponse.getCountry();
		log.info("The sourceIp country: {}", sourceIpCountry);
		String countryCode = ipAddressLookupResponse.getCountry_code();

		log.info("countryCode recvd {}", countryCode);
		if (countryCode != null && !countryCode.isEmpty()) {

			String countryServiceStatus = isoCountryCodeBundle.getString(countryCode);

			
			log.info("countryServiceStatus found {}", countryServiceStatus);

		    String routingMessage = null;
			// java17 switch expression usage
		    countryServiceStatus = countryServiceStatus.toUpperCase();

			switch (countryServiceStatus) {
			case "BLOCKED" -> {

				log.info("@@@  BLOCKED service status.");

			}
			case "ALLOWED" -> {

				log.info("@@@  ALLOWED service status.");

			}
			case "AVAILABLE" -> {

				log.info("@@@  AVAILABLE service status.");

			}
			case "UNAVAILABLE" -> {

				log.info("@@@  UNAVAILABLE service status.");
				
				
				routingMessage = routingMessagesBundle.getString(countryServiceStatus.toUpperCase());	
				routingMessage = routingMessage.replace("{serviceName}",theUserRequestedService );
				routingMessage = routingMessage.replace("{country}", sourceIpCountry);
				
				
				log.error("routingMessage:", routingMessage);
				throw new ServiceUnavailableException(routingMessage,countryServiceStatus);


			}
			default -> {
				throw new IllegalArgumentException("Unexpected value: " + countryServiceStatus);

			}

			}

		} else {

			log.error("GeoLocation Data not found");

		}

		return chain.filter(exchange);
	}

}
