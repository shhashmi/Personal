package com.gojek.locator.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.gojek.locator.error.ErrorMessage;
import com.gojek.locator.exception.InvalidAccuracyException;
import com.gojek.locator.model.UpdateDriverLocationResponse;

@Provider
public class InvalidAccuracyExceptionMapper implements ExceptionMapper<InvalidAccuracyException>{

	@Override
	public Response toResponse(InvalidAccuracyException arg0) {
		UpdateDriverLocationResponse response = new UpdateDriverLocationResponse();
		String[] error = {ErrorMessage.InvalidAccuracy.getMessage()};
		response.setErrors(error);
		
		return Response.status(422).entity(response).build();
	}

}
