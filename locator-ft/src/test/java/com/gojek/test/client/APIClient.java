package com.gojek.test.client;

import java.io.IOException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.gojek.test.domain.UpdateDriverLocationRequest;
import com.gojek.test.httpclient.JerseyClient;

public class APIClient {

	public static Response findDriver(Double latitude,Double longitude,Integer radius,Integer limit) throws Exception {
		WebTarget target = JerseyClient.getFindDriverResource(String.valueOf(latitude), String.valueOf(longitude), radius, limit);
		target.request().accept(MediaType.APPLICATION_JSON).get().getEntity();
		return target.request().accept(MediaType.APPLICATION_JSON).get();
		
	}
	
	public static Response updateDriverLocation(int driverId,UpdateDriverLocationRequest request) throws JsonGenerationException, IOException {
		WebTarget target = JerseyClient.getUpdateDriverResource(driverId);
		
		Response response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(request, MediaType.APPLICATION_JSON));
		
		

		return response;
	}
}
