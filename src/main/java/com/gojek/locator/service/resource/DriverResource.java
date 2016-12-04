package com.gojek.locator.service.resource;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gojek.locator.model.GetDriverResponse;
import com.gojek.locator.model.Location;
import com.gojek.locator.model.UpdateDriverLocationRequest;
import com.gojek.locator.model.UpdateDriverLocationResponse;
import com.gojek.locator.service.DriverService;

@Component
@Path("/drivers")
public class DriverResource {

	@Autowired
	private DriverService driverService;
	
	@POST
	@Path("/{id}/location")
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sample(@PathParam("id") int id,UpdateDriverLocationRequest request) {
		driverService.handleUpdateRequest(id, request);
		UpdateDriverLocationResponse response = new UpdateDriverLocationResponse();
		String[] errors = {"no error"};
		response.setErrors(errors);
		return Response.status(Status.OK).entity(response).build();
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getDrivers(@QueryParam("latitude") Float latitude, @QueryParam("longitude") Float longitude,
			@QueryParam("radius") @DefaultValue("500") int radius, @QueryParam("limit") @DefaultValue("10") int limit) {
		Set<GetDriverResponse> drivers = driverService.getDrivers(new Location(latitude,longitude), limit, radius);
		
		return Response.status(Status.OK).entity(drivers).build();
		
		

	}
}
