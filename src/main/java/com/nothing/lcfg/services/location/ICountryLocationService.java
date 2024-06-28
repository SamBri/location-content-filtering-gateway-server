package com.nothing.lcfg.services.location;


public interface ICountryLocationService {

	
	// co-variant returns
    public Object getRequesterIpGeoLocationMetaData(String requesterIpAddress);
	
	String getRequesterCountry(String requesterIpAddress);
	
	String getRequesterCountryCode(String requesterIpAddress);
	
	
}
