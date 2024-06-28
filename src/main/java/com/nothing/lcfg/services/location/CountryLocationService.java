package com.nothing.lcfg.services.location;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nothing.lcfg.IPWhoIsClient;
import com.nothing.lcfg.wsresponses.ipwhois.IpWhoIsResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CountryLocationService implements ICountryLocationService {

	@Autowired
	@Qualifier("ipWhoIsClient")
	IPWhoIsClient ipWhoIsClient;
	
	@Override
	public IpWhoIsResponse getRequesterIpGeoLocationMetaData(String requesterIpAddress) {

		log.info("@@:" + requesterIpAddress);

		IpWhoIsResponse ipWhoIsResponse;
		if ((ipWhoIsResponse = ipWhoIsClient.doWhoIsIpOn(requesterIpAddress)) != null) {
			
			log.info("ipAddress ipWhoIsResponse :: JSON {}", new JSONObject(ipWhoIsResponse).toString());
		} else {
			ipWhoIsResponse = null;
		}

		return ipWhoIsResponse;

	}
	

	@Override
	public String getRequesterCountry(String requesterIpAddress) {

		log.info("@@:" + requesterIpAddress);

		IpWhoIsResponse ipWhoIsResponse = ipWhoIsClient.doWhoIsIpOn(requesterIpAddress);

		String theOriginatingCountry = null;
		if (ipWhoIsResponse != null) {
			theOriginatingCountry = ipWhoIsResponse.getCountry();
		} else {

		}

		return theOriginatingCountry;

	}

	@Override
	public String getRequesterCountryCode(String requesterIpAddress) {
	
		log.info("@@:" + requesterIpAddress);

		IpWhoIsResponse ipWhoIsResponse = ipWhoIsClient.doWhoIsIpOn(requesterIpAddress);

		String theOriginatingCountryCode = null;
		if (ipWhoIsResponse != null) {
			theOriginatingCountryCode = ipWhoIsResponse.getCountry_code();
		} else {

		}

		return theOriginatingCountryCode;

	}

}
