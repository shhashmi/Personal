package com.gojek.locator.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.gojek.locator.exception.mapper.InvalidAccuracyExceptionMapper;
import com.gojek.locator.exception.mapper.InvalidLatitudeExceptionMapper;
import com.gojek.locator.exception.mapper.InvalidLongitudeExceptionMapper;
import com.gojek.locator.exception.mapper.InvalidRequestExceptionMapper;
import com.gojek.locator.service.resource.DriverResource;


@Component
public class Config extends ResourceConfig{

	 public Config() {
		 	//packages(true,"com.gojek");
	        register(DriverResource.class);
	        register(InvalidLongitudeExceptionMapper.class);
	        register(InvalidAccuracyExceptionMapper.class);
	        register(InvalidRequestExceptionMapper.class);
	        register(InvalidLatitudeExceptionMapper.class);
	    }

}
