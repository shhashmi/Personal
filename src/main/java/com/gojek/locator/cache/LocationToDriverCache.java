package com.gojek.locator.cache;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gojek.locator.model.DriverLocation;
import com.gojek.locator.model.Location;

@Component
public class LocationToDriverCache {

	@Autowired
	private DriverLocationCache driverLocations;
	
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
		
		Set<Integer> driversAtUpperLong = getEntryValueSet(longRangeMap.ceilingEntry(location.getLongitude()));
		
		driversAtUpperLat.retainAll(driversAtUpperLong);
		return driversAtUpperLat;
		
	}
	
	public Map<DriverLocation,Integer> getMaxDriverCountInRange(Location searchLocation,int distance,int limit) {
		//Set<Integer> drivers = getDrivers(searchLocation);
		
		Map<DriverLocation,Integer> driverWithDistance = new HashMap<DriverLocation,Integer>();
		LinkedList<Location> surroundingLocationQueue = new LinkedList<Location>();
		surroundingLocationQueue.add(searchLocation);
		Set<Location> visitedLocation = new HashSet<Location>();
		Location location = null;
		while((location=surroundingLocationQueue.pollLast())!=null) {
			visitedLocation.add(location);
			Set<Integer> drivers = getDrivers(location);
			drivers.forEach((driver->{
				Location dLoc = driverLocations.getLocation(driver);
				int diff = dLoc.diff(searchLocation);
				if(diff<=distance) {
					driverWithDistance.put(new DriverLocation(driver, dLoc), diff);
				}
			}));
			
			if(driverWithDistance.keySet().size()>=limit) {
				break;
			}
			
			getLocationsAround(location, visitedLocation).forEach((item->{
				surroundingLocationQueue.addFirst(item);
			}));
						
		}
		
		return driverWithDistance;
		
	}
	
	
	Set<Location> getLocationsAround(Location location,Set<Location> visited) {
		Set<Location> locations = new HashSet<Location>();
		Float higherLat = latRangeMap.higherKey(location.getLatitude())!=null?latRangeMap.higherKey(latRangeMap.higherKey(location.getLatitude())):null;
		Float higherLong =longRangeMap.higherKey(location.getLongitude())!=null?longRangeMap.higherKey(longRangeMap.higherKey(location.getLongitude())):null;
		Float lowerLat = latRangeMap.lowerKey(location.getLatitude());
		Float lowerLong = latRangeMap.lowerKey(location.getLatitude());
		if(lowerLat!=null) {
			locations.add(new Location(lowerLat,location.getLongitude()));
		}
		if(higherLat!=null) {
			locations.add(new Location(higherLat,location.getLongitude()));
		}
		if(lowerLong!=null) {
			locations.add(new Location(location.getLatitude(),lowerLong));
		}
		if(higherLong!=null) {
			locations.add(new Location(location.getLatitude(),higherLong));
		}
		
		if(lowerLat!=null && lowerLong!=null) {
			locations.add(new Location(lowerLat,lowerLong));
		}
		if(higherLat!=null && higherLong!=null) {
			locations.add(new Location(higherLat,higherLong));
		}
		if(lowerLat!=null && higherLong!=null) {
			locations.add(new Location(lowerLat,higherLong));
		}
		if(higherLat!=null && lowerLong!=null) {
			locations.add(new Location(higherLat,lowerLong));
		}
				
		locations.removeAll(visited);
		return locations;
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
