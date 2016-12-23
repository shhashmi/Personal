package com.gojek.locator.model;

import java.util.Arrays;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetDriverResponse {

	@JsonProperty("errors")
	private String[] errors;

	public String[] getErrors() {
		return errors;
	}

	public void setErrors(String[] errors) {
		this.errors = errors;
	}
	@JsonProperty
	private Set<NearByDriver> drivers;

	
	public Set<NearByDriver> getDrivers() {
		return drivers;
	}


	public void setDrivers(Set<NearByDriver> drivers) {
		this.drivers = drivers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((drivers == null) ? 0 : drivers.hashCode());
		result = prime * result + Arrays.hashCode(errors);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GetDriverResponse other = (GetDriverResponse) obj;
		if (drivers == null) {
			if (other.drivers != null)
				return false;
		} else if (!drivers.equals(other.drivers))
			return false;
		if (!Arrays.equals(errors, other.errors))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GetDriverResponse [errors=" + Arrays.toString(errors) + ", drivers=" + drivers + "]";
	}



	public static class NearByDriver {
		
		public NearByDriver() {

		}

		public NearByDriver(int driverId, Double latitude, Double longitude, Integer distance) {
			super();
			this.driverId = driverId;
			this.latitude = latitude;
			this.longitude = longitude;
			this.distance = distance;
		}

		@JsonProperty("id")
		private int driverId;

		@JsonProperty("latitude")
		private Double latitude;

		@JsonProperty("longitude")
		private Double longitude;

		@JsonProperty("distance")
		private Integer distance;

		public int getDriverId() {
			return driverId;
		}

		public void setDriverId(int driverId) {
			this.driverId = driverId;
		}

		public Double getLatitude() {
			return latitude;
		}

		public void setLatitude(Double latitude) {
			this.latitude = latitude;
		}

		public Double getLongitude() {
			return longitude;
		}

		public void setLongitude(Double longitude) {
			this.longitude = longitude;
		}

		public Integer getDistance() {
			return distance;
		}

		public void setDistance(Integer distance) {
			this.distance = distance;
		}

		@Override
		public String toString() {
			return "GetDriverResponse [driverId=" + driverId + ", latitude=" + latitude + ", longitude=" + longitude
					+ ", distance=" + distance + "]";
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((distance == null) ? 0 : distance.hashCode());
			result = prime * result + driverId;
			result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
			result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			NearByDriver other = (NearByDriver) obj;
			if (distance == null) {
				if (other.distance != null)
					return false;
			} else if (!distance.equals(other.distance))
				return false;
			if (driverId != other.driverId)
				return false;
			if (latitude == null) {
				if (other.latitude != null)
					return false;
			} else if (!latitude.equals(other.latitude))
				return false;
			if (longitude == null) {
				if (other.longitude != null)
					return false;
			} else if (!longitude.equals(other.longitude))
				return false;
			return true;
		}


	}

}
