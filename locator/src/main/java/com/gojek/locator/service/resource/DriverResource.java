package com.gojek.locator.service.resource;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
import com.gojek.locator.validator.RequestValidator;

@Component
@Path("/drivers")
public class DriverResource {

	@Autowired
	private DriverService driverService;
	
	@POST
	@Path("/{id}/location")
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Valid
	public Response updateDriverLocation(@PathParam("id") int id,UpdateDriverLocationRequest request) {
		RequestValidator.validateUpdateDriverLocationRequest(id, request);
		driverService.handleUpdateRequest(id, request);
		return Response.status(Status.OK).build();
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getDrivers(@QueryParam("latitude") Double latitude, @QueryParam("longitude") Double longitude,
			@QueryParam("radius") @DefaultValue("500") int radius, @QueryParam("limit") @DefaultValue("10") int limit) {
		
		RequestValidator.validateGetDriverRequest(latitude, longitude);
		
		GetDriverResponse drivers = driverService.getDrivers(new Location(latitude,longitude), limit, radius);
		
		return Response.status(Status.OK).entity(drivers).build();
		
		

	}
}
