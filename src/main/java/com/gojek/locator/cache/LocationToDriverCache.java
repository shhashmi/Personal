package com.gojek.locator.cache;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.gojek.locator.model.DriverLocation;
import com.gojek.locator.model.Location;

@Component
public class LocationToDriverCache {

	NavigableMap<Float, Set<Integer>> latRangeMap = new TreeMap<Float,Set<Integer>>();
	NavigableMap<Float, Set<Integer>> longRangeMap = new TreeMap<Float,Set<Integer>>();
	
	public void addToCache(int driverId,Location location) {
		
		Float latKey = getLocationKey(location.getLatitude());
		Float longKey = getLocationKey(location.getLongitude());
		if(latRangeMap.containsKey(latKey)) {
			latRangeMap.get(latKey).add(driverId);
		}
		else {
			Set<Integer> driverSet = Collections.synchronizedSet(new HashSet<Integer>());
			driverSet.add(driverId);
			latRangeMap.put(latKey, driverSet);
			
		}
		
		if(longRangeMap.containsKey(longKey)) {
			longRangeMap.get(longKey).add(driverId);
		}
		else {
			Set<Integer> driverSet = Collections.synchronizedSet(new HashSet<Integer>());
			driverSet.add(driverId);
			longRangeMap.put(longKey, driverSet);
		}
		
		
	}
	
	public Set<Integer> getDrivers(Location location) {
		
		Set<Integer> driversAtUpperLat = getEntryValueSet(latRangeMap.ceilingEntry(location.getLatitude()));
		//Set<Integer> driversAtLowerLat = getEntryValueSet(latRangeMap.floorEntry(location.getLatitude()));
		//Set<Integer> driversAtLowerLong = getEntryValueSet(latRangeMap.floorEntry(location.getLongitude()));
		Set<Integer> driversAtUpperLong = getEntryValueSet(longRangeMap.ceilingEntry(location.getLongitude()));
		//driversAtUpperLat.retainAll(driversAtLowerLat);
		//driversAtUpperLat.retainAll(driversAtLowerLong);
		driversAtUpperLat.retainAll(driversAtUpperLong);
		return driversAtUpperLat;
		
	}
	
	private Set<Integer> getEntryValueSet(Entry<Float,Set<Integer>> entry) {
		if(entry == null) {
			return new HashSet<Integer>();
		}
		return new HashSet<>(entry.getValue());
	}
	
	public void removeDriver(DriverLocation driverLocation) {
		if(driverLocation.getLocation()==null) {
			return ;
		}
		Float latKey = getLocationKey(driverLocation.getLocation().getLatitude());
		Float longKey = getLocationKey(driverLocation.getLocation().getLongitude());
		if(latRangeMap.get(latKey)!=null) {
			latRangeMap.get(latKey).remove(driverLocation.getDriverId());
		}
		
		if(longRangeMap.get(longKey)!=null) {
			longRangeMap.get(longKey).remove(driverLocation.getDriverId());
		}
	}
	private Float getLocationKey(Float value) {
		
		BigDecimal b = new BigDecimal(value);
		
		return b.setScale(3, BigDecimal.ROUND_HALF_UP).floatValue();
		
	}
	
}
