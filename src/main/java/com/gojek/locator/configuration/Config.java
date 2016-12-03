package com.gojek.locator.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.gojek.locator.service.resource.DriverResource;

@Component
public class Config extends ResourceConfig{

	 public Config() {
	        register(DriverResource.class);
	    }

}
