package com.nothing.lcfg;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nothing.lcfg.wsresponses.ipwhois.IpWhoIsResponse;

@Component("ipWhoIsClient")
public class IPWhoIsClient {

	@Autowired
	RestTemplate restTemplate;

	@Value("${IpWhoIs.service}")
	private String ipWhoIsUrl;

	public IpWhoIsResponse doWhoIsIpOn(String ipAddress) {

		ipWhoIsUrl = ipWhoIsUrl.replace("<IP_ADDRESS>", ipAddress);

		RequestEntity<Void> request = RequestEntity.get(URI.create(ipWhoIsUrl)).accept(MediaType.APPLICATION_JSON)
				.build();

		ResponseEntity<IpWhoIsResponse> response = restTemplate.exchange(request, IpWhoIsResponse.class);

		System.out.println(response.getBody());
		
		IpWhoIsResponse ipWhoIsResponse = null;
		if (response.getStatusCode() == HttpStatus.OK) {

			ipWhoIsResponse = response.getBody();
		}

		return ipWhoIsResponse;

	}

}
