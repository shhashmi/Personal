package com.gojek.locator.exception.mapper;

import javax.ws.rs.core.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.gojek.locator.error.ErrorMessage;
import com.gojek.locator.exception.InvalidAccuracyException;
import com.gojek.locator.model.UpdateDriverLocationResponse;
import com.gojek.locator.utils.Constants;

public class InvalidAccuracyExceptionMapperTest {

	@Test
	public void toResponseTest () {
		InvalidAccuracyExceptionMapper mapper = new InvalidAccuracyExceptionMapper();
		Response response = mapper.toResponse(new InvalidAccuracyException());
		Assert.assertEquals(response.getStatus(), Constants.UNPROCESSABLE_ENTITY_RESPONSE);
		UpdateDriverLocationResponse entity = (UpdateDriverLocationResponse)response.getEntity();
		//UpdateDriverLocationResponse entity = response.readEntity(UpdateDriverLocationResponse.class);
		Assert.assertEquals(entity.getErrors()[0], ErrorMessage.InvalidAccuracy.getMessage());
	}
	
}
