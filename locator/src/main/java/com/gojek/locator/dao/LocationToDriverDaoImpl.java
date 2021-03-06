package com.gojek.locator.dao;

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
public class LocationToDriverDaoImpl implements LocationToDriverDao {

	@Autowired
	private DriverLocationDao driverLocations;

	NavigableMap<Double, Set<Integer>> latRangeMap = new TreeMap<Double, Set<Integer>>();
	NavigableMap<Double, Set<Integer>> longRangeMap = new TreeMap<Double, Set<Integer>>();

	@Override
	public void addDriverLocation(int driverId, Location location) {

		Double latKey = getLatLongKey(location.getLatitude());
		Double longKey = getLatLongKey(location.getLongitude());
		if (latRangeMap.containsKey(latKey)) {
			latRangeMap.get(latKey).add(driverId);
		} else {
			Set<Integer> driverSet = Collections.synchronizedSet(new HashSet<Integer>());
			driverSet.add(driverId);
			latRangeMap.put(latKey, driverSet);

		}

		if (longRangeMap.containsKey(longKey)) {
			longRangeMap.get(longKey).add(driverId);
		} else {
			Set<Integer> driverSet = Collections.synchronizedSet(new HashSet<Integer>());
			driverSet.add(driverId);
			longRangeMap.put(longKey, driverSet);
		}

	}

	/*
	 * (non-Javadoc) Entire latitude, longitude is divided into smaller cells of
	 * square shape. To affectively implement this without too much of space
	 * overhead all the drivers at a particular latitude are stored as a list
	 * against that latitude and same is the case for longitude. Granularity of
	 * latitude and longitude is 1. Latitude and Longitude Data is stored in a
	 * data structure that enables query based on closest match.
	 * 
	 * @see
	 * com.gojek.locator.dao.LocationToDriverDao#getDrivers(com.gojek.locator.
	 * model.Location)
	 */
	@Override
	public Set<Integer> getDrivers(Location location) {

		Set<Integer> driversAtUpperLat = getEntryValueSet(latRangeMap.ceilingEntry(location.getLatitude()));

		Set<Integer> driversAtUpperLong = getEntryValueSet(longRangeMap.ceilingEntry(location.getLongitude()));

		driversAtUpperLat.retainAll(driversAtUpperLong);
		return driversAtUpperLat;

	}

	@Override
	public Map<DriverLocation, Integer> getMaxDriverCountInRange(Location searchLocation, int distance, int limit) {
		// Set<Integer> drivers = getDrivers(searchLocation);

		Map<DriverLocation, Integer> driverWithDistance = new HashMap<DriverLocation, Integer>();
		Set<Location> visitedLocation = new HashSet<Location>();
		LinkedList<Location> surroundingLocationQueue = new LinkedList<Location>();
		surroundingLocationQueue.addAll(getLocationsAround(searchLocation, visitedLocation, searchLocation, distance));

		Location location = null;
		while ((location = surroundingLocationQueue.pollLast()) != null) {
			if (location.diff(searchLocation) > distance) {
				break;
			}

			Set<Integer> drivers = getDrivers(location);
			for (Integer driver : drivers) {
				Location dLoc = driverLocations.getLocation(driver);
				int diff = dLoc.diff(searchLocation);
				if (diff <= distance) {
					driverWithDistance.put(new DriverLocation(driver, dLoc), diff);
				}
				if (isExpectedDriverCountMet(driverWithDistance, limit)) {
					break;
				}

			}

			if (isExpectedDriverCountMet(driverWithDistance, limit)) {
				break;
			}

			getLocationsAround(location, visitedLocation, searchLocation, distance).forEach((item -> {
				surroundingLocationQueue.addFirst(item);
			}));

		}

		return driverWithDistance;

	}

	private boolean isExpectedDriverCountMet(Map<DriverLocation, Integer> driverWithDistance, int maxDriverCount) {
		if (driverWithDistance.keySet().size() >= maxDriverCount) {
			return true;
		}
		return false;
	}

	private Set<Location> getLocationsAround(Location location, Set<Location> visited, Location searchLocation,
			int distance) {
		Set<Location> locations = new HashSet<Location>();
		Double higherLat = null;
		Double higherLong = null;
		
		if(!visited.contains(location)) {
			higherLat = getLatLongKey(latRangeMap.ceilingKey(location.getLatitude()));
			higherLong = getLatLongKey(longRangeMap.ceilingKey(location.getLongitude()));
		}
		else {
			higherLat = getLatLongKey(latRangeMap.higherKey(location.getLatitude()));
			higherLong = getLatLongKey(longRangeMap.higherKey(location.getLongitude()));
		}
		
		Double lowerLat = getLatLongKey(latRangeMap.lowerKey(location.getLatitude()));
		Double lowerLong = getLatLongKey(longRangeMap.lowerKey(location.getLongitude()));
		if (lowerLat != null) {
			addToSurroundingLocationSet(locations, searchLocation,
					new Location(lowerLat, getLatLongKey(location.getLongitude())), distance);
		}
		if (higherLat != null) {
			addToSurroundingLocationSet(locations, searchLocation,
					new Location(higherLat, getLatLongKey(location.getLongitude())), distance);
		}
		if (lowerLong != null) {
			addToSurroundingLocationSet(locations, searchLocation,
					new Location(getLatLongKey(location.getLatitude()), lowerLong), distance);
		}
		if (higherLong != null) {
			addToSurroundingLocationSet(locations, searchLocation,
					new Location(getLatLongKey(location.getLatitude()), higherLong), distance);
		}

		if (lowerLat != null && lowerLong != null) {
			addToSurroundingLocationSet(locations, searchLocation, new Location(lowerLat, lowerLong), distance);
		}
		if (higherLat != null && higherLong != null) {
			addToSurroundingLocationSet(locations, searchLocation, new Location(higherLat, higherLong), distance);

		}
		if (lowerLat != null && higherLong != null) {
			addToSurroundingLocationSet(locations, searchLocation, new Location(lowerLat, higherLong), distance);
		}
		if (higherLat != null && lowerLong != null) {
			addToSurroundingLocationSet(locations, searchLocation, new Location(higherLat, lowerLong), distance);

		}

		locations.removeAll(visited);

		locations.forEach((item -> {
			visited.add(new Location(getLatLongKey(item.getLatitude()), getLatLongKey(item.getLongitude())));
		}));
		return locations;
	}

	private void addToSurroundingLocationSet(Set<Location> locations, Location searchLocation, Location location,
			int distance) {
		if (location.diff(searchLocation) <= distance) {
			locations.add(location);
		}
	}

	private Set<Integer> getEntryValueSet(Entry<Double, Set<Integer>> entry) {
		if (entry == null) {
			return new HashSet<Integer>();
		}
		return new HashSet<>(entry.getValue());
	}

	@Override
	public void removeDriver(DriverLocation driverLocation) {
		if (driverLocation.getLocation() == null) {
			return;
		}
		Double latKey = getLatLongKey(driverLocation.getLocation().getLatitude());
		Double longKey = getLatLongKey(driverLocation.getLocation().getLongitude());
		if (latRangeMap.get(latKey) != null) {
			latRangeMap.get(latKey).remove(driverLocation.getDriverId());
		}

		if (longRangeMap.get(longKey) != null) {
			longRangeMap.get(longKey).remove(driverLocation.getDriverId());
		}
	}

	private Double getLatLongKey(Double value) {
		if (value == null) {
			return null;
		}
		BigDecimal b = new BigDecimal(value);

		return b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

	}
	
	public void setDriverLocation(DriverLocationDao dao) {
		this.driverLocations = dao;
	}

}
