package com.gojek.test.api;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.gojek.test.client.APIClient;
import com.gojek.test.domain.UpdateDriverLocationRequest;
import com.gojek.test.domain.UpdateDriverLocationResponse;

public class UpdateDriverTest {

	@Test
	public void addDriverNegativeId() throws JsonGenerationException, IOException {
		UpdateDriverLocationRequest request = new UpdateDriverLocationRequest();
		request.setLatitude(-10.00000000);
		request.setLongitude(-17.97569987);
		Response response = APIClient.updateDriverLocation(-1, request);
		Assert.assertEquals(response.readEntity(UpdateDriverLocationResponse.class).getErrors()[0],"Valid driver id is between 1-50000");
		Assert.assertEquals(response.getStatus(), 404);
	}
	
	@Test
	public void addDriverGreaterThan5KDriverId() throws JsonGenerationException, IOException {
		UpdateDriverLocationRequest request = new UpdateDriverLocationRequest();
		request.setLatitude(-10.00000000);
		request.setLongitude(-17.97569987);
		Response response = APIClient.updateDriverLocation(50001, request);
		Assert.assertEquals(response.readEntity(UpdateDriverLocationResponse.class).getErrors()[0],"Valid driver id is between 1-50000");
		Assert.assertEquals(response.getStatus(), 404);
	}
	
	@Test
	public void addDriver() throws JsonGenerationException, IOException {
		UpdateDriverLocationRequest request = new UpdateDriverLocationRequest();
		request.setLatitude(-10.00000000);
		request.setLongitude(-17.97569987);
		Response response = APIClient.updateDriverLocation(56, request);
		//Assert.assertEquals(response.readEntity(UpdateDriverLocationResponse.class).getErrors()[0],"Valid driver id is between 1-50000");
		Assert.assertEquals(response.getStatus(), 200);
	}
	
	@Test
	public void addDriverLatLessThanNegative90() throws JsonGenerationException, IOException {
		UpdateDriverLocationRequest request = new UpdateDriverLocationRequest();
		request.setLatitude(-120.00000000);
		request.setLongitude(-17.97569987);
		Response response = APIClient.updateDriverLocation(3, request);
		Assert.assertEquals(response.readEntity(UpdateDriverLocationResponse.class).getErrors()[0],"Latitude should be between +/- 90");
		Assert.assertEquals(response.getStatus(), 422);
	}
	
	@Test
	public void addDriverLonLessThanNegative90() throws JsonGenerationException, IOException {
		UpdateDriverLocationRequest request = new UpdateDriverLocationRequest();
		request.setLatitude(-10.00000000);
		request.setLongitude(-197.97569987);
		Response response = APIClient.updateDriverLocation(3, request);
		Assert.assertEquals(response.readEntity(UpdateDriverLocationResponse.class).getErrors()[0],"Longitude should be between +/- 90");
		Assert.assertEquals(response.getStatus(), 422);
	}
	
	
	
	
}
