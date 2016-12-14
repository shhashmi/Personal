package com.gojek.test.httpclient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;


public class JerseyClient {

	private static String SYSTEM_UNDER_TEST_HOST = null;
	
	private static String findDriverUriWithRadiusAndLimit = "drivers?latitude=%s&longitude=%s&radius=%d&limit=%d";
	private static String findDriverUriWithRadius = "drivers?latitude=%s&longitude=%s&radius=%d";
	private static String findDriverUriWithLimit = "drivers?latitude=%s&longitude=%s&limit=%d";
	private static String findDriverUri = "drivers?latitude=%s&longitude=%s";
	
	private static String updateDriverUri = "drivers/%d/location";
	private static Client client = null;
	
	static 
	{
			if((SYSTEM_UNDER_TEST_HOST=System.getProperty("host"))==null) {
				SYSTEM_UNDER_TEST_HOST = "localhost:8080";
			}
			
			ClientConfig config = new ClientConfig();
			config.register(new JacksonFeature());
			client = ClientBuilder.newClient(config);
	}
	
	public static WebTarget getFindDriverResource(String latitude,String longitude,Integer radius,Integer limit) throws Exception {
	
		if(StringUtils.isBlank(latitude) || StringUtils.isBlank(longitude)) {
			throw new Exception("can not creat webresource for find driver");
		}
		if(radius!=null && limit!=null) {
			return client.target("http://"+SYSTEM_UNDER_TEST_HOST+"/"+String.format(findDriverUriWithRadiusAndLimit, latitude,longitude,radius,limit));
		}
		if(radius!=null) {
			return client.target("http://"+SYSTEM_UNDER_TEST_HOST+"/"+String.format(findDriverUriWithRadius, latitude,longitude,radius));
		}
		if(limit!=null) {
			return client.target("http://"+SYSTEM_UNDER_TEST_HOST+"/"+String.format(findDriverUriWithLimit, latitude,longitude,limit));
		}
		
		return client.target("http://"+SYSTEM_UNDER_TEST_HOST+"/"+String.format(findDriverUri, latitude,longitude));
	}
	
	
	public static WebTarget getUpdateDriverResource(int driverId) {
		return client.target("http://"+SYSTEM_UNDER_TEST_HOST+"/"+String.format(updateDriverUri, driverId));
	}
	
}
