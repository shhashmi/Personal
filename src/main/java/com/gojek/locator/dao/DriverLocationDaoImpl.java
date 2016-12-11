package com.gojek.locator.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.gojek.locator.model.Location;

@Component
public class DriverLocationDaoImpl implements DriverLocationDao {

	private Map<Integer,Location> driverLocation  = new ConcurrentHashMap<Integer,Location>();
	
		
	@Override
	public void updateLocation(Integer driverId,Location location) {
		driverLocation.put(driverId, location);
	}
	
	@Override
	public Location getLocation(Integer driverId) {
		return driverLocation.get(driverId);
	}
	
}
