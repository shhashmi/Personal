package com.gojek.locator.exception.mapper;

import javax.ws.rs.core.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.gojek.locator.error.ErrorMessage;
import com.gojek.locator.exception.InvalidLatitudeException;
import com.gojek.locator.model.UpdateDriverLocationResponse;
import com.gojek.locator.utils.Constants;

public class InvalidLatitudeExceptionMapperTest {

	@Test
	public void toResponseTest() {
		InvalidLatitudeExceptionMapper mapper = new InvalidLatitudeExceptionMapper();
		Response response = mapper.toResponse(new InvalidLatitudeException());
		Assert.assertEquals(response.getStatus(), Constants.UNPROCESSABLE_ENTITY_RESPONSE);
		UpdateDriverLocationResponse entity = (UpdateDriverLocationResponse)response.getEntity();
		Assert.assertEquals(entity.getErrors()[0], ErrorMessage.InvalidLatitude.getMessage());
	}
}
