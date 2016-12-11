package com.gojek.locator.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.gojek.locator.error.ErrorMessage;
import com.gojek.locator.exception.InvalidDriverException;
import com.gojek.locator.model.UpdateDriverLocationResponse;


@Provider
@Component
public class InvalidRequestExceptionMapper implements ExceptionMapper<InvalidDriverException> {

	@Override
	public Response toResponse(InvalidDriverException arg0) {
		UpdateDriverLocationResponse response = new UpdateDriverLocationResponse();
		String[] error = {ErrorMessage.InvalidDriver.getMessage()};
		response.setErrors(error);
		
		return Response.status(Status.NOT_FOUND).entity(response).build();
	}

}
