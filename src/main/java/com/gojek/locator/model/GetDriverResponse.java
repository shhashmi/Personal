package com.gojek.locator.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.jaxrs.json.annotation.JSONP;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetDriverResponse {
	

	public GetDriverResponse() {
		
	}
	public GetDriverResponse(int driverId, Float latitude, Float longitude, Integer distance) {
		super();
		this.driverId = driverId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.distance = distance;
	}

	@JsonProperty("id")
	private int driverId;
	
	@JsonProperty("latitude")
	private Float latitude;
	
	@JsonProperty("longitude")
	private Float longitude;
	
	@JsonProperty("distance")
	private Integer distance;

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
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

	
	
	
}
