package com.gojek.test.api;

import java.util.Set;

import javax.ws.rs.core.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.gojek.test.client.APIClient;
import com.gojek.test.domain.GetDriverResponse;
import com.gojek.test.domain.GetDriverResponse.NearByDriver;
import com.gojek.test.domain.UpdateDriverLocationRequest;
import com.gojek.test.domain.UpdateDriverLocationResponse;

public class FindDriverTest {

	@Test
	public void getDriver() throws Exception {
		UpdateDriverLocationRequest request = new UpdateDriverLocationRequest();
		request.setLatitude(-10.00000000);
		request.setLongitude(-17.97569987);
		APIClient.updateDriverLocation(1, request);
		Response response = APIClient.findDriver(-10.00000000, -17.97569987, 500, 10);
		GetDriverResponse getResponse = response.readEntity(GetDriverResponse.class);
		Set<NearByDriver> drivers = getResponse.getDrivers();
		boolean driverFound = false;
		for (NearByDriver driver : drivers) {
			if (driver.getDriverId() == 1) {
				driverFound = true;
				break;
			}
		}
		Assert.assertEquals(driverFound, true, "driver with Id not found");

	}

	@Test
	public void getDriverCountAndLimitTest() throws Exception {

		for (int i = 1; i < 10; i++) {
			UpdateDriverLocationRequest request = new UpdateDriverLocationRequest();
			request.setLatitude(80.0050000 + i);
			request.setLongitude(70.9796998 + i);
			Response re = APIClient.updateDriverLocation(i, request);

			// System.out.println(re.readEntity(UpdateDriverLocationResponse.class));

		}

		Response response = APIClient.findDriver(80.00500000, 70.97969987, 10, 5);
		GetDriverResponse getResponse = response.readEntity(GetDriverResponse.class);
		Set<NearByDriver> drivers = getResponse.getDrivers();
		Assert.assertEquals(drivers.size(), 5);

	}

	@Test
	public void getDriverInvalidLatitude() throws Exception {

		Response response = APIClient.findDriver(180.00500000, 70.97969987, 10, 5);
		GetDriverResponse getResponse = response.readEntity(GetDriverResponse.class);
		Assert.assertEquals(getResponse.getErrors()[0], "Latitude should be between +/- 90");

	}

	@Test
	public void getDriverInvalidLongitude() throws Exception {

		Response response = APIClient.findDriver(80.00500000, 170.97969987, 10, 5);
		GetDriverResponse getResponse = response.readEntity(GetDriverResponse.class);
		Assert.assertEquals(getResponse.getErrors()[0], "Longitude should be between +/- 90");

	}
	
	@Test
	public void getDriverDistanceLimit() throws Exception{
		
		/**
		 * add drivers with greater than expected distance;
		 */
		for (int i = 1; i < 20; i++) {
			UpdateDriverLocationRequest request = new UpdateDriverLocationRequest();
			request.setLatitude(-80.0050000 + i);
			request.setLongitude(-70.9796998 + i);
			Response re = APIClient.updateDriverLocation(i, request);

			// System.out.println(re.readEntity(UpdateDriverLocationResponse.class));

		}

		/**
		 * Add drivers within expected distance from search location
		 */
		
		for (Long i = 21L; i < 40; i++) {
			UpdateDriverLocationRequest request = new UpdateDriverLocationRequest();
			
			request.setLatitude(80.050000 + i/10);
			request.setLongitude(70.9796998 + i/10);
			APIClient.updateDriverLocation(i.intValue(), request);

		}
		
		
		Response response = APIClient.findDriver(80.00500000, 70.97969987, 30, 15);
		GetDriverResponse getResponse = response.readEntity(GetDriverResponse.class);
		Set<NearByDriver> drivers = getResponse.getDrivers();
		Assert.assertEquals(drivers.size(), 15);
	}

}
