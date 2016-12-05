package com.gojek.locator.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.gojek.locator.model.Location;

@Component
public class DriverLocationCache {

	private Map<Integer,Location> driverLocation  = new ConcurrentHashMap<Integer,Location>();
	
		
	public void updateLocation(Integer driverId,Location location) {
		driverLocation.put(driverId, location);
	}
	
	public Location getLocation(Integer driverId) {
		return driverLocation.get(driverId);
	}
	
}
