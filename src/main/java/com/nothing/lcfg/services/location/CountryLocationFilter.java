package com.nothing.lcfg.services.location;

import java.net.Inet4Address;
import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.nothing.lcfg.IPWhoIsClient;

import reactor.core.publisher.Mono;



@Component
public class CountryLocationFilter implements GatewayFilter {
	
	
	@Autowired
	ICountryLocationService countryLocationService;
	

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();

		String ipAddress = remoteAddress.getAddress().getHostAddress();

		String country = countryLocationService.getRequesterCountry(ipAddress);

		if (country != null) {
			
			
			// list of allowed countries to access content

		}

		return chain.filter(exchange);
	}


}
