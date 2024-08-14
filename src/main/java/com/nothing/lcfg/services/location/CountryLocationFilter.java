package com.nothing.lcfg.services.location;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.nothing.lcfg.routing.exceptions.ServiceBlockedException;
import com.nothing.lcfg.routing.exceptions.ServiceUnavailableException;
import com.nothing.lcfg.wsresponses.ipwhois.IpWhoIsResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

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
	ResourceBundle pathConfiguration;

	@Value("${gateway.boundary.type}")
	String gatewayBoundaryType;

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

		log.info("@@ The request path ::  {}", theUserRequestedService);

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

			switch (gatewayBoundaryType.toUpperCase()) {

			case "COUNTRY":
				log.info("@@@ gateway boundary type is {}", gatewayBoundaryType);
				filterServiceAccessByCountryCode(theUserRequestedService, sourceIpCountry, countryCode);
				break;

			case "PATH":
				log.info("@@@ gateway boundary type is {}", gatewayBoundaryType);
				filterServiceAccessByServiceType(theUserRequestedService, sourceIpCountry, countryCode);
				break;

			default:
				log.error("no boudary type specified.");

			}

		} else {

			log.error("GeoLocation Data not found");

		}

		return chain.filter(exchange);
	}

	private void filterServiceAccessByServiceType(String theUserRequestedService, String sourceIpCountry,
			String countryCode) {

		log.info("@@@ inside  filterServiceAccessByServiceType");

		switch (countryCode) {

		case "AF":
		case "AL":
		case "DZ":
		case "AS":
		case "AD":
		case "AO":
		case "AI":
		case "AQ":
		case "AG":
		case "AR":
		case "AM":
		case "AW":
		case "AU":
		case "AT":
		case "AZ":
		case "BS":
		case "BH":
		case "BD":
		case "BB":
		case "BY":
		case "BE":
		case "BZ":
		case "BJ":
		case "BM":
		case "BT":
		case "BO":
		case "BA":
		case "BW":
		case "BV":
		case "BR":
		case "IO":
		case "BN":
		case "BG":
		case "BF":
		case "BI":
		case "KH":
		case "CM":
		case "CA":
		case "CV":
		case "KY":
		case "CF":
		case "TD":
		case "CL":
		case "CN":
		case "CX":
		case "CC":
		case "CO":
		case "KM":
		case "CD":
		case "CG":
		case "CK":
		case "CR":
		case "CI":
		case "HR":
		case "CU":
		case "CY":
		case "CZ":
		case "CS":
		case "DK":
		case "DJ":
		case "DM":
		case "DO":
		case "TP":
		case "EC":
		case "EG":
		case "SV":
		case "GQ":
		case "ER":
		case "EE":
		case "ET":
		case "FK":
		case "FO":
		case "FJ":
		case "FI":
		case "FR":
		case "GF":
		case "PF":
		case "TF":
		case "GA":
		case "GM":
		case "GE":
		case "DE":
		case "GH":

//			switch (theUserRequestedService) {
//			case "/luxury-cars/world": {
//				String routingMessage = "";
//
//				routingMessage = routingMessage.replace("{serviceName}", theUserRequestedService);
//				routingMessage = routingMessage.replace("{country}", sourceIpCountry);
//
//				log.error("routingMessage:", routingMessage);
//				throw new ServiceUnavailableException(routingMessage);
//
//			}
//			default:
//			}

			String pathConfig;

			pathConfig = pathConfiguration.getString(countryCode);
			
			
			log.info("@@@ pathConfig {}", pathConfig);
			
			String paths[] = null;
			String statuses[] = null;

			String pathCollection[] = pathConfig.split("\\|");
			
			log.info("pathCollectionLength ::" + pathCollection.length);
			

			
			for(String pathColl : pathCollection) {
				System.err.println("@@ pathCol ::" +pathColl);
			}

			paths = pathCollection[0].split(",");
			statuses = pathCollection[1].split(",");
			
			

			
			
			
			for(String path : paths) {
				System.err.println("@@ path :: "+path);
			}
			
			
			for(String status : statuses) {
				System.err.println("@@ status :: "+status);
			}
			
			
			//System.err.println("@@@ status " + statuses);



			HashMap<String, String> routingMap = new HashMap<String, String>();

			for (int i = 0; i < pathCollection.length; i++) {

				if (!pathCollection[i].equalsIgnoreCase(",")) {
					
					log.info("paths {} :: status {}",paths[i], statuses[i] );

					routingMap.put(paths[i], statuses[i]);

				}

			}
			
			

				
			
			
			routingMap.forEach( (path, status) -> {
				
				status = status.toUpperCase();
				
				String routingMessage = "";

				routingMessage = routingMessagesBundle.getString(status);
				routingMessage = routingMessage.replace("{serviceName}", path);
				routingMessage = routingMessage.replace("{country}", sourceIpCountry);
				routingMessage = routingMessage.replace("{serviceStatus}", status);

		
				
				switch (status) {
				case "BLOCKED" -> {
	
					// all services are blocked by default
	
					log.info("@@@  BLOCKED service status.");
					throw new ServiceBlockedException(routingMessage, status);
	
				}
				case "ALLOWED" -> {
	
					log.info("@@@  ALLOWED service status.");
	
				}
				case "AVAILABLE" -> {
	
					log.info("@@@  AVAILABLE service status.");
	
				}
				case "UNAVAILABLE" -> {
	
					log.info("@@@  UNAVAILABLE service status.");
	
					log.error("routingMessage:", routingMessage);
					throw new ServiceUnavailableException(routingMessage, status);
	
				}
				default -> {
					throw new IllegalArgumentException("Unexpected value: " + status);
	
				}
				
				}
				
				
				
			});

//			log.info("countryServiceStatus found {}", countryServiceStatus);
//
//			// java17 switch expression usage
//			countryServiceStatus = countryServiceStatus.toUpperCase();
//
//			switch (countryServiceStatus) {
//			case "BLOCKED" -> {
//
//				// all services are blocked by default
//
//				log.info("@@@  BLOCKED service status.");
//				throw new ServiceBlockedException(routingMessage, countryServiceStatus);
//
//			}
//			case "ALLOWED" -> {
//
//				log.info("@@@  ALLOWED service status.");
//
//			}
//			case "AVAILABLE" -> {
//
//				log.info("@@@  AVAILABLE service status.");
//
//			}
//			case "UNAVAILABLE" -> {
//
//				log.info("@@@  UNAVAILABLE service status.");
//
//				log.error("routingMessage:", routingMessage);
//				throw new ServiceUnavailableException(routingMessage, countryServiceStatus);
//
//			}
//			default -> {
//				throw new IllegalArgumentException("Unexpected value: " + countryServiceStatus);
//
//			}

			break;
		case "GI":
		case "GR":
		case "GL":
		case "GD":
		case "GP":
		case "GU":
		case "GT":
		case "GN":
		case "GW":
		case "GY":
		case "HT":
		case "HM":
		case "HN":
		case "HK":
		case "HU":
		case "IS":
		case "IN":
		case "ID":
		case "IR":
		case "IQ":
		case "IE":
		case "IL":
		case "IT":
		case "JM":
		case "JP":
		case "JO":
		case "KZ":
		case "KE":
		case "KI":
		case "KP":
		case "KR":
		case "KW":
		case "KG":
		case "LA":
		case "LV":
		case "LB":
		case "LS":
		case "LR":
		case "LY":
		case "LI":
		case "LT":
		case "LU":
		case "MO":
		case "MK":
		case "MG":
		case "MW":
		case "MY":
		case "MV":
		case "ML":
		case "MT":
		case "MH":
		case "MQ":
		case "MR":
		case "MU":
		case "YT":
		case "MX":
		case "FM":
		case "MD":
		case "MC":
		case "MN":
		case "MS":
		case "MA":
		case "MZ":
		case "MM":
		case "NA":
		case "NR":
		case "NP":
		case "NL":
		case "AN":
		case "NC":
		case "NZ":
		case "NI":
		case "NE":
		case "NG":
		case "NU":
		case "NF":
		case "MP":
		case "NO":
		case "OM":
		case "PK":
		case "PW":
		case "PS":
		case "PA":
		case "PG":
		case "PY":
		case "PE":
		case "PH":
		case "PN":
		case "PL":
		case "PT":
		case "PR":
		case "QA":
		case "RE":
		case "RO":
		case "SU":
		case "RU":
		case "RW":
		case "SH":
		case "KN":
		case "LC":
		case "PM":
		case "VC":
		case "WS":
		case "SM":
		case "ST":
		case "SA":
		case "RS":
		case "SN":
		case "SC":
		case "SL":
		case "SG":
		case "SK":
		case "SI":
		case "SB":
		case "SO":
		case "ZA":
		case "GS":
		case "ES":
		case "LK":
		case "SD":
		case "SR":
		case "SJ":
		case "SZ":
		case "SE":
		case "CH":
		case "SY":
		case "TW":
		case "TJ":
		case "TZ":
		case "TH":
		case "TG":
		case "TK":
		case "TO":
		case "TT":
		case "TE":
		case "TN":
		case "TR":
		case "TM":
		case "TC":
		case "TV":
		case "UG":
		case "UA":
		case "AE":
		case "GB":
		case "US":
		case "UM":
		case "UY":
		case "UZ":
		case "VU":
		case "VA":
		case "VE":
		case "VN":
		case "VI":
		case "VQ":
		case "WF":
		case "EH":
		case "YE":
		case "YU":
		case "ZR":
		case "ZM":
		case "ZW":

		}

	}

	private void filterServiceAccessByCountryCode(String theUserRequestedService, String sourceIpCountry,
			String countryCode) {

		log.info("@@@ inside  filterServiceAccessByCountryCode");

		String countryServiceStatus = isoCountryCodeBundle.getString(countryCode);

		String routingMessage = null;

		routingMessage = routingMessagesBundle.getString(countryServiceStatus.toUpperCase());
		routingMessage = routingMessage.replace("{serviceName}", theUserRequestedService);
		routingMessage = routingMessage.replace("{country}", sourceIpCountry);

		log.info("countryServiceStatus found {}", countryServiceStatus);

		// java17 switch expression usage
		countryServiceStatus = countryServiceStatus.toUpperCase();

		switch (countryServiceStatus) {
		case "BLOCKED" -> {

			// all services are blocked by default

			log.info("@@@  BLOCKED service status.");
			throw new ServiceBlockedException(routingMessage, countryServiceStatus);

		}
		case "ALLOWED" -> {

			log.info("@@@  ALLOWED service status.");

		}
		case "AVAILABLE" -> {

			log.info("@@@  AVAILABLE service status.");

		}
		case "UNAVAILABLE" -> {

			log.info("@@@  UNAVAILABLE service status.");

			log.error("routingMessage:", routingMessage);
			throw new ServiceUnavailableException(routingMessage, countryServiceStatus);

		}
		default -> {
			throw new IllegalArgumentException("Unexpected value: " + countryServiceStatus);

		}

		}
	}

}
