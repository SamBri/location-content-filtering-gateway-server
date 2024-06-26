package com.nothing.lcfg.services.location;

import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;


@Order(1)
@Component
public class CountryLocationFilter implements GlobalFilter {
	
	
	ICountryLocationService countryLocationService;
	
	
    @Autowired
	public CountryLocationFilter(ICountryLocationService countryLocationService) {
		this.countryLocationService = countryLocationService;
	}




	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		System.out.println("@@@ filtering ");
		
		InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();


		String ipAddress = remoteAddress.getAddress().toString();


		System.out.println(ipAddress);

		String country = countryLocationService.getRequesterCountry(ipAddress);

		
		System.out.println(country);
		if (country != null) {
			
			
			// list of allowed countries to access content

		}

		return chain.filter(exchange);
	}




}
