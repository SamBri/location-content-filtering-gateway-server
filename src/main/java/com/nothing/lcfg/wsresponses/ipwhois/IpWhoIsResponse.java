package com.nothing.lcfg.wsresponses.ipwhois;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class IpWhoIsResponse{
	
    private String ip;
    private boolean success;
    private String type;
    private String continent;
    private String continent_code;
    private String country;
    private String country_code;
    private String region;
    private String region_code;
    private String city;
    private double latitude;
    private double longitude;
    private boolean is_eu;
    private String postal;
    private String calling_code;
    private String capital;
    private String borders;
    private Flag flag;
    private Connection connection;
    private Timezone timezone;
    private Currency currency;
    private Security security;
	

}








