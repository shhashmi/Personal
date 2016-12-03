package com.gojek.locator.cache;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

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
			Set<Integer> driverSet = new HashSet<Integer>();
			driverSet.add(driverId);
			latRangeMap.put(latKey, driverSet);
		}
		
		if(longRangeMap.containsKey(longKey)) {
			longRangeMap.get(longKey).add(driverId);
		}
		else {
			Set<Integer> driverSet = new HashSet<Integer>();
			driverSet.add(driverId);
			longRangeMap.put(longKey, driverSet);
		}
		
		
	}
	
	public Set<Integer> getDrivers(Location location) {
		Set<Integer> driversAtUpperLat = new HashSet<Integer>(latRangeMap.ceilingEntry(location.getLatitude()).getValue());
		Set<Integer> driversAtLowerLat = new HashSet<Integer>(latRangeMap.floorEntry(location.getLatitude()).getValue());
		Set<Integer> driversAtLowerLong = new HashSet<Integer>(latRangeMap.floorEntry(location.getLongitude()).getValue());
		Set<Integer> driversAtUpperLong = new HashSet<Integer>(latRangeMap.ceilingEntry(location.getLongitude()).getValue());
		driversAtUpperLat.retainAll(driversAtLowerLat);
		driversAtUpperLat.retainAll(driversAtLowerLong);
		driversAtUpperLat.retainAll(driversAtUpperLong);
		return driversAtUpperLat;
		
	}
	
	private Float getLocationKey(Float value) {
		
		BigDecimal b = new BigDecimal(value);
		
		return b.setScale(3, BigDecimal.ROUND_HALF_UP).floatValue();
		
	}
	
}
