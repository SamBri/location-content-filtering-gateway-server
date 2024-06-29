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
	

//	HttpServerRequest httpServerRequest;

	@Autowired
	public CountryLocationFilter(ICountryLocationService countryLocationService) {
		this.countryLocationService = countryLocationService;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		log.info("@@@ inside filter ");
		log.info("The Pre Filter Incoming Request Headers :: {}", exchange.getRequest().getHeaders());

		InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();

		String remoteAddressIp = remoteAddress.getAddress().toString();
        String localAddressIp = exchange.getRequest().getLocalAddress().getAddress().toString();
		
        

//        log.info("The Locale of the request : {}", exchange.getLocaleContext().getLocale().toString());
        log.info("The local address (sourceIp) : {}",localAddressIp );
		log.info("The remote addrsess  (destinatioIp): {}", remoteAddressIp);

		IpWhoIsResponse ipAddressLookupResponse = (IpWhoIsResponse) countryLocationService
				.getRequesterIpGeoLocationMetaData(remoteAddressIp);

		log.info("The sourceIp country: {}", ipAddressLookupResponse.getCountry());
		String countryCode = ipAddressLookupResponse.getCountry_code();
		
		log.info("countryCode recvd {}", countryCode);
		if (countryCode != null && !countryCode.isEmpty()) {

			String countryServiceStatus = isoCountryCodeBundle.getString(countryCode);

			log.info("countryServiceStatus found {}", countryCode);

			switch (countryServiceStatus) {
			case "BLOCKED": {

				log.info("@@@  BLOCKED service status.");
				
				throw new ServiceUnavailableException("This service is not available in your country.");
			}
			case "ALLOWED": {

				log.info("@@@  ALLOWED service status.");

			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + countryServiceStatus);
			}

		} else {
			
			log.error("GeoLocation Data not found");

		}

		return chain.filter(exchange);
	}

}
