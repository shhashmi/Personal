package com.gojek.locator.service.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Component;

import com.gojek.locator.model.UpdateDriverLocationRequest;
import com.gojek.locator.model.UpdateDriverLocationResponse;

@Component
@Path("/drivers")
public class DriverResource {

	@POST
	@Path("/{id}/location")
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sample(@PathParam("id") int id,UpdateDriverLocationRequest request) {
		UpdateDriverLocationResponse response = new UpdateDriverLocationResponse();
		String[] errors = {"no error"};
		response.setErrors(errors);
		return Response.status(Status.OK).entity(response).build();
	}
}
