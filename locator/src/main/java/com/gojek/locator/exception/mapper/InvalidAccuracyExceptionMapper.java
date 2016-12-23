package com.gojek.locator.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.gojek.locator.error.ErrorMessage;
import com.gojek.locator.exception.InvalidAccuracyException;
import com.gojek.locator.model.UpdateDriverLocationResponse;
import com.gojek.locator.utils.Constants;

@Provider
public class InvalidAccuracyExceptionMapper implements ExceptionMapper<InvalidAccuracyException>{

	@Override
	public Response toResponse(InvalidAccuracyException arg0) {
		UpdateDriverLocationResponse response = new UpdateDriverLocationResponse();
		String[] error = {ErrorMessage.InvalidAccuracy.getMessage()};
		response.setErrors(error);
		
		return Response.status(Constants.UNPROCESSABLE_ENTITY_RESPONSE).entity(response).build();
	}

}
